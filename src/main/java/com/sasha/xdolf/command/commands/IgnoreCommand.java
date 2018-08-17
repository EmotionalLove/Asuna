package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleClientIgnore;

import java.io.IOException;

/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@CommandInfo(description = "Ignore or unignore a player. The player's name is CaSe SeNsiTve", syntax = {"<player>"})
public class IgnoreCommand extends XdolfCommand {
    public IgnoreCommand() {
        super("ignore");
    }

    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length != 1) {
            XdolfMod.logErr(false, "Too few or too many arguments. Try -help command ignore");
            return;
        }
        if (!ModuleClientIgnore.ignorelist.contains(this.getArguments()[0])) {
            ModuleClientIgnore.ignorelist.add(this.getArguments()[0]);
            XdolfMod.logMsg(false, this.getArguments()[0] + " ignored.");
            try {
                XdolfMod.DATA_MANAGER.saveIgnorelist(ModuleClientIgnore.ignorelist);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        ModuleClientIgnore.ignorelist.remove(this.getArguments()[0]);
        XdolfMod.logMsg(false, this.getArguments()[0] + " unignored.");
        try {
            XdolfMod.DATA_MANAGER.saveIgnorelist(ModuleClientIgnore.ignorelist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
