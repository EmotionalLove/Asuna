package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.misc.XdolfMath;
import com.sasha.xdolf.module.ModuleManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@CommandInfo(description = "Set a module's keybind", syntax = {"<module> <key>", "<module> <'none'>"})
public class BindCommand extends XdolfCommand {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void onCommand() {
        super.onCommand();
        if (this.getArguments() == null || this.getArguments().length != 2){
            XdolfMod.logErr(false, "Arguments required! Try \"-help command bind\"");
            return;
        }
        if (XdolfMath.determineKeyCode(this.getArguments()[1]) == null){
            XdolfMod.logErr(false, "That's not a valid key!");
            return;
        }
        AtomicBoolean found = new AtomicBoolean(false);
        ModuleManager.moduleRegistry.forEach(mod -> {
            if (mod.getModuleName().equalsIgnoreCase(this.getArguments()[0])){
                mod.setKeyBind(XdolfMath.determineKeyCode(this.getArguments()[1]));
                XdolfMod.logMsg(false, "Changed " + mod.getModuleName() + "'s keybind!");
                XdolfMod.scheduler.schedule(() -> {
                    try {
                        XdolfMod.DATA_MANAGER.saveModuleBind(mod);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 0, TimeUnit.NANOSECONDS);
                found.set(true);
                return;
            }
        });
        if (!found.get()) {
            XdolfMod.logErr(false, "Couldn't find the specified module.");
        }
    }
}
