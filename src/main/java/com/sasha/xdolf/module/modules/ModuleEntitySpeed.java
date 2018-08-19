package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.Mod;

@ModuleInfo(description = "Speedhack for ridable animals")
public class ModuleEntitySpeed extends XdolfModule {
    static float speed = 2.5f;
    public ModuleEntitySpeed() {
        super("EntitySpeed", XdolfCategory.MOVEMENT, false);
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
        if (XdolfMod.minecraft.player.ridingEntity != null) {
            MovementInput movementInput = XdolfMod.minecraft.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = XdolfMod.minecraft.player.rotationYaw;
            if ((forward == 0.0D) && (strafe == 0.0D)) {
                XdolfMod.minecraft.player.ridingEntity.motionX = 0.0D;
                XdolfMod.minecraft.player.ridingEntity.motionZ = 0.0D;
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
                XdolfMod.minecraft.player.ridingEntity.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
                XdolfMod.minecraft.player.ridingEntity.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
            }
        }
    }
}
