package com.sasha.xdolf.module.modules.hudelements;

import com.sasha.xdolf.module.ForcefulEnable;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
@ModuleInfo(description = "Renders the Xdolf watermark on the HUD")
@ForcefulEnable
public class ModuleWatermark extends XdolfModule {
    public ModuleWatermark() {
        super("Watermark", XdolfCategory.GUI, true);
    }

    @Override
    public void onEnable() {
        
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
}
