package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;

/**
 * Created by Sasha on 11/08/2018 at 7:05 PM
 **/
@CommandInfo(description = "Displays data about the extra time needed to tick modules.", syntax = {""})
public class LagCommand extends XdolfCommand {
    public LagCommand() {
        super("lag");
    }

    @Override
    public void onCommand() {
        XdolfMod.logMsg(false, "Displaying performance info:");
        XdolfMod.logMsg("Module onTick time (current) \2476\247l" + XdolfMod.PERFORMANCE_ANAL.getCurrentMsNormal() + " ms");
        XdolfMod.logMsg("Module onTick time (average) \2476\247l" + XdolfMod.PERFORMANCE_ANAL.getAvgMsNormal() + " ms");
        XdolfMod.logMsg("Module onRender time (current) \2476\247l" + XdolfMod.PERFORMANCE_ANAL.getCurrentMsRender() + " ms");
        XdolfMod.logMsg("Module onRender time (average) \2476\247l" + XdolfMod.PERFORMANCE_ANAL.getAvgMsRender() + " ms");
    }
}
