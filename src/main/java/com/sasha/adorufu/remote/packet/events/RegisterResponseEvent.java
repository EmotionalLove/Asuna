package com.sasha.adorufu.remote.packet.events;

import com.sasha.adorufu.remote.packet.RegisterResponsePacket;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 6:43 PM on 8/25/2018
 */
public class RegisterResponseEvent extends SimpleEvent {
    private RegisterResponsePacket pck;

    public RegisterResponseEvent(RegisterResponsePacket pck) {
        this.pck = pck;
    }

    public RegisterResponsePacket getPck() {
        return pck;
    }
}
