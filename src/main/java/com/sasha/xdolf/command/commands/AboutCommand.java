package com.sasha.xdolf.command.commands;

import static com.sasha.xdolf.XdolfMod.mc;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import net.minecraft.util.text.TextComponentString;

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
    }
}
