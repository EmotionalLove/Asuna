package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleClientIgnore;

import static com.sasha.xdolf.XdolfMod.logMsg;
import static com.sasha.xdolf.module.modules.ModuleClientIgnore.ignorelist;

/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@CommandInfo(description = "View your ignorelist", syntax = {"", "[player]"})
public class IgnorelistCommand extends XdolfCommand {
    public IgnorelistCommand() {
        super("ignorelist");
    }

    public void onCommand(){
        if (this.getArguments() == null) {
            StringBuilder builder = new StringBuilder();
            logMsg(false, "Listing \247l" + ignorelist.size() + " \247r\2477ignored players:");
            for (int i = 0; i < ignorelist.size(); i++) {
                if (i == 0) {
                    builder.append(ignorelist.get(i));
                    continue;
                }
                builder.append(", ").append(ignorelist.get(i));
            }
            logMsg(builder.toString());
            return;
        }
        String player = this.getArguments()[0];
        logMsg(false, player + " is" + (ignorelist.contains(player) ? "" : " not") + " ignored.");
    }
}
