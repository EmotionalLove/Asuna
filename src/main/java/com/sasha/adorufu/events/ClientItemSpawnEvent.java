package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.item.Item;

public class ClientItemSpawnEvent extends SimpleEvent {

    private int x,y,z;

    public ClientItemSpawnEvent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int[] getCoordinate() {
        return new int[] {this.x, this.y, this.z};
    }

}
