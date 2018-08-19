package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.util.MovementInput;

@ModuleInfo(description = "Speedhack for ridable animals")
public class ModuleEntitySpeed extends AdorufuModule {
    static float speed = 2.5f;
    public ModuleEntitySpeed() {
        super("EntitySpeed", AdorufuCategory.MOVEMENT, false);
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
            MovementInput movementInput = AdorufuMod.minecraft.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = AdorufuMod.minecraft.player.rotationYaw;
            if ((forward == 0.0D) && (strafe == 0.0D)) {
                AdorufuMod.minecraft.player.ridingEntity.motionX = 0.0D;
                AdorufuMod.minecraft.player.ridingEntity.motionZ = 0.0D;
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
                AdorufuMod.minecraft.player.ridingEntity.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
                AdorufuMod.minecraft.player.ridingEntity.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
            }
            if (AdorufuMod.minecraft.player.ridingEntity.ridingEntity != null) {
                MovementInput movementInput1 = AdorufuMod.minecraft.player.movementInput;
                double forward1 = movementInput.moveForward;
                double strafe1 = movementInput.moveStrafe;
                float yaw1 = AdorufuMod.minecraft.player.rotationYaw;
                if ((forward1 == 0.0D) && (strafe1 == 0.0D)) {
                    AdorufuMod.minecraft.player.ridingEntity.ridingEntity.motionX = 0.0D;
                    AdorufuMod.minecraft.player.ridingEntity.ridingEntity.motionZ = 0.0D;
                }
                else {
                    if (forward1 != 0.0D) {
                        if (strafe1 > 0.0D) {
                            yaw1 += (forward1 > 0.0D ? -45 : 45);
                        } else if (strafe < 0.0D) {
                            yaw1 += (forward1 > 0.0D ? 45 : -45);
                        }
                        strafe1 = 0.0D;
                        if (forward1 > 0.0D) {
                            forward1 = 1.0D;
                        } else if (forward1 < 0.0D) {
                            forward1 = -1.0D;
                        }
                    }
                    AdorufuMod.minecraft.player.ridingEntity.ridingEntity.motionX = (forward1 * speed * Math.cos(Math.toRadians(yaw1 + 90.0F)) + strafe1 * speed * Math.sin(Math.toRadians(yaw1 + 90.0F)));
                    AdorufuMod.minecraft.player.ridingEntity.ridingEntity.motionZ = (forward1 * speed * Math.sin(Math.toRadians(yaw1 + 90.0F)) - strafe1 * speed * Math.cos(Math.toRadians(yaw1 + 90.0F)));
                }
            }
        }
    }
}
