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

package com.sasha.adorufu.mod.feature.impl;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.AbstractAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.AdorufuCategory;
import com.sasha.adorufu.mod.feature.IAdorufuRenderableFeature;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOption;
import com.sasha.adorufu.mod.misc.AdorufuRender;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.*;

/**
 * Created by Sasha on 09/08/2018 at 7:32 PM
 **/
@FeatureInfo(description = "Draws an outline around storage containers")
public class StorageESPFeature extends AbstractAdorufuTogglableFeature implements IAdorufuRenderableFeature {
    public StorageESPFeature() {
        super("StorageESP", AdorufuCategory.RENDER,
                new AdorufuFeatureOption<>("Chest", true),
                new AdorufuFeatureOption<>("Trapped Chest", true),
                new AdorufuFeatureOption<>("Ender Chest", true),
                new AdorufuFeatureOption<>("Furnace", true),
                new AdorufuFeatureOption<>("Dropper", true),
                new AdorufuFeatureOption<>("Dispenser", true),
                new AdorufuFeatureOption<>("Shulker", true),
                new AdorufuFeatureOption<>("Dispenser", true),
                new AdorufuFeatureOption<>("Hopper", true));
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
                    if (this.getOption("Trapped Chest")) AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 0.0f);
                } else {
                    if (this.getOption("Chest"))AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.0f);
                }
            }
            if (TE instanceof TileEntityFurnace && this.getOption("Furnace")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDropper && this.getOption("Dropper")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDispenser && this.getOption("Dispenser")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityShulkerBox && this.getOption("Shulker")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.3f);
            }
            if (TE instanceof TileEntityEnderChest && this.getOption("Ender Chest")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 1.0f);
            }
            if (TE instanceof TileEntityHopper && this.getOption("Hopper")) {
                i++;
                AdorufuRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.3f, 0.3f, 0.3f);
            }
        }
        this.setSuffix(i + "");
    }

}
