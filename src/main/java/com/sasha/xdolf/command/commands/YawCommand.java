package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleYawLock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@CommandInfo(description = "Adjust the yawlock value", syntax = {"<angle>"})
public class YawCommand extends XdolfCommand {
    public YawCommand() {
        super("yaw");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null){
            XdolfMod.logErr(false, "Arguments required! Try \"-help command yaw\"");
            return;
        }
        try {
            ModuleYawLock.yawDegrees = Integer.parseInt(this.getArguments()[0]);
        }catch (Exception e) {
            XdolfMod.logErr(false, "Your argument must be an integer.");
        }
    }
}
