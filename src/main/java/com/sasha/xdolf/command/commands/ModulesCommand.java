package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;

/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
public class ModulesCommand extends XdolfCommand {
    public ModulesCommand() {
        super("modules");
    }

    public void onCommand(){
        ModuleManager.moduleRegistry.forEach(module -> XdolfMod.logMsg(false, module.getModuleName() + (module.isEnabled() ? "\247aenabled" : "\247cdisabled")
        + " " + (module.isRenderable() ? "\247arenderable" : "\247crenderable")));
    }
}
