package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;

public class ClientEnderPearlSpawnEvent extends SimpleEvent {

    private int x,y,z;

    public ClientEnderPearlSpawnEvent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int[] getCoordinate() {
        return new int[] {this.x, this.y, this.z};
    }

}
