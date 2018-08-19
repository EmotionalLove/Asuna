package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;

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
