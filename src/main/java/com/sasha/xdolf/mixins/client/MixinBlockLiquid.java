package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.module.modules.ModuleJesus;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.sasha.xdolf.module.modules.ModuleJesus.WATER_AABB;
import static net.minecraft.block.Block.NULL_AABB;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {
    @Inject(method = "getCollisionBoundingBox", at = @At("HEAD"), cancellable = true)
    public void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> info) {
        info.setReturnValue(ModuleJesus.INSTANCE.doJesus() ? WATER_AABB : NULL_AABB);
        info.cancel();
    }
}