package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 11/08/2018 at 3:18 PM
 **/
public class ModuleNoSlow extends XdolfModule {
    public ModuleNoSlow() {
        super("NoSlow", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (XdolfMod.minecraft.player.isHandActive()) {
            XdolfMod.minecraft.player.moveForward *= 2;
            XdolfMod.minecraft.player.moveStrafing *= 2;
        }
    }
}
