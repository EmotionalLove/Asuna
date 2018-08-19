package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.AdorufuClickGUI;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.PostToggleExec;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 11/08/2018 at 10:27 AM
 **/
@PostToggleExec
@ModuleInfo(description = "Displays the Clickgui")
public class ModuleClickGUI extends AdorufuModule {
    public ModuleClickGUI() {
        super("ClickGUI", AdorufuCategory.GUI, false);
    }

    @Override
    public void onEnable(){
        AdorufuMod.minecraft.displayGuiScreen(new AdorufuClickGUI());
        this.toggle();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
}
