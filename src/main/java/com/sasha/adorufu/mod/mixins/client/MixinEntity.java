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

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.events.client.ClientEntityCollideEvent;
import com.sasha.adorufu.mod.events.client.ClientPushOutOfBlocksEvent;
import com.sasha.adorufu.mod.events.client.EntityMoveEvent;
import com.sasha.adorufu.mod.events.playerclient.PlayerKnockbackEvent;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
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
    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void resetPositionToBB();

    @Shadow
    public abstract String getName();

    @Shadow
    public double motionX;

    @Shadow
    public double motionY;

    @Shadow
    public double motionZ;

    @Shadow
    public boolean onGround;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public Entity ridingEntity;

    @Shadow
    public int entityId;

    @Shadow
    public World world;

    @Shadow
    public float stepHeight;

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo info) {
        EntityMoveEvent event = new EntityMoveEvent(this.world, this.entityId, type, x, y, z);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) return;
        x = event.getX();y = event.getY();z = event.getZ();
    }

    @Inject(method = "isInsideOfMaterial", at = @At("HEAD"), cancellable = true)
    public void isInsideOfMaterial(Material materialIn, CallbackInfoReturnable<Boolean> info) {
        if (Manager.Module.getModule("Freecam").isEnabled()) {
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
        ClientPushOutOfBlocksEvent event = new ClientPushOutOfBlocksEvent(x, y, z);
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
    public void setVelocity(double x, double y, double z) {
        PlayerKnockbackEvent event = new PlayerKnockbackEvent(x, y, z);
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
