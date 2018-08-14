package com.sasha.xdolf.module.modules;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import com.sasha.xdolf.XdolfMod;

/**
 * Created by Sasha on 13/08/2018 at 3:27 PM
 **/
public class XdolfDiscordPresense {

    public static DiscordRPC discordRpc;
    public static DiscordRichPresence rpc;
    public static String state = "";
    public static String details = "";

    public static void setupPresense() {
        discordRpc = DiscordRPC.INSTANCE;
        String applicationId = "478587669308375043";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = discordUser -> XdolfMod.logMsg(true, "Connected to Discord Rich Presense API");
        discordRpc.Discord_Initialize(applicationId, handlers, true, null);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L; // epoch second
        presence.details = details;
        presence.largeImageKey = "xdolfcover";
        presence.smallImageKey = "xdolfsmol_2";
        presence.state = state;
        discordRpc.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRpc.Discord_RunCallbacks();
                try {
                    if (XdolfMod.minecraft.world == null) {
                        details = "Main Menu";
                        state = "Thinking about what to do.";
                    }
                    else if (XdolfMod.minecraft.getCurrentServerData() != null) {
                        details = "Playing Multiplayer";
                        state = "on " + XdolfMod.minecraft.getCurrentServerData().serverIP;
                    }
                    else {
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

