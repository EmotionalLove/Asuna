package com.sasha.adorufu.remote.packet.events;

import com.sasha.adorufu.remote.packet.RetrieveDataFilePacket;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 6:43 PM on 8/25/2018
 */
public class RetrieveDataFileEvent extends SimpleEvent {
    private RetrieveDataFilePacket pck;

    public RetrieveDataFileEvent(RetrieveDataFilePacket pck) {
        this.pck = pck;
    }
}
