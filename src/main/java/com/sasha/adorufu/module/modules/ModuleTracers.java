package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.misc.AdorufuRender;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 10/08/2018 at 8:55 AM
 **/
@ModuleInfo(description = "Draws lines to nearby players.")
public class ModuleTracers extends AdorufuModule {
    public static int i;
    public ModuleTracers() {
        super("Tracers", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable(){
        i = 0;
    }
    @Override
    public void onRender(){
        if (this.isEnabled()) {
            i = AdorufuRender.tracers();
        }
    }

    @Override
    public void onTick() {

    }
}
