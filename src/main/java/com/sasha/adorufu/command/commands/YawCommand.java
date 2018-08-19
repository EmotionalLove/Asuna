package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.command.AdorufuCommand;
import com.sasha.adorufu.module.modules.ModuleYawLock;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@CommandInfo(description = "Adjust the yawlock value", syntax = {"<angle>"})
public class YawCommand extends AdorufuCommand {
    public YawCommand() {
        super("yaw");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null){
            AdorufuMod.logErr(false, "Arguments required! Try \"-help command yaw\"");
            return;
        }
        try {
            ModuleYawLock.yawDegrees = Integer.parseInt(this.getArguments()[0]);
        }catch (Exception e) {
            AdorufuMod.logErr(false, "Your argument must be an integer.");
        }
    }
}
