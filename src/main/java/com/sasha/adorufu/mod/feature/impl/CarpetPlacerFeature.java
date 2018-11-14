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
import com.sasha.adorufu.mod.feature.IAdorufuTickableFeature;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class CarpetPlacerFeature extends AbstractAdorufuTogglableFeature implements IAdorufuTickableFeature {

    public CarpetPlacerFeature() {
        super("AutoCarpetPlace", AdorufuCategory.MOVEMENT);
    }

    @Override public void onTick() {
        if (AdorufuMod.minecraft.player.getHeldItem(EnumHand.MAIN_HAND).getTranslationKey().contains("tile.woolCarpet")) {
            RayTraceResult rayTraceResult = AdorufuMod.minecraft.objectMouseOver;
            if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) return; // make sure we're looking at a block
            if (AdorufuMod.minecraft.world.getBlockState(rayTraceResult.getBlockPos()).getMaterial() == Material.CARPET)
                return;
            if (rayTraceResult.sideHit != EnumFacing.UP || AdorufuMod.minecraft.rightClickDelayTimer > 0) return; // make sure it's at the top
            //BlockPos up = rayTraceResult.getBlockPos().up();
            //AdorufuMath.placeBlock(up);
            AdorufuMod.minecraft.rightClickMouse();
        }

    }
}
