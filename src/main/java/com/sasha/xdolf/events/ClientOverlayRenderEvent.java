package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha on 09/08/2018 at 11:30 AM
 **/
public class ClientOverlayRenderEvent extends SimpleEvent {
    private int partialTicks;

    public ClientOverlayRenderEvent(int partialTicks) {
        this.partialTicks = partialTicks;
    }

    public int getPartialTicks() {
        return partialTicks;
    }
}
