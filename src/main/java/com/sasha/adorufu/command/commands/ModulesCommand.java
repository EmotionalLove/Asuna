package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@SimpleCommandInfo(description = "Used for debugging. To be removed", syntax = {""})
public class ModulesCommand extends SimpleCommand {
    public ModulesCommand() {
        super("modules");
    }

    public void onCommand(){
        ModuleManager.moduleRegistry.forEach(module -> AdorufuMod.logMsg(false, module.getModuleName() + (module.isEnabled() ? "\247aenabled" : "\247cdisabled")
        + " " + (module.isRenderable() ? "\247arenderable" : "\247crenderable")));
    }
}
