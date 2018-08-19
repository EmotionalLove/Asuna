package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha on 09/08/2018 at 11:30 AM
 **/
public class ClientOverlayRenderEvent extends SimpleEvent {
    private float partialTicks;

    public ClientOverlayRenderEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
