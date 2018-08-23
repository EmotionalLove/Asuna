package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MovementInput;

import java.io.IOException;

@ModuleInfo(description = "Speedhack for ridable animals")
public class ModuleEntitySpeed extends AdorufuModule {
    public static float speed;
    public ModuleEntitySpeed() {
        super("EntitySpeed", AdorufuCategory.MOVEMENT, false);
        try {
            speed = (float)AdorufuMod.DATA_MANAGER.loadSomeGenericValue("Adorufu.values", "entityspeed", 2.5f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if(!this.isEnabled())
            return;
        this.setSuffix(speed + "");
        if (AdorufuMod.minecraft.player.ridingEntity != null) {
            Entity theEntity = AdorufuMod.minecraft.player.ridingEntity;
            speedEntity(AdorufuMod.minecraft.player.ridingEntity);
            for (Entity e : AdorufuMod.minecraft.world.getLoadedEntityList()) {
                if (e.riddenByEntities.contains(theEntity)) {
                    for (Entity riddenByEntity : e.riddenByEntities) {
                        speedEntity(riddenByEntity);
                    }
                }
            }
        }
    }
    private static void speedEntity(Entity entity) {
        MovementInput movementInput = AdorufuMod.minecraft.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = AdorufuMod.minecraft.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            entity.motionX = 0.0D;
            entity.motionZ = 0.0D;
        }
        else {
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
}
