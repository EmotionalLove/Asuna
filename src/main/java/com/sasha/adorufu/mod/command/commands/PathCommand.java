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

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder")
public class PathCommand extends SimpleCommand {

    public PathCommand() {
        super("path");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length == 0) {
            AdorufuMod.logErr(false, "Invalid arguments.");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("go")) {
            BaritoneAPI.getSettings().allowSprint.value = true;
            BaritoneAPI.getPathingBehavior().path();
            AdorufuMod.logMsg(false, "The pathfinder is now active");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("stop")) {
            BaritoneAPI.getPathingBehavior().cancel();
            AdorufuMod.logMsg(false, "The pathfinder has stopped");
            return;
        }
        // -path location <x> <y> <z>
        if (this.getArguments()[0].equalsIgnoreCase("location")) {
            if (this.getArguments().length != 3) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path location <x> <z>\"");
                return;
            }
            try {
                int x = Integer.parseInt(this.getArguments()[1]);
                int z = Integer.parseInt(this.getArguments()[2]);
                BaritoneAPI.getPathingBehavior().setGoal(new GoalXZ(x, z));
            }
            catch (Exception e) {
                AdorufuMod.logErr(false, "Coordinates must be integers!");
                return;
            }
            return;
        }
        // -path mine <blockname> <quantity>
        if (this.getArguments()[0].equalsIgnoreCase("mine")) {
            if (this.getArguments().length != 3) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path mine <block> <amt>\"");
                return;
            }
            try {
                int quantity = Integer.parseInt(this.getArguments()[1]);
                //BaritoneAPI.getMineBehavior().mine(quantity, (String)this.getArguments()[2]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException ignored) {
                AdorufuMod.logErr(false, "invalid args");
            }
        }
    }
}
