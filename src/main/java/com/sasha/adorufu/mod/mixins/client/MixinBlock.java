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

package com.sasha.adorufu.mod.mixins.client;

import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.modules.ModuleXray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by Sasha on 11/08/2018 at 12:53 PM
 **/
@Mixin(value = Block.class, priority = 999)
public abstract class MixinBlock {

    @Inject(method = "isOpaqueCube", at = @At("HEAD") , cancellable = true)
    public void isOpaqueCube(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if (!Manager.Module.moduleRegistry.isEmpty() && Manager.Module.moduleRegistry.get(1).isEnabled()) {
            info.setReturnValue(true);
        }
        if (!Manager.Module.moduleRegistry.isEmpty() && Manager.Module.moduleRegistry.get(0).isEnabled() ) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(state.getBlock()));
        }
    }
    @Inject(method = "isFullBlock", at = @At("HEAD") , cancellable = true)
    public void isFullBlock(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if ((Manager.Module.moduleRegistry.get(0).isEnabled() || Manager.Module.moduleRegistry.get(1).isEnabled()) && !Manager.Module.moduleRegistry.isEmpty()) {
            info.setReturnValue(false);
        }
    }
    @Inject(method = "isFullCube", at = @At("HEAD") , cancellable = true)
    public void isFullCube(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if (!Manager.Module.moduleRegistry.isEmpty() && Manager.Module.moduleRegistry.get(1).isEnabled())     {
            info.setReturnValue(true);
        }
        if (Manager.Module.moduleRegistry.get(0).isEnabled() && !Manager.Module.moduleRegistry.isEmpty()) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(state.getBlock()));
        }
    }
    @Inject(method = "getRenderType", at = @At("HEAD") , cancellable = true)
    public void getRenderType(IBlockState state, CallbackInfoReturnable<EnumBlockRenderType> info) {
        if (Manager.Module.moduleRegistry.get(0).isEnabled() && !Manager.Module.moduleRegistry.isEmpty()) {
            if (!state.getBlock().isNormalCube(state) && !ModuleXray.xrayBlocks.contains(state.getBlock())) {
                info.setReturnValue(EnumBlockRenderType.INVISIBLE);
            }
        }
    }
    @Inject(method = "shouldSideBeRendered", at = @At("HEAD") , cancellable = true)
    public void shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> info) {
        if (Manager.Module.moduleRegistry.get(0).isEnabled() ) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(blockState.getBlock()));
        }
    }

}
