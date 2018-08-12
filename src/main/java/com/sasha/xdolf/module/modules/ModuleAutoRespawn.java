package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by Sasha on 11/08/2018 at 6:32 PM
 **/
public class ModuleAutoRespawn extends XdolfModule {
    public ModuleAutoRespawn() {
        super("AutoRespawn", XdolfCategory.COMBAT, false);
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
        if(XdolfMod.minecraft.currentScreen instanceof GuiGameOver) {
            XdolfMod.minecraft.player.respawnPlayer();
            XdolfMod.minecraft.displayGuiScreen((GuiScreen)null);
        }
    }
}
