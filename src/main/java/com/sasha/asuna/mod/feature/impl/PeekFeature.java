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
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class PeekFeature extends AbstractAsunaTogglableFeature {

    public PeekFeature() {
        super("Peek", AsunaCategory.NA);
    }

    @Override
    public void onEnable() {
        ItemStack itemStack = AsunaMod.minecraft.player.itemStackMainHand;
        if (itemStack.getItem() instanceof ItemShulkerBox) {
            TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            entityBox.blockType = ((ItemShulkerBox) itemStack.getItem()).getBlock();
            entityBox.setWorld(AsunaMod.minecraft.world);
            entityBox.readFromNBT(itemStack.getTagCompound().getCompoundTag("BlockEntityTag"));
            entityBox.setCustomName("Asuna Shulker Peek");
            AsunaMod.minecraft.player.displayGUIChest(entityBox);
            this.toggleState();
            return;
        }
        AsunaMod.logErr(false, "You must be holding a shulker box!");
        this.toggleState();
    }

}
