package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;

/**
 * Created by Sasha on 08/08/2018 at 7:42 AM
 **/
@CommandInfo(description = "Displays the authors of the client and the client's version.", syntax = {""})
public class AboutCommand extends XdolfCommand {
    public AboutCommand() {
        super("about");
    }

    @Override
    public void onCommand() {
        XdolfMod.logMsg(false, "Xdolf " + XdolfMod.VERSION + " by Sasha and Sgt Pepper. All Rights Reserved.");
        XdolfMod.logMsg(false, "Run -help for commands.");
    }
}
