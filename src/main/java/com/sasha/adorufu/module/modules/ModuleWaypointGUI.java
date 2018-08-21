package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.AdorufuClickGUI;
import com.sasha.adorufu.gui.waypointgui.WaypointGUI;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.PostToggleExec;

/**
 * Created by Sasha on 11/08/2018 at 10:27 AM
 **/
@PostToggleExec
@ModuleInfo(description = "Displays the WaypointGUI")
public class ModuleWaypointGUI extends AdorufuModule {
    public ModuleWaypointGUI() {
        super("WaypointGUI", AdorufuCategory.GUI, false);
    }

    @Override
    public void onEnable(){
        AdorufuMod.minecraft.displayGuiScreen(new WaypointGUI());
        this.toggle();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
}
