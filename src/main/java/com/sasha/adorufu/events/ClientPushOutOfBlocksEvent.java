package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;

public class ClientPushOutOfBlocksEvent extends SimpleCancellableEvent {
    private double x;
    private double y;
    private double z;
    public ClientPushOutOfBlocksEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
