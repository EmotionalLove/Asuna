package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "Fly like you're in creative with an elytra")
public class ModuleElytraFlight extends AdorufuModule {
    public ModuleElytraFlight() {
        super("ElytraFlight", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            if (AdorufuMod.minecraft.player.isElytraFlying()) {
                AdorufuMod.minecraft.player.capabilities.isFlying = true;
            }
        }
    }
    @Override
    public void onEnable() {
        //
    }
    @Override
    public void onDisable() {
        AdorufuMod.minecraft.player.capabilities.isFlying = false;
    }
}
