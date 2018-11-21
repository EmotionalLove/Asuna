/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by Sasha on 11/08/2018 at 8:27 PM
 **/
@FeatureInfo(description = "Automatically moves a totem into your offhand if it's empty")
public class AutoTotemFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {
    public AutoTotemFeature() {
        super("AutoTotem", AsunaCategory.COMBAT);
    }


    @Override
    public void onTick() {
        if (!this.isEnabled())
            return;
        ItemStack offhand = AsunaMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        int i = 0;
        for (int x = 9; x <= 44; x++) {
            ItemStack stack = AsunaMod.minecraft.player.inventory.getStackInSlot(x);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                i++;
            }
        }
        this.setSuffix(i + "");
        if (offhand.getItem() != Items.TOTEM_OF_UNDYING) {
            for (int x = 9; x <= 44; x++) {
                ItemStack stack = AsunaMod.minecraft.player.inventory.getStackInSlot(x);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING && AsunaMod.minecraft.currentScreen == null /* don't move totems if the inventory is open */) {
                    AsunaMod.minecraft.playerController.windowClick(0, x, 0, ClickType.PICKUP, AsunaMod.minecraft.player);
                    AsunaMod.minecraft.playerController.windowClick(0, 45, 0, ClickType.PICKUP, AsunaMod.minecraft.player);
                    break;
                }
            }
        }

    }
}
