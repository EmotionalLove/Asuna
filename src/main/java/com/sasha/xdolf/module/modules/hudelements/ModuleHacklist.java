package com.sasha.xdolf.module.modules.hudelements;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
public class ModuleHacklist extends XdolfModule {
    public ModuleHacklist() {
        super("Hacklist", XdolfCategory.GUI, true);
    }

    @Override
    public void onTick(){
        if (XdolfMod.mc.currentScreen != null){

        }
    }
}
