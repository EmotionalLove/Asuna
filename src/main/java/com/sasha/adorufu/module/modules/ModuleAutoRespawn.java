package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by Sasha on 11/08/2018 at 6:32 PM
 **/
@ModuleInfo(description = "Automatically respawn upon death")
public class ModuleAutoRespawn extends AdorufuModule {
    public ModuleAutoRespawn() {
        super("AutoRespawn", AdorufuCategory.COMBAT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if(!this.isEnabled())
            return;
        if(AdorufuMod.minecraft.currentScreen instanceof GuiGameOver) {
            AdorufuMod.minecraft.player.respawnPlayer();
            AdorufuMod.minecraft.displayGuiScreen((GuiScreen)null);
        }
    }
}
