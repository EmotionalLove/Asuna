package com.sasha.adorufu.module.modules.hudelements;

import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
@ModuleInfo(description = "Renders inventory statistics on the HUD")
public class ModuleInventoryStats extends AdorufuModule {
    public ModuleInventoryStats() {
        super("InventoryStats", AdorufuCategory.GUI, true);
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
