package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;

/**
 * Created by Sasha at 7:41 PM on 9/2/2018
 */
public class PlayerKnockbackEvent extends SimpleCancellableEvent {

    private double motionX;
    private double motionY;
    private double motionZ;

    public PlayerKnockbackEvent(double mX, double mY, double mZ) {
        this.motionX = mX;
        this.motionY = mY;
        this.motionZ = mZ;
    }

    public double getMotionX() {
        return motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }
}
