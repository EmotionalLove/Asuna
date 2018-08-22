package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.events.PlayerBlockBreakEvent;
import com.sasha.adorufu.misc.AdorufuRender;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketSpawnObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModuleGhostBlockWarning extends AdorufuModule implements SimpleListener {

    /** dont ask why this variable is named this it was like this already HUSH PLS*/
    private static LinkedHashMap<Coordinate, Boolean> BlakeIsMyBoyfriendMap = new LinkedHashMap<>();

    public ModuleGhostBlockWarning() {
        super("GhostBlockWarning", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
    @Override
    public void onRender(){
        if (!this.isEnabled()){
            return;
        }
        for (HashMap.Entry<Coordinate, Boolean> wow : BlakeIsMyBoyfriendMap.entrySet()){
            if(!wow.getValue()){
                AdorufuRender.ghostBlock(wow.getKey().getX(), wow.getKey().getY(), wow.getKey().getZ(), 1.0f, 0.0f, 0.0f, 0.5f);
            }
        }
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e) {
        BlakeIsMyBoyfriendMap.put(new Coordinate(e.getBlockPos().x, e.getBlockPos().y, e.getBlockPos().z, AdorufuMod.minecraft.player.dimension), false);
    }
    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e) {
        if (this.isEnabled()) {
            if (e.getRecievedPacket() instanceof SPacketSpawnObject) {
                SPacketSpawnObject pck = (SPacketSpawnObject) e.getRecievedPacket();
                if (pck.getEntityID() == 2) {
                    int[] coord = new int[]{(int)pck.getX(), (int)pck.getY(), (int)pck.getZ()};
                    for (HashMap.Entry<Coordinate, Boolean> fugg : BlakeIsMyBoyfriendMap.entrySet()) {
                        if (!fugg.getValue() && between(fugg.getKey().getX(), coord[0], 0.25d, 0.25d) &&
                                between(fugg.getKey().getY(),coord[1], 0.25d, 0.25d) &&
                                between(fugg.getKey().getZ(),coord[2], 0.25d, 0.25d)){
                            BlakeIsMyBoyfriendMap.put(fugg.getKey(), true);
                        }
                    }
                }
            }
        }
    }
    private static boolean between(int i, int coord, double min, double max) {
        if (coord < 0) {
            coord--;
        }
        return i >= (coord - min) && i <= (coord + max);
    }
}
class Coordinate {
    private int x,y,z,dim;

    public Coordinate(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    public int getDim() {
        return dim;
    }
}
