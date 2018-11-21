/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;

public class AutoEatFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    private static boolean eating = false;
    private static boolean checked = false;
    private static int prevSlot = 0;

    public AutoEatFeature() {
        super("AutoEat", AsunaCategory.MISC,
                new AsunaFeatureOptionBehaviour(true),
                new AsunaFeatureOption<>("Priority gapple", false),
                new AsunaFeatureOption<>("Conserve  gapple", true));
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
        if (AsunaMod.minecraft.player.getFoodStats().getFoodLevel() <= 8) {
            // we need to eat
            for (int s = 0; s <= 8; s++) {
                if (AsunaMod.minecraft.player.inventory.getStackInSlot(s).getItemUseAction() == EnumAction.EAT) {
                    // we can eat this item
                    if (this.getOption("Conserve gapple") && AsunaMod.minecraft.player.inventory.getStackInSlot(s).getItem() == Items.GOLDEN_APPLE) {
                        continue;

                    } else if (this.getOption("Priority gapple") && !checked && AsunaMod.minecraft.player.inventory.getStackInSlot(s).getItem() != Items.GOLDEN_APPLE) {
                        continue;
                    }
                    if (!eating) prevSlot = AsunaMod.minecraft.player.inventory.currentItem;
                    eating = true;
                    checked = false;
                    AsunaMod.minecraft.player.inventory.currentItem = s;
                    AsunaMod.setPressed(AsunaMod.minecraft.gameSettings.keyBindUseItem, true);
                    return;
                }
            }
            if (this.getOption("Priority gapple")) {
                checked = true;
            }
            return;
        }
        if (eating) {
            AsunaMod.setPressed(AsunaMod.minecraft.gameSettings.keyBindUseItem, false);
            AsunaMod.minecraft.player.inventory.currentItem = prevSlot;
            eating = false;
        }
    }
}
