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

package com.sasha.adorufu.mod.mixin.client;

import com.sasha.adorufu.mod.feature.impl.CarpetWalkerFeature;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCarpet.class)
public class MixinBlockCarpet {

    @Inject(method = "getBoundingBox",at = @At("HEAD"), cancellable = true)
    public void getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> info) {
        if (Manager.Feature.isFeatureEnabled(CarpetWalkerFeature.class)) {
            info.setReturnValue(Block.NULL_AABB);
            info.cancel();
        }
    }

}
