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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.simplesettings.annotation.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.MovementInput;

@FeatureInfo(description = "Speedhack for ridable animals")
public class EntitySpeedFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    @Setting
    public static double speed = 2.0;

    public EntitySpeedFeature() {
        super("EntitySpeed", AsunaCategory.MOVEMENT);
    }

    private static void speedEntity(Entity entity) {
        if (entity instanceof EntityLlama) {
            entity.rotationYaw = AsunaMod.minecraft.player.rotationYaw;
            ((EntityLlama) entity).rotationYawHead = AsunaMod.minecraft.player.rotationYawHead;
        }
        MovementInput movementInput = AsunaMod.minecraft.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = AsunaMod.minecraft.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            entity.motionX = 0.0D;
            entity.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }
            entity.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            entity.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
            if (entity instanceof EntityMinecart) {
                EntityMinecart em = (EntityMinecart) entity;
                em.setVelocity((forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F))), em.motionY, (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F))));
            }
        }
    }

    @Override
    public void onTick() {
        if (!this.isEnabled())
            return;
        this.setSuffix(speed + "");
        if (AsunaMod.minecraft.player.ridingEntity != null) {
            Entity theEntity = AsunaMod.minecraft.player.ridingEntity;
            speedEntity(AsunaMod.minecraft.player.ridingEntity);
            for (Entity e : AsunaMod.minecraft.world.getLoadedEntityList()) {
                if (e.riddenByEntities.contains(theEntity)) {
                    for (Entity riddenByEntity : e.riddenByEntities) {
                        speedEntity(riddenByEntity);
                    }
                }
            }
        }
    }
}
