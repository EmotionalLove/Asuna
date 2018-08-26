package com.sasha.adorufu.remote.packet.events;

import com.sasha.adorufu.remote.packet.SaveDataFilePacket;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 6:43 PM on 8/25/2018
 */
public class SaveDataResponseEvent extends SimpleEvent {
    private SaveDataFilePacket pck;

    public SaveDataResponseEvent(SaveDataFilePacket pck) {
        this.pck = pck;
    }
}
