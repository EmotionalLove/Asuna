package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
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
        AdorufuMod.minecraft.player.removePotionEffect(Potion.getPotionById(16));
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        PotionEffect p = new PotionEffect(Potion.getPotionById(16), 999999999, 2, true, false);
        AdorufuMod.minecraft.player.addPotionEffect(p);
    }
}
