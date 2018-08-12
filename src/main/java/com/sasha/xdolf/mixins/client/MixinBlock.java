package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.CollisionBoxEvent;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleXray;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.Block.NULL_AABB;

/**
 * Created by Sasha on 11/08/2018 at 12:53 PM
 **/
@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow @Final public BlockStateContainer blockState;
    /**
     * @author Sasha Stevens
     * @reason because i need to do the event thingy
     */

    @Inject(method = "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"
    , at = @At("HEAD"), cancellable = true)
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_, CallbackInfo info) {
        AxisAlignedBB blockBox = state.getCollisionBoundingBox(worldIn, pos);
        CollisionBoxEvent event = new CollisionBoxEvent(this.blockState.getBlock(), pos, blockBox); //todo asuna
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        //this.addCollisionBoxToList(event.getPos(), entityBox , collidingBoxes, event.getAabb())
        if (event.getAabb() != NULL_AABB)
        {
            AxisAlignedBB axisalignedbb = event.getAabb().offset(event.getPos());

            if (entityBox.intersects(axisalignedbb))
            {
                collidingBoxes.add(axisalignedbb);
            }
        }
        info.cancel();

    }


    @Inject(method = "isOpaqueCube", at = @At("HEAD") , cancellable = true)
    public void isOpaqueCube(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if (!ModuleManager.moduleRegistry.isEmpty() && ModuleManager.moduleRegistry.get(0).isEnabled() ) {
            info.setReturnValue(ModuleXray.xrayBlocks.contains(state.getBlock()));
        }
    }
    @Inject(method = "isFullBlock", at = @At("HEAD") , cancellable = true)
    public void isFullBlock(IBlockState state, CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.moduleRegistry.get(0).isEnabled() && !ModuleManager.moduleRegistry.isEmpty()) {
            info.setReturnValue(false);
        }
    }
    @Inject(method = "isFullCube", at = @At("HEAD") , cancellable = true)
    public void isFullCube(IBlockState state, CallbackInfoReturnable<Boolean> info) {
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
