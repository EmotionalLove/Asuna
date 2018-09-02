package com.sasha.adorufu.events;

import com.mojang.authlib.GameProfile;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 9:21 PM on 9/1/2018
 */
public class ServerPlayerJoinEvent extends SimpleEvent {

    private GameProfile gp;

    public ServerPlayerJoinEvent(GameProfile gp) {this.gp = gp;}

    public GameProfile getGameProfile() {
        return gp;
    }
}
