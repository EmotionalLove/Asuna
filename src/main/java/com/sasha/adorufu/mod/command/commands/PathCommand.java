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
import baritone.api.behavior.IFollowBehavior;
import baritone.api.behavior.IMineBehavior;
import baritone.api.pathing.goals.GoalXZ;
import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.modules.ModuleJesus;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import java.awt.*;
import java.lang.reflect.Method;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder"
, syntax = {"location <x> <z>", "mine <block>", "stop", "debug", "parkour <true/false>"})
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
        // -path follow <name>
        if (this.getArguments()[0].equalsIgnoreCase("follow")) {
            if (this.getArguments().length != 2) {
                AdorufuMod.logErr(false, "Not enough args!");
                return;
            }
            for (Entity entity : AdorufuMod.minecraft.world.getLoadedEntityList()) {
                if (entity instanceof EntityPlayerMP) {
                    if (entity.getName().equalsIgnoreCase(this.getArguments()[1])) {
                        try {
                            IFollowBehavior iFollowBehavior = BaritoneAPI.getFollowBehavior();
                            for (Method declaredMethod : BaritoneAPI.getFollowBehavior().getClass().getMethods()) {
                                if (declaredMethod.getName().equals("follow") && declaredMethod.getParameters()[0].getType().getName().contains("Entity")) {
                                    declaredMethod.invoke(iFollowBehavior, (Object) new String[]{this.getArguments()[1]});
                                    AdorufuMod.logMsg(false, "Following " + this.getArguments()[1]);
                                    break;
                                }
                            }
                            return;
                        }catch (Exception e) {
                            AdorufuMod.logErr(false ,"An error occurred");
                        }
                        return;
                    }
                }
            }
        }
        if (this.getArguments()[0].equalsIgnoreCase("debug")) {
            BaritoneAPI.getSettings().chatDebug.value = true;
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("stop")) {
            BaritoneAPI.getPathingBehavior().cancel();
            BaritoneAPI.getMineBehavior().cancel();
            BaritoneAPI.getFollowBehavior().cancel();
            AdorufuMod.logMsg(false, "The pathfinder has stopped");
            return;
        }
        // -path parkour <on/off>
        if (this.getArguments()[0].equalsIgnoreCase("parkour")) {
            if (this.getArguments().length != 2) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path parkour <on/off>\"");
                return;
            }
            if (this.getArguments()[1].toLowerCase().matches("off|false|disable|no")) {
                BaritoneAPI.getSettings().allowParkour.value = false;
                BaritoneAPI.getSettings().allowParkourPlace.value = false;
                AdorufuMod.logMsg(false, "Parkour disabled");
                return;
            }
            else if (this.getArguments()[1].toLowerCase().matches("on|true|enable|yes")) {
                BaritoneAPI.getSettings().allowParkour.value = true;
                BaritoneAPI.getSettings().allowParkourPlace.value = true;
                AdorufuMod.logMsg(false, "Parkour enabled");
                return;
            }
            else {
                AdorufuMod.logMsg(false, "Unknown setting");
            }
            return;
        }
        // -path location <x> <z>
        if (this.getArguments()[0].equalsIgnoreCase("location")) {
            if (this.getArguments().length != 3) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path location <x> <z>\"");
                return;
            }
            try {
                int x = Integer.parseInt(this.getArguments()[1]);
                int z = Integer.parseInt(this.getArguments()[2]);
                BaritoneAPI.getPathingBehavior().setGoal(new GoalXZ(x, z));
                AdorufuMod.logMsg(false, "Going to " + x + " " + z);
                BaritoneAPI.getPathingBehavior().path();
            } catch (Exception e) {
                AdorufuMod.logErr(false, "Coordinates must be integers!");
                return;
            }
            return;
        }
        // -path mine <blockname> <quantity>
        if (this.getArguments()[0].equalsIgnoreCase("mine")) {
            if (this.getArguments().length != 2) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path mine <block>\"");
                return;
            }
            try {
                IMineBehavior iMineBehavior = BaritoneAPI.getMineBehavior();
                for (Method declaredMethod : BaritoneAPI.getMineBehavior().getClass().getMethods()) {
                    if (declaredMethod.getName().equals("mine") && declaredMethod.getParameters()[0].getType().getName().contains("String")) {
                        declaredMethod.invoke(iMineBehavior, (Object) new String[]{this.getArguments()[1]});
                        AdorufuMod.logMsg(false, "Mining " + this.getArguments()[1].replace("_", " "));
                        break;
                    }
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AdorufuMod.logErr(false, "Unknown parametre. Try -help command path");
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
