package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;

/**
 * Created by Sasha at 3:48 PM on 8/27/2018
 */
@ModuleInfo(description = "Plays the creative mode exclusive music in other gamemodes.")
public class ModuleCreativeMusic extends AdorufuModule {
    public ModuleCreativeMusic() {
        super("CreativeMusic", AdorufuCategory.MISC, false);
    }

    @Override
    public void onEnable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onTick() {

    }
}
