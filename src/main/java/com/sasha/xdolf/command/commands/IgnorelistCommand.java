package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;

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

        }
    }
}
