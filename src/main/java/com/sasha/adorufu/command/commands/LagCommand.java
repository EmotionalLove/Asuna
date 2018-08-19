package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.command.AdorufuCommand;

/**
 * Created by Sasha on 11/08/2018 at 7:05 PM
 **/
@CommandInfo(description = "Displays data about the extra time needed to tick modules.", syntax = {""})
public class LagCommand extends AdorufuCommand {
    public LagCommand() {
        super("lag");
    }

    @Override
    public void onCommand() {
        AdorufuMod.logMsg(false, "Displaying performance info:");
        AdorufuMod.logMsg("Module onTick time (current) \2476\247l" + AdorufuMod.PERFORMANCE_ANAL.getCurrentMsNormal() + " ms");
        AdorufuMod.logMsg("Module onTick time (average) \2476\247l" + AdorufuMod.PERFORMANCE_ANAL.getAvgMsNormal() + " ms");
        AdorufuMod.logMsg("Module onRender time (current) \2476\247l" + AdorufuMod.PERFORMANCE_ANAL.getCurrentMsRender() + " ms");
        AdorufuMod.logMsg("Module onRender time (average) \2476\247l" + AdorufuMod.PERFORMANCE_ANAL.getAvgMsRender() + " ms");
    }
}
