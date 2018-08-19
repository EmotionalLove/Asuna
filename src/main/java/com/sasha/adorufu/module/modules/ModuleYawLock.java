package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "Lock your yaw to a specified angle")
public class ModuleYawLock extends AdorufuModule {

    public static int yawDegrees = 90;

    public ModuleYawLock() {
        super("YawLock", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            AdorufuMod.minecraft.player.rotationYaw = yawDegrees;
            this.setSuffix(yawDegrees + "\u00b0");
        }
    }
}
