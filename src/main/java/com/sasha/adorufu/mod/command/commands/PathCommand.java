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
import baritone.api.event.events.*;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.pathing.goals.GoalXZ;
import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.modules.ModuleJesus;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

/**
 * This class is designed to cooperate with the Baritone API to
 * do things. It's pretty cool.
 */
@SimpleCommandInfo(description = "Push instructions to the Baritone pathfinder"
        , syntax = {"location <x> <z>", "mine <block>", "stop", "debug", "parkour <true/false>"})
public class PathCommand extends SimpleCommand implements IGameEventListener {

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
                }
                BaritoneAPI.getMineBehavior().mine(this.getArguments()[1]);
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

    @Override
    public void onTick(TickEvent tickEvent) {

    }

    @Override
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {

    }

    @Override
    public void onProcessKeyBinds() {

    }

    @Override
    public void onSendChatMessage(ChatEvent chatEvent) {

    }

    @Override
    public void onChunkEvent(ChunkEvent chunkEvent) {

    }

    @Override
    public void onRenderPass(RenderEvent renderEvent) {

    }

    @Override
    public void onWorldEvent(WorldEvent worldEvent) {

    }

    @Override
    public void onSendPacket(PacketEvent packetEvent) {

    }

    @Override
    public void onReceivePacket(PacketEvent packetEvent) {

    }

    @Override
    public void onPlayerRotationMove(RotationMoveEvent rotationMoveEvent) {

    }

    @Override
    public void onBlockInteract(BlockInteractEvent blockInteractEvent) {

    }

    @Override
    public void onPlayerDeath() {

    }

    @Override
    public void onPathEvent(PathEvent pathEvent) {

    }
}
