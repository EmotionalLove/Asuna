/*
 * Copyright (c) Sasha Stevens 2018.
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

package com.sasha.adorufu.mod.command.commands;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.module.modules.ModuleEntitySpeed;
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
            AdorufuMod.logErr(false, "Incorrect Arguments. Try \"-help command " + this.getCommandName() + "\"");
            return;
        }
        double newSpeed = Double.parseDouble(this.getArguments()[0]);
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
