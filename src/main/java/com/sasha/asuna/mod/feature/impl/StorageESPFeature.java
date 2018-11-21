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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaRenderableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.misc.AsunaRender;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.*;

/**
 * Created by Sasha on 09/08/2018 at 7:32 PM
 **/
@FeatureInfo(description = "Draws an outline around storage containers")
public class StorageESPFeature extends AbstractAsunaTogglableFeature implements IAsunaRenderableFeature {
    public StorageESPFeature() {
        super("StorageESP", AsunaCategory.RENDER,
                new AsunaFeatureOption<>("Chest", true),
                new AsunaFeatureOption<>("Trapped Chest", true),
                new AsunaFeatureOption<>("Ender Chest", true),
                new AsunaFeatureOption<>("Furnace", true),
                new AsunaFeatureOption<>("Dropper", true),
                new AsunaFeatureOption<>("Dispenser", true),
                new AsunaFeatureOption<>("Shulker", true),
                new AsunaFeatureOption<>("Dispenser", true),
                new AsunaFeatureOption<>("Hopper", true));
    }


    @Override
    public void onRender() {
        if (!this.isEnabled())
            return;
        int i = 0;
        for (TileEntity TE : AsunaMod.minecraft.world.loadedTileEntityList) {
            if (TE instanceof TileEntityChest) {
                i++;
                if (((TileEntityChest) TE).getChestType() == BlockChest.Type.TRAP /* OwO What's this? */) {
                    if (this.getOption("Trapped Chest")) AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 0.0f);
                } else {
                    if (this.getOption("Chest"))AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.0f);
                }
            }
            if (TE instanceof TileEntityFurnace && this.getOption("Furnace")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDropper && this.getOption("Dropper")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityDispenser && this.getOption("Dispenser")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.0f, 0.3f, 1.0f);
            }
            if (TE instanceof TileEntityShulkerBox && this.getOption("Shulker")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.3f, 0.3f);
            }
            if (TE instanceof TileEntityEnderChest && this.getOption("Ender Chest")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 1.0f, 0.0f, 1.0f);
            }
            if (TE instanceof TileEntityHopper && this.getOption("Hopper")) {
                i++;
                AsunaRender.storageESP(TE.getPos().getX(), TE.getPos().getY(), TE.getPos().getZ(), 0.3f, 0.3f, 0.3f);
            }
        }
        this.setSuffix(i + "");
    }

}
