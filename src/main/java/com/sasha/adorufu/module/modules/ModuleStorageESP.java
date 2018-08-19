package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.misc.AdorufuRender;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.*;

/**
 * Created by Sasha on 09/08/2018 at 7:32 PM
 **/
@ModuleInfo(description = "Draws an outline around storage containers")
public class ModuleStorageESP extends AdorufuModule {
    public ModuleStorageESP() {
        super("StorageESP", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onRender() {
        if (!this.isEnabled())
            return;
        int i = 0;
        for (TileEntity TE : AdorufuMod.minecraft.world.loadedTileEntityList) {
            if (TE instanceof TileEntityChest) {
                i++;
                if (((TileEntityChest) TE).getChestType() == BlockChest.Type.TRAP /* OwO What's this? */) {
                    AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 0.0f);
                } else {
                    AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.0f);
                }
            }
            if (TE instanceof TileEntityFurnace) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDropper) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDispenser) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityShulkerBox) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.3f);
            }
            if (TE instanceof TileEntityEnderChest) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 1.0f);
            }
        }
        //todo int x = AdorufuMod.minecraft.world.loadedTileEntityList.stream().filter(tile -> tile. )
        this.setSuffix(i+"");
    }

    @Override
    public void onTick() {

    }

}
