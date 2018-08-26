package com.sasha.adorufu.remote.packet.events;

import com.sasha.adorufu.remote.packet.DisconnectSessionPacket;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 6:43 PM on 8/25/2018
 */
public class DisconnectSessionEvent extends SimpleEvent {

    private DisconnectSessionPacket pck;

    public DisconnectSessionEvent(DisconnectSessionPacket pck) {
        this.pck = pck;
    }

    public DisconnectSessionPacket getPck() {
        return pck;
    }
}
