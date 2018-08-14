package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.CollisionBoxEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sasha on 13/08/2018 at 5:44 PM
 **/
@Mixin(BlockStateContainer.StateImplementation.class)
public class MixinBlockStateContainer_StateImplementation {

    @Shadow @Final private Block block;

    @Inject(method = "addCollisionBoxToList", at = @At("HEAD"))
    public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185908_6_, CallbackInfo info){
        CollisionBoxEvent event = new CollisionBoxEvent(this.block, pos, entityBox);
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        pos = event.getPos();
        entityBox = event.getAabb();
        XdolfMod.logMsg("oof");
    }

}
