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

package com.sasha.asuna.mod.feature;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.sasha.asuna.mod.AsunaMod;

/**
 * Created by Sasha on 13/08/2018 at 3:27 PM
 **/
public class AsunaDiscordPresense {

    public static DiscordRPC discordRpc;
    public static DiscordRichPresence rpc;
    public static String state = "";
    public static String details = "";

    public static void setupPresense() {
        discordRpc = DiscordRPC.INSTANCE;
        String applicationId = "500896211977502720";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = discordUser -> AsunaMod.logMsg(true, "Connected to Discord Rich Presence API");
        discordRpc.Discord_Initialize(applicationId, handlers, true, null);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L; // epoch getValue
        presence.details = details;
        presence.largeImageKey = "asuna";
        presence.state = state;
        discordRpc.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRpc.Discord_RunCallbacks();
                try {
                    if (AsunaMod.minecraft.world == null) {
                        details = "Main Menu";
                        state = "Thinking about what to do.";
                    } else if (AsunaMod.minecraft.getCurrentServerData() != null) {
                        details = "Playing Multiplayer";
                        state = "on " + AsunaMod.minecraft.getCurrentServerData().serverIP;
                    } else {
                        details = "Playing Singleplayer";
                        state = "all alone ;-;";
                    }
                    Thread.sleep(15000L);
                    presence.details = details;
                    presence.state = state;
                    discordRpc.Discord_UpdatePresence(presence);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();
    }
}

