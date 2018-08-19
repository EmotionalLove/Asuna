package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "Automatically sprint when walking")
public class ModuleAutoSprint extends AdorufuModule {
    public ModuleAutoSprint() {
        super("AutoSprint", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (!AdorufuMod.minecraft.player.isSprinting() && this.isEnabled()) {
            AdorufuMod.minecraft.player.setSprinting(true);
        }
    }
}
