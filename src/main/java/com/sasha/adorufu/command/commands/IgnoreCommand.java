package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.module.modules.ModuleClientIgnore;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

import java.io.IOException;

/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@SimpleCommandInfo(description = "Ignore or unignore a player. The player's name is CaSe SeNsiTve", syntax = {"<player>"})
public class IgnoreCommand extends SimpleCommand {
    public IgnoreCommand() {
        super("ignore");
    }

    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length != 1) {
            AdorufuMod.logErr(false, "Too few or too many arguments. Try -help command ignore");
            return;
        }
        if (!ModuleClientIgnore.ignorelist.contains(this.getArguments()[0])) {
            ModuleClientIgnore.ignorelist.add(this.getArguments()[0]);
            AdorufuMod.logMsg(false, this.getArguments()[0] + " ignored.");
            try {
                AdorufuMod.DATA_MANAGER.saveIgnorelist(ModuleClientIgnore.ignorelist);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        ModuleClientIgnore.ignorelist.remove(this.getArguments()[0]);
        AdorufuMod.logMsg(false, this.getArguments()[0] + " unignored.");
        try {
            AdorufuMod.DATA_MANAGER.saveIgnorelist(ModuleClientIgnore.ignorelist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
