/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.command.commands;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

/**
 * Created by Sasha on 11/08/2018 at 7:05 PM
 **/
@SimpleCommandInfo(description = "Displays data about the extra time needed to tick impl.", syntax = {""})
public class LagCommand extends SimpleCommand {
    public LagCommand() {
        super("lag");
    }

    @Override
    public void onCommand() {
        AsunaMod.logMsg(false, "Displaying performance info:");
        AsunaMod.logMsg("Module onTick time (current) \2476\247l" + AsunaMod.PERFORMANCE_ANAL.getCurrentMsNormal() + " ms");
        AsunaMod.logMsg("Module onTick time (average) \2476\247l" + AsunaMod.PERFORMANCE_ANAL.getAvgMsNormal() + " ms");
        AsunaMod.logMsg("Module onRender time (current) \2476\247l" + AsunaMod.PERFORMANCE_ANAL.getCurrentMsRender() + " ms");
        AsunaMod.logMsg("Module onRender time (average) \2476\247l" + AsunaMod.PERFORMANCE_ANAL.getAvgMsRender() + " ms");
    }
}
