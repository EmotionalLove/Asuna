package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
/**
 * Created by Sasha on 11/08/2018 at 7:05 PM
 **/
@SimpleCommandInfo(description = "Displays data about the extra time needed to tick modules.", syntax = {""})
public class LagCommand extends SimpleCommand {
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
