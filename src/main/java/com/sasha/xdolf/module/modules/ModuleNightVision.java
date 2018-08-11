package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
public class ModuleNightVision extends XdolfModule {
    private float oldGamma;

    public ModuleNightVision() {
        super("NightVision", XdolfCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        oldGamma = XdolfMod.minecraft.gameSettings.gammaSetting;
    }
    @Override
    public void onDisable() {
        XdolfMod.minecraft.gameSettings.gammaSetting = oldGamma;
    }

    @Override
    public void onTick() {
        XdolfMod.minecraft.gameSettings.gammaSetting = 8888f;
    }

}
