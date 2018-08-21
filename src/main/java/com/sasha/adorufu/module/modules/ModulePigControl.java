package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;

@ModuleInfo(description = "Control pigs without carrots")
public class ModulePigControl extends AdorufuModule {
    public ModulePigControl() {
        super("PigControl", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
}
