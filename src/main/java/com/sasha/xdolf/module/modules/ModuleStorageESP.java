package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.misc.XdolfRender;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.*;

/**
 * Created by Sasha on 09/08/2018 at 7:32 PM
 **/
@ModuleInfo(description = "Draws an outline around storage containers")
public class ModuleStorageESP extends XdolfModule {
    public ModuleStorageESP() {
        super("StorageESP", XdolfCategory.RENDER, false);
    }
    @Override
    public void onRender() {
        if (!this.isEnabled())
            return;
        int i = 0;
        for (TileEntity TE : XdolfMod.minecraft.world.loadedTileEntityList) {
            if (TE instanceof TileEntityChest) {
                i++;
                if (((TileEntityChest) TE).getChestType() == BlockChest.Type.TRAP /* OwO What's this? */) {
                    XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 0.0f);
                } else {
                    XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.0f);
                }
            }
            if (TE instanceof TileEntityFurnace) {
                i++;
                XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDropper) {
                i++;
                XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDispenser) {
                i++;
                XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityShulkerBox) {
                i++;
                XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.3f);
            }
            if (TE instanceof TileEntityEnderChest) {
                i++;
                XdolfRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 1.0f);
            }
        }
        //todo int x = XdolfMod.minecraft.world.loadedTileEntityList.stream().filter(tile -> tile. )
        this.setSuffix(i+"");
    }

}
