package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
@ModuleInfo(description = "Lets you see in the dark.")
public class ModuleNightVision extends XdolfModule {

    public ModuleNightVision() {
        super("NightVision", XdolfCategory.RENDER, false);
    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.player.removePotionEffect(Potion.getPotionById(16));
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        PotionEffect p = new PotionEffect(Potion.getPotionById(16), 999999999, 2, true, false);
        XdolfMod.minecraft.player.addPotionEffect(p);
    }
}
