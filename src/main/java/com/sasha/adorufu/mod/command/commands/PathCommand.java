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
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalRunAway;
import baritone.api.pathing.goals.GoalXZ;
import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.modules.ModuleJesus;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder"
        , syntax = {"to <x> <z>", "mine <block>", "stop", "debug", "parkour <true/false>", "follow <player>", "avoid <true/false>", "top"})
public class PathCommand extends SimpleCommand {

    private static boolean set = false;
    /**
     * Whether to avoid hostile mobs if they come into dangerous range.
     */
    private static boolean avoid = false;
    private static Goal rememberGoal;

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
                AdorufuMod.logErr(false, "Not enough args");
                return;
            }
            if (this.getArguments()[0].equalsIgnoreCase(AdorufuMod.minecraft.player.getName())) {
                AdorufuMod.logErr(false, "You can't follow yourself");
                return;
            }
            for (Entity entity : AdorufuMod.minecraft.world.getLoadedEntityList()) {
                if (entity instanceof EntityPlayer) {
                    if (entity.getName().equalsIgnoreCase(this.getArguments()[1])) {
                        BaritoneAPI.getFollowBehavior().follow(entity);
                        AdorufuMod.logMsg("Following " + entity.getName());
                        return;
                    }

                }
            }
            AdorufuMod.logErr(false, "That player isn't near you.");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("debug")) {
            BaritoneAPI.getSettings().chatDebug.value = true;
            return;
        }
        // go to the highest point where u currently are
        if (this.getArguments()[0].equalsIgnoreCase("top")) {
            int x = AdorufuMod.minecraft.player.getPosition().getX();
            int z = AdorufuMod.minecraft.player.getPosition().getZ();
            for (int y = 256; y > 4; y--) {
                IBlockState bs = AdorufuMod.minecraft.world.getBlockState(new BlockPos(x, y, z));
                if (bs.getMaterial() != Material.AIR && bs.isFullBlock()) {
                    BaritoneAPI.getPathingBehavior().setGoal(new GoalBlock(x, y + 1 ,z));
                    BaritoneAPI.getPathingBehavior().path();
                    AdorufuMod.logMsg(false, "Moving to higher ground");
                    break;
                }
            }
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
            } else if (this.getArguments()[1].toLowerCase().matches("on|true|enable|yes")) {
                BaritoneAPI.getSettings().allowParkour.value = true;
                BaritoneAPI.getSettings().allowParkourPlace.value = true;
                AdorufuMod.logMsg(false, "Parkour enabled");
                return;
            } else {
                AdorufuMod.logMsg(false, "Unknown setting");
            }
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("avoid")) {
            if (this.getArguments().length != 2) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path avoid <on/off>\"");
                return;
            }
            if (this.getArguments()[1].toLowerCase().matches("off|false|disable|no")) {
                avoid = false;
                AdorufuMod.logMsg(false, "Avoid disabled");
                return;
            } else if (this.getArguments()[1].toLowerCase().matches("on|true|enable|yes")) {
                avoid = true;
                AdorufuMod.logMsg(false, "Avoid enabled");
                return;
            } else {
                AdorufuMod.logMsg(false, "Unknown setting");
            }
            return;
        }
        // -path location <x> <z>
        if (this.getArguments()[0].toLowerCase().matches("location|to|go")) {
            if (this.getArguments().length != 3) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path &s <x> <z>\"".replace("&s", this.getArguments()[0].toLowerCase()));
                return;
            }
            try {
                int x = Integer.parseInt(this.getArguments()[1]);
                int z = Integer.parseInt(this.getArguments()[2]);
                Goal goal = new GoalXZ(x, z);
                rememberGoal = goal;
                BaritoneAPI.getPathingBehavior().setGoal(goal);
                AdorufuMod.logMsg(false, "Going to " + x + " " + z);
                BaritoneAPI.getPathingBehavior().path();
            } catch (Exception e) {
                AdorufuMod.logErr(false, "Coordinates must be integers!");
                return;
            }
            return;
        }
        // -paht mine this
        // -path mine <blockname>
        if (this.getArguments()[0].equalsIgnoreCase("mine")) {
            if (this.getArguments().length != 2) {
                AdorufuMod.logErr(false, "Invalid Args. Expected \"-path mine <block>\"");
                return;
            }
            try {
                if (this.getArguments()[1].equalsIgnoreCase("this")) {
                    Block block = Block.getBlockFromItem(AdorufuMod.minecraft.player.getHeldItemMainhand().getItem());
                    if (block.material == Material.AIR) {
                        AdorufuMod.logErr(false, "You aren't holding anything!");
                        return;
                    }
                    BaritoneAPI.getMineBehavior().mine(block);
                    AdorufuMod.logErr(false, "Mining " + block.getLocalizedName());
                    return;
                }
                if (Block.getBlockFromName(this.getArguments()[1]) == null) {
                    AdorufuMod.logErr(false, "Invalid block name");
                    return;
                }
                BaritoneAPI.getMineBehavior().cancel();
                BaritoneAPI.getMineBehavior().mine(this.getArguments()[1]);
                AdorufuMod.logMsg(false, "Mining " + this.getArguments()[0].replace("_", " "));
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
        if (avoid) {
            BlockPos pos = isHostileEntityClose();
            if (BaritoneAPI.getPathingBehavior().isPathing() && (!(BaritoneAPI.getPathingBehavior().getGoal() instanceof GoalRunAway)) && rememberGoal != null && pos != null) {
                BaritoneAPI.getPathingBehavior().cancel();
                BaritoneAPI.getPathingBehavior().setGoal(new GoalRunAway(50, pos));
                BaritoneAPI.getPathingBehavior().path();
                AdorufuMod.scheduler.schedule(() -> {
                    BaritoneAPI.getPathingBehavior().cancel();
                    BaritoneAPI.getPathingBehavior().setGoal(rememberGoal);
                    BaritoneAPI.getPathingBehavior().path();
                    rememberGoal = null;
                }, 20L, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * Check if a hostile entity is within 6 blocks of us
     * @return the entity's blockpos, if one is present.
     */
    @Nullable
    private static BlockPos isHostileEntityClose() {
        for (Entity entity : AdorufuMod.minecraft.world.getLoadedEntityList()) {
            if (entity instanceof EntityMob && entity.getDistance(AdorufuMod.minecraft.player) <= 6f) {
                return entity.getPosition();
            }
        }
        return null;
    }
}
