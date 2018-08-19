package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;

public class PlayerJumpEvent extends SimpleCancellableEvent {
    private float jumpHeight;

    public PlayerJumpEvent(float jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public float getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(float jumpHeight) {
        this.jumpHeight = jumpHeight;
    }
}
