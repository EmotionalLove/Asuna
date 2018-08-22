package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.modules.ModuleXray;
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
        if (!ModuleManager.moduleRegistry.isEmpty() && ModuleManager.moduleRegistry.get(1).isEnabled()) {
            info.setReturnValue(true);
        }
        if (!ModuleManager.moduleRegistry.isEmpty() && ModuleManager.moduleRegistry.get(0).isEnabled() ) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(state.getBlock()));
        }
    }
    @Inject(method = "isFullBlock", at = @At("HEAD") , cancellable = true)
    public void isFullBlock(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if ((ModuleManager.moduleRegistry.get(0).isEnabled() || ModuleManager.moduleRegistry.get(1).isEnabled()) && !ModuleManager.moduleRegistry.isEmpty()) {
            info.setReturnValue(false);
        }
    }
    @Inject(method = "isFullCube", at = @At("HEAD") , cancellable = true)
    public void isFullCube(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if (!ModuleManager.moduleRegistry.isEmpty() && ModuleManager.moduleRegistry.get(1).isEnabled())     {
            info.setReturnValue(true);
        }
        if (ModuleManager.moduleRegistry.get(0).isEnabled() && !ModuleManager.moduleRegistry.isEmpty()) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(state.getBlock()));
        }
    }
    @Inject(method = "getRenderType", at = @At("HEAD") , cancellable = true)
    public void getRenderType(IBlockState state, CallbackInfoReturnable<EnumBlockRenderType> info) {
        if (ModuleManager.moduleRegistry.get(0).isEnabled() && !ModuleManager.moduleRegistry.isEmpty()) {
            if (!state.getBlock().isNormalCube(state) && !ModuleXray.xrayBlocks.contains(state.getBlock())) {
                info.setReturnValue(EnumBlockRenderType.INVISIBLE);
            }
        }
    }
    @Inject(method = "shouldSideBeRendered", at = @At("HEAD") , cancellable = true)
    public void shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.moduleRegistry.get(0).isEnabled() ) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(blockState.getBlock()));
        }
    }

}
