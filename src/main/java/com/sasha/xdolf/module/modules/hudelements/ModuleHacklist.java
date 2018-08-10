package com.sasha.xdolf.module.modules.hudelements;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
@ModuleInfo(description = "Renders the hack arraylist on the HUD")
public class ModuleHacklist extends XdolfModule {
    public ModuleHacklist() {
        super("Hacklist", XdolfCategory.GUI, true);
    }
}
