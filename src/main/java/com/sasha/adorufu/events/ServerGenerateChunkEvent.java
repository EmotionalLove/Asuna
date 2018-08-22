package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;

public class ServerGenerateChunkEvent extends SimpleEvent {

    private int chunkX;
    private int chunkZ;

    public ServerGenerateChunkEvent(int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkZ = chunkY;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
