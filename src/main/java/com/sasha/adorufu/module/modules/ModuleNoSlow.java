package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 11/08/2018 at 3:18 PM
 **/
@ModuleInfo(description = "Don't slow down whilst eating.") //todo
public class ModuleNoSlow extends AdorufuModule {
    public ModuleNoSlow() {
        super("NoSlow", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (AdorufuMod.minecraft.player.isHandActive()) {
            AdorufuMod.minecraft.player.moveForward *= 2;
            AdorufuMod.minecraft.player.moveStrafing *= 2;
        }
    }
}
