package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.command.CommandInfo;
import com.sasha.simplecmdsys.SimpleCommand;

import static com.sasha.adorufu.AdorufuMod.logMsg;
import static com.sasha.adorufu.module.modules.ModuleClientIgnore.ignorelist;
import com.sasha.simplecmdsys.SimpleCommandInfo;
/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@SimpleCommandInfo(description = "View your ignorelist", syntax = {"", "[player]"})
public class IgnorelistCommand extends SimpleCommand {
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
