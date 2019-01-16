/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalRunAway;
import baritone.api.pathing.goals.GoalXZ;
import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.impl.JesusFeature;
import com.sasha.asuna.mod.misc.Manager;
import com.sasha.asuna.mod.waypoint.Waypoint;
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
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder"
        , syntax = {
        "to <x> <z>",
        "mine <block>",
        "mine this",
        "stop",
        "debug",
        "parkour <true/false>",
        "follow <player>",
        "avoid <true/false>",
        "top"})
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

    // todo armour and feather falling/enchants?????
    public static void tick() {
        if (AsunaMod.minecraft.world == null) return;
        int fall = 3;
        fall += (0.5f * AsunaMod.minecraft.player.getHealth()); // make sure falling wont kill the player
        BaritoneAPI.getSettings().maxFallHeightBucket.value = fall;
        BaritoneAPI.getSettings().assumeWalkOnWater.value = Manager.Feature.isFeatureEnabled(JesusFeature.class);
        if (avoid) {
            BlockPos pos = isHostileEntityClose();
            if (getPrimaryBaritone().getPathingBehavior().isPathing() && (!(getPrimaryBaritone().getPathingBehavior().getGoal() instanceof GoalRunAway)) && rememberGoal != null && pos != null) {
                getPrimaryBaritone().getPathingBehavior().cancelEverything();
                getPrimaryBaritone().getCustomGoalProcess().setGoal(new GoalRunAway(50, pos));
                getPrimaryBaritone().getCustomGoalProcess().path();
                AsunaMod.scheduler.schedule(() -> {
                    getPrimaryBaritone().getPathingBehavior().cancelEverything();
                    getPrimaryBaritone().getCustomGoalProcess().setGoal(rememberGoal);
                    getPrimaryBaritone().getCustomGoalProcess().path();
                    rememberGoal = null;
                }, 20L, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * Check if a hostile entity is within 6 blocks of us
     *
     * @return the entity's blockpos, if one is present.
     */
    @Nullable
    private static BlockPos isHostileEntityClose() {
        for (Entity entity : AsunaMod.minecraft.world.getLoadedEntityList()) {
            if (entity instanceof EntityMob && entity.getDistance(AsunaMod.minecraft.player) <= 6f) {
                return entity.getPosition();
            }
        }
        return null;
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
            BaritoneAPI.getSettings().renderPathIgnoreDepth.value = true;
            BaritoneAPI.getSettings().renderGoalIgnoreDepth.value = true;
            BaritoneAPI.getSettings().renderSelectionBoxesIgnoreDepth.value = true;
            set = true;
        }
        if (this.getArguments() == null || this.getArguments().length == 0) {
            AsunaMod.logErr(false, "Invalid arguments.");
            return;
        }
        // -path follow <name>
        if (this.getArguments()[0].equalsIgnoreCase("follow")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Not enough args");
                return;
            }
            if (this.getArguments()[0].equalsIgnoreCase(AsunaMod.minecraft.player.getName())) {
                AsunaMod.logErr(false, "You can't follow yourself");
                return;
            }
            for (Entity entity : AsunaMod.minecraft.world.getLoadedEntityList()) {
                if (entity instanceof EntityPlayer && entity.getName().equalsIgnoreCase(this.getArguments()[1])) {
                    getPrimaryBaritone().getFollowProcess().follow(e -> e == entity);
                    AsunaMod.logMsg("Following " + entity.getName());
                    return;
                }
            }
            AsunaMod.logErr(false, "That player isn't near you.");
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("debug")) {
            BaritoneAPI.getSettings().chatDebug.value = true;
            return;
        }
        // go to the highest point where u currently are
        if (this.getArguments()[0].equalsIgnoreCase("top")) {
            int x = AsunaMod.minecraft.player.getPosition().getX();
            int z = AsunaMod.minecraft.player.getPosition().getZ();
            for (int y = 256; y > 4; y--) {
                IBlockState bs = AsunaMod.minecraft.world.getBlockState(new BlockPos(x, y, z));
                if (bs.getMaterial() != Material.AIR && bs.isFullBlock()) {
                    getPrimaryBaritone().getCustomGoalProcess().setGoal(new GoalBlock(x, y + 1, z));
                    getPrimaryBaritone().getCustomGoalProcess().path();
                    AsunaMod.logMsg(false, "Moving to higher ground");
                    return;
                }
            }
        }
        if (this.getArguments()[0].equalsIgnoreCase("stop")) {
            getPrimaryBaritone().getPathingBehavior().cancelEverything();
            getPrimaryBaritone().getMineProcess().cancel();
            getPrimaryBaritone().getFollowProcess().cancel();
            AsunaMod.logMsg(false, "The pathfinder has stopped");
            return;
        }
        if (this.getArguments()[0].toLowerCase().matches("build|print")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Invalid args");
                return;
            }
            File dir = new File("schematics");
            if (!dir.exists()) dir.mkdir();
            File file = new File(dir, this.getArguments()[1]);
            if (!file.exists()) {
                AsunaMod.logErr(false, "Schematic file doesn't exist!!");
                return;
            }
            AsunaMod.logMsg(false, "Trying to build " + this.getArguments()[1]);
            if (!getPrimaryBaritone().getBuilderProcess().build(this.getArguments()[1])) {
                AsunaMod.logErr(false, "Couldn't build!");
            }
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("waypoint")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Invalid args");
                return;
            }
            for (Waypoint waypoint : AsunaMod.WAYPOINT_MANAGER.getWaypoints()) {
                if (waypoint.getName().equalsIgnoreCase(this.getArguments()[1])) {
                    getPrimaryBaritone().getPathingBehavior().cancelEverything();
                    getPrimaryBaritone().getCustomGoalProcess().setGoal(new GoalBlock(waypoint.getCoords()[0], waypoint.getCoords()[1], waypoint.getCoords()[2]));
                    getPrimaryBaritone().getCustomGoalProcess().path();
                    AsunaMod.logMsg(false, "Going to " + waypoint.getName());
                    return;
                }
            }
            AsunaMod.logErr(false, "That waypoint couldn't be found.");
            return;
        }
        // -path parkour <on/off>
        if (this.getArguments()[0].equalsIgnoreCase("parkour")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Invalid Args. Expected \"-path parkour <on/off>\"");
                return;
            }
            if (this.getArguments()[1].toLowerCase().matches("off|false|disable|no")) {
                BaritoneAPI.getSettings().allowParkour.value = false;
                BaritoneAPI.getSettings().allowParkourPlace.value = false;
                AsunaMod.logMsg(false, "Parkour disabled");
                return;
            } else if (this.getArguments()[1].toLowerCase().matches("on|true|enable|yes")) {
                BaritoneAPI.getSettings().allowParkour.value = true;
                BaritoneAPI.getSettings().allowParkourPlace.value = true;
                AsunaMod.logMsg(false, "Parkour enabled");
                return;
            } else {
                AsunaMod.logMsg(false, "Unknown setting");
            }
            return;
        }
        if (this.getArguments()[0].equalsIgnoreCase("avoid")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Invalid Args. Expected \"-path avoid <on/off>\"");
                return;
            }
            if (this.getArguments()[1].toLowerCase().matches("off|false|disable|no")) {
                avoid = false;
                AsunaMod.logMsg(false, "Avoid disabled");
                return;
            } else if (this.getArguments()[1].toLowerCase().matches("on|true|enable|yes")) {
                avoid = true;
                AsunaMod.logMsg(false, "Avoid enabled");
                return;
            } else {
                AsunaMod.logMsg(false, "Unknown setting");
            }
            return;
        }
        // -path location <x> <z>
        if (this.getArguments()[0].toLowerCase().matches("location|to|go")) {
            if (this.getArguments().length != 3) {
                AsunaMod.logErr(false, "Invalid Args. Expected \"-path &s <x> <z>\"".replace("&s", this.getArguments()[0].toLowerCase()));
                return;
            }
            try {
                int x = Integer.parseInt(this.getArguments()[1]);
                int z = Integer.parseInt(this.getArguments()[2]);
                Goal goal = new GoalXZ(x, z);
                rememberGoal = goal;
                getPrimaryBaritone().getCustomGoalProcess().setGoal(goal);
                AsunaMod.logMsg(false, "Going to " + x + " " + z);
                getPrimaryBaritone().getCustomGoalProcess().path();
            } catch (Exception e) {
                AsunaMod.logErr(false, "Coordinates must be integers!");
                e.printStackTrace();
                return;
            }
            return;
        }
        // -paht mine this
        // -path mine <blockname>
        if (this.getArguments()[0].equalsIgnoreCase("mine")) {
            if (this.getArguments().length != 2) {
                AsunaMod.logErr(false, "Invalid Args. Expected \"-path mine <block>\"");
                return;
            }
            try {
                if (this.getArguments()[1].equalsIgnoreCase("this")) {
                    Block block = Block.getBlockFromItem(AsunaMod.minecraft.player.getHeldItemMainhand().getItem());
                    if (block.material == Material.AIR) {
                        AsunaMod.logErr(false, "You aren't holding anything!");
                        return;
                    }
                    getPrimaryBaritone().getMineProcess().mine(block);
                    AsunaMod.logMsg(false, "Mining " + block.getLocalizedName());
                    return;
                }
                if (Block.getBlockFromName(this.getArguments()[1]) == null) {
                    AsunaMod.logErr(false, "Invalid block name");
                    return;
                }
                getPrimaryBaritone().getMineProcess().cancel();
                getPrimaryBaritone().getMineProcess().mine(Block.getBlockFromName(this.getArguments()[1]));
                AsunaMod.logMsg(false, "Mining " + this.getArguments()[1].replace("_", " "));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AsunaMod.logErr(false, "Unknown parameter. Try -help command path");
    }

    private static IBaritone getPrimaryBaritone() {
        return BaritoneAPI.getProvider().getPrimaryBaritone();
    }
}
