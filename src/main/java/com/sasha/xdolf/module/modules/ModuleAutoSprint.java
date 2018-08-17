package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

public class ModuleAutoSprint extends XdolfModule {
    public ModuleAutoSprint() {
        super("AutoSprint", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (!XdolfMod.minecraft.player.isSprinting()) {
            XdolfMod.minecraft.player.setSprinting(true);
        }
    }
}
