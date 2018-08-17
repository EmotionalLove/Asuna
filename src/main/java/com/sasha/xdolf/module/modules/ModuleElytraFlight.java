package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

public class ModuleElytraFlight extends XdolfModule {
    public ModuleElytraFlight() {
        super("ElytraFlight", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            if (XdolfMod.minecraft.player.isElytraFlying()) {
                XdolfMod.minecraft.player.capabilities.isFlying = true;
            }
        }
    }
    @Override
    public void onEnable() {
        //
    }
    @Override
    public void onDisable() {
        XdolfMod.minecraft.player.capabilities.isFlying = false;
    }
}
