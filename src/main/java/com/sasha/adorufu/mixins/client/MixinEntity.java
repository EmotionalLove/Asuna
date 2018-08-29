package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientEntityCollideEvent;
import com.sasha.adorufu.events.ClientPushOutOfBlocksEvent;
import com.sasha.adorufu.module.ModuleManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 999)
public abstract class MixinEntity {
    @Shadow public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow public abstract void resetPositionToBB();

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo info) {
        if (ModuleManager.getModule("Freecam").isEnabled()) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
        }
    }
    @Inject(method = "isInsideOfMaterial", at = @At("HEAD"), cancellable = true)
    public void isInsideOfMaterial(Material materialIn, CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.getModule("Freecam").isEnabled()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollision(Entity entityIn, CallbackInfo info) {
        ClientEntityCollideEvent event = new ClientEntityCollideEvent(entityIn);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    protected void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> info) {
        ClientPushOutOfBlocksEvent event = new ClientPushOutOfBlocksEvent(x,y,z);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.setReturnValue(false);
            info.cancel();
        }
    }
}
