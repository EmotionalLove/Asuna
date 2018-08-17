package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 12/08/2018 at 9:04 AM
 **/
@ModuleInfo(description = "Allows you to fly. Only works on servers that don't have an anticheat.")
public class ModuleFlight extends XdolfModule {
    public ModuleFlight() {
        super("Flight", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.player.capabilities.isFlying = false;
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        XdolfMod.minecraft.player.capabilities.isFlying = true;
    }
}
