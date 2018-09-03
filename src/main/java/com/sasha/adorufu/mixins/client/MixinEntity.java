package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientEntityCollideEvent;
import com.sasha.adorufu.events.ClientPushOutOfBlocksEvent;
import com.sasha.adorufu.events.PlayerKnockbackEvent;
import com.sasha.adorufu.module.ModuleManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

    @Shadow public abstract String getName();

    @Shadow public double motionX;

    @Shadow public double motionY;

    @Shadow public double motionZ;

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

    /**
     * @author Sasha Stevens
     * @reason PlayerKnockbackEvent
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public void setVelocity(double x, double y, double z)
    {
        PlayerKnockbackEvent event = new PlayerKnockbackEvent(x,y,z);
        // i would use if (this instanceof EntityPlayerSP but mixins dont allow this soooooo //
        if (this.getName().equals(AdorufuMod.minecraft.player.getName())) { // dont suppress knockback on other entities.
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        this.motionX = event.getMotionX();
        this.motionY = event.getMotionY();
        this.motionZ = event.getMotionZ();
    }
}
