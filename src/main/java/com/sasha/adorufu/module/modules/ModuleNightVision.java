package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.PostToggleExec;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
@PostToggleExec
@ModuleInfo(description = "Lets you see in the dark.")
public class ModuleNightVision extends AdorufuModule {

    public ModuleNightVision() {
        super("NightVision", AdorufuCategory.RENDER, false);
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
