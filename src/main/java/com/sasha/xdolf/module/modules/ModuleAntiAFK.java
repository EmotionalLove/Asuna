package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

public class ModuleAntiAFK extends XdolfModule {
    private int timer=0;
    public ModuleAntiAFK() {
        super("AntiAFK", XdolfCategory.MISC, false);
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
            timer++;
            if (timer >= 20) {
                XdolfMod.minecraft.clickMouse();
                timer = 0;
            }
        }
    }
}
