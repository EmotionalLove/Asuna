package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;

/**
 * Created by Sasha on 08/08/2018 at 7:42 AM
 **/
public class AboutCommand extends XdolfCommand {
    public AboutCommand() {
        super("about");
    }

    @Override
    public void onCommand() {
        XdolfMod.logMsg(false, "This is a test :3");
        if (this.getArguments() != null){
            for (String argument : this.getArguments()) {
                XdolfMod.logMsg(false, argument);
            }
        }
    }
}
