package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.eventsys.SimpleEvent;
import net.minecraft.util.math.AxisAlignedBB;

public class MinecraftGetAABBEvent extends SimpleEvent {
    private AxisAlignedBB axisAlignedBB;

    public MinecraftGetAABBEvent(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }
}
