package com.sasha.xdolf.module.modules.hudelements;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
@ModuleInfo(description = "Renders the tickrate on the HUD")
public class ModuleTPS extends XdolfModule {
    public ModuleTPS() {
        super("TPS", XdolfCategory.GUI, true);
    }

    @Override
    public void onTick(){
        if (XdolfMod.minecraft.currentScreen != null){

        }
    }
}
