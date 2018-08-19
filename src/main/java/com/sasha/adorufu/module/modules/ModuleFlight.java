package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 12/08/2018 at 9:04 AM
 **/
@ModuleInfo(description = "Allows you to fly. Only works on servers that don't have an anticheat.")
public class ModuleFlight extends AdorufuModule {
    public ModuleFlight() {
        super("Flight", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.player.capabilities.isFlying = false;
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        AdorufuMod.minecraft.player.capabilities.isFlying = true;
    }
}
