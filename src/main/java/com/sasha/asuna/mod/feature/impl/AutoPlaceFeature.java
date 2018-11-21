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
import com.sasha.asuna.mod.misc.TPS;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class AutoPlaceFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public AutoPlaceFeature() {
        super("AutoPlace", AsunaCategory.MOVEMENT);
    }

    @Override
    public void onTick() {
        ItemStack stack = AsunaMod.minecraft.player.getHeldItem(EnumHand.MAIN_HAND);
        Block inHandBlock = Block.getBlockFromItem(stack.getItem());
        if (inHandBlock != Blocks.AIR) {
            RayTraceResult rayTraceResult = AsunaMod.minecraft.objectMouseOver;
            if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) return; // make sure we're looking at a block
            IBlockState state = AsunaMod.minecraft.world.getBlockState(rayTraceResult.getBlockPos());
            if (state.getMaterial() == inHandBlock.getMaterial(inHandBlock.defaultBlockState) || state.getBlock() instanceof BlockContainer)
                return;
            if (AsunaMod.minecraft.rightClickDelayTimer > 0 || !TPS.isServerResponding())
                return;
            if (rayTraceResult.sideHit != EnumFacing.UP) {
                if (state.getMaterial() == inHandBlock.getMaterial(inHandBlock.defaultBlockState)) {
                    AsunaMod.minecraft.rightClickMouse();
                    return;
                }
            }
            AsunaMod.minecraft.rightClickMouse();
        }

    }
}
