package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

public class ModuleAntiAFK extends AdorufuModule {
    private int timer=0;
    public ModuleAntiAFK() {
        super("AntiAFK", AdorufuCategory.MISC, false);
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
                AdorufuMod.minecraft.clickMouse();
                timer = 0;
            }
        }
    }
}
