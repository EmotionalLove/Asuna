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
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.modules.ModuleJesus;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

import java.awt.*;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder")
public class PathCommand extends SimpleCommand {

    private static boolean set = false;

    public PathCommand() {
        super("path");
    }

    @Override
    public void onCommand() {
        if (!set) {
            BaritoneAPI.getSettings().chatDebug.value = false;
            BaritoneAPI.getSettings().allowSprint.value = true;
            BaritoneAPI.getSettings().antiCheatCompatibility.value = true;
            BaritoneAPI.getSettings().walkWhileBreaking.value = false;
            BaritoneAPI.getSettings().freeLook.value = false;
            BaritoneAPI.getSettings().colorCurrentPath.value = Color.MAGENTA;
            BaritoneAPI.getSettings().colorNextPath.value = Color.BLUE;
            BaritoneAPI.getSettings().colorBestPathSoFar.value = Color.YELLOW;
            set = true;
        }
        if (this.getArguments() == null || this.getArguments().length == 0) {
            AdorufuMod.logErr(false, "Invalid arguments.");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("go")) {
            BaritoneAPI.getPathingBehavior().path();
            AdorufuMod.logMsg(false, "The pathfinder is now active");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("debug")) {
            BaritoneAPI.getSettings().chatDebug.value = true;
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
    // todo armour and feather falling/enchants?????
    public static void tick() {
        if (AdorufuMod.minecraft.world == null) return;
        int fall = 3;
        fall += (0.5f * AdorufuMod.minecraft.player.getHealth()); // make sure falling wont kill the player
        BaritoneAPI.getSettings().maxFallHeightBucket.value = fall;
        BaritoneAPI.getSettings().assumeWalkOnWater.value = Manager.Module.getModule(ModuleJesus.class).isEnabled();
    }
}
