/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.mod.module.modules;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.events.server.ServerLoadChunkEvent;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import com.sasha.adorufu.mod.module.ModuleInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sasha at 8:10 PM on 9/22/2018
 */
@ModuleInfo(description = "Highlights blocks that were modified since the last time you were in that chunk")
public class ModuleChunkCheck extends AdorufuModule implements SimpleListener {

    static volatile ArrayList<ChunkCheckData> chunkDatas;
    private static ArrayList<BlockPos> changedBlocks;

    public ModuleChunkCheck() {
        super("ChunkCheck", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        if (AdorufuMod.minecraft.getCurrentServerData() == null) {
            this.toggle();
            return;
        }
        chunkDatas = new ArrayList<>();
        new Thread(() -> {
            chunkDatas.clear();
            ModuleChunkCheckData.loadData(AdorufuMod.minecraft.getCurrentServerData().serverIP);
        }).start();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (chunkDatas == null) {
            onEnable();
        }

    }
    @Override
    public void onRender() {
        // todo render the changed blocks here
    }

    @SimpleEventHandler
    public void onChunkLoad(ServerLoadChunkEvent e) {
        if (!this.isEnabled()) return;
        new Thread(() -> {
            boolean havePreviousChunk = false;
            ChunkCheckData prevCachedChunk = null;
            for (ChunkCheckData chunkData : chunkDatas) {
                if (chunkData.chunkXPos == e.getChunkX() && chunkData.chunkZPos == e.getChunkZ()) {
                    havePreviousChunk = true;
                    prevCachedChunk = chunkData;
                    break;
                }
            }
            // make a new chunk obj to compare to the old one
            ChunkCheckData thisNewChunk = new ChunkCheckData(e.getChunkX(), e.getChunkZ());
            for (int x = (e.getChunkX() * 16); x < (e.getChunkX() * 16) + 16; x++) {
                for (int y = 0; y < 256; y++) {
                    for (int z = (e.getChunkZ() * 16); z < (e.getChunkZ() * 16) + 16; z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        Block block = AdorufuMod.minecraft.world.getBlockState(pos).getBlock();
                        int id = Block.getIdFromBlock(block);
                        thisNewChunk.add(id);
                    }
                }
            }
            if (!havePreviousChunk) {
                if (AdorufuMod.minecraft.getCurrentServerData() == null) {
                    this.toggle();
                    return;
                }
                try {
                    ModuleChunkCheckData.saveData(thisNewChunk, AdorufuMod.minecraft.getCurrentServerData().serverIP);
                } catch (IOException e1) {
                    AdorufuMod.logErr(false, "Error occured while saving check data " + e1.getMessage());
                    e1.printStackTrace();
                }
                return;
            }
            // compare
        }).start();
    }
}

class ModuleChunkCheckData {

    public static void saveData(ChunkCheckData data, String ip) throws IOException {
        if (!ModuleChunkCheck.chunkDatas.contains(data)) ModuleChunkCheck.chunkDatas.add(data);
        File dir = new File("checkdata");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!dir.isDirectory()) {
            dir.delete();
            dir.mkdir();
        }
        String ipformat = ip.toLowerCase().replace("_", "-").replace(".", "_");
        File ipDir = new File(dir, ipformat);
        if (!ipDir.exists()) {
            ipDir.mkdir();
            return;
            // no data
        }
        if (!ipDir.isDirectory()) {
            ipDir.delete();
            ipDir.mkdir();
            return;
            // no data
        }
        File file = new File(ipDir, data.chunkXPos + "_" + data.chunkZPos + ".mcdat");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(data);
        AdorufuMod.logMsg("Wrote ChunkCheck data for " + ip + ", " + data.chunkXPos + " " + data.chunkZPos);
        oos.close();
    }

    public static void loadData(String ip) {
        File dir = new File("checkdata");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!dir.isDirectory()) {
            dir.delete();
            dir.mkdir();
        }
        String ipformat = ip.toLowerCase().replace("_", "-").replace(".", "_");
        File ipDir = new File(dir, ipformat);
        if (!ipDir.exists()) {
            ipDir.mkdir();
            return;
            // no data
        }
        if (!ipDir.isDirectory()) {
            ipDir.delete();
            ipDir.mkdir();
            return;
            // no data
        }
        File[] datas = ipDir.listFiles();
        if (datas == null) {
            return;
            // no data
        }
        Arrays.stream(datas).filter(f -> f.getName().endsWith(".mcdat")).forEach(dat -> {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat));
                ChunkCheckData obj = (ChunkCheckData) ois.readObject();
                ModuleChunkCheck.chunkDatas.add(obj);
                AdorufuMod.logMsg("Loaded ChunkCheck data for " + ip + ", " + obj.chunkXPos + " " + obj.chunkZPos);
            } catch (IOException | ClassNotFoundException e) {
                return;
            }
        });
    }

}

class ChunkCheckData implements Serializable {
    private String serverIp;
    final Integer chunkXPos;
    final Integer chunkZPos;
    private ArrayList<Integer> blocksIds;

    public ChunkCheckData(int chunkXPos, int chunkZPos) {
        this.chunkXPos = chunkXPos;
        this.chunkZPos = chunkZPos;
    }
    public void add(int id) {
        this.blocksIds.add(id);
    }
    public void del(int id) {
        if (!this.blocksIds.contains(id)) return;
        this.blocksIds.remove(id);
    }
}
