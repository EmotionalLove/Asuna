package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.command.AdorufuCommand;

/**
 * Created by Sasha on 08/08/2018 at 7:42 AM
 **/
@CommandInfo(description = "Displays the authors of the client and the client's version.", syntax = {""})
public class AboutCommand extends AdorufuCommand {
    public AboutCommand() {
        super("about");
    }

    @Override
    public void onCommand() {
        AdorufuMod.logMsg(false, "Adorufu " + AdorufuMod.VERSION + " by Sasha. All Rights Reserved.");
        AdorufuMod.logMsg(false, "Run -help for commands.");
    }
}
