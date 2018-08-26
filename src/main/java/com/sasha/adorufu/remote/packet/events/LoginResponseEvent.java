package com.sasha.adorufu.remote.packet.events;

import com.sasha.adorufu.remote.packet.LoginResponsePacket;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 6:43 PM on 8/25/2018
 */
public class LoginResponseEvent extends SimpleEvent {
    private LoginResponsePacket pck;

    public LoginResponseEvent(LoginResponsePacket pck) {
        this.pck = pck;
    }
}
