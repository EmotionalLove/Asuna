package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
public class ToggleCommand extends XdolfCommand {
    public ToggleCommand() {
        super("toggle");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null){
            XdolfMod.logErr(false, "Arguments required! \"-toggle <module>\"");
            return;
        }
        ModuleManager.moduleRegistry.forEach(mod -> {
            if (mod.getModuleName().equalsIgnoreCase(this.getArguments()[0])){
                mod.toggle();
                XdolfMod.logMsg(false, "Toggled " + mod.getModuleName());
                return;
            }
        });
        XdolfMod.logErr(false, "Couldn't find the specified module.");
    }
}
