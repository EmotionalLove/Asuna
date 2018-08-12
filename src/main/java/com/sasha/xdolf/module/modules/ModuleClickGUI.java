package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.clickgui.XdolfClickGUI;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 11/08/2018 at 10:27 AM
 **/
@ModuleInfo(description = "Displays the Clickgui")
public class ModuleClickGUI extends XdolfModule {
    public ModuleClickGUI() {
        super("ClickGUI", XdolfCategory.GUI, false);
    }

    @Override
    public void onEnable(){
        XdolfMod.minecraft.displayGuiScreen(new XdolfClickGUI());
        this.toggle();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
}
