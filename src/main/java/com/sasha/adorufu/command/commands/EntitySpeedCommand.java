package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.modules.ModuleEntitySpeed;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SimpleCommandInfo(description = "Adjust the speed of entityspeed", syntax = {"<speed (decimals allowed)>"})
public class EntitySpeedCommand extends SimpleCommand {

    public EntitySpeedCommand() {
        super("entityspeed");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length > 1) {
            AdorufuMod.logErr(false, "Incorrect Arguments. Try \"-help command " + this.getCommandName());
            return;
        }
        float newSpeed = Float.parseFloat(this.getArguments()[0]);
        if (newSpeed < 0.1f) {
            AdorufuMod.logErr(false, "Speed values smaller than 0.1 are not allowed.");
            return;
        }
        ModuleEntitySpeed.speed = newSpeed;
        AdorufuMod.logMsg(false, "EntitySpeed's speed changed to " + newSpeed);
        AdorufuMod.scheduler.schedule(() -> {
            try {
                AdorufuMod.DATA_MANAGER.saveSomeGenericValue("Adorufu.values", "entityspeed", newSpeed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.NANOSECONDS);
    }
}
