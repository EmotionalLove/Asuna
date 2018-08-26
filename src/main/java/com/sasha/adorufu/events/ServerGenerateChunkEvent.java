package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.world.chunk.Chunk;

public class ServerGenerateChunkEvent extends SimpleEvent {

    private int chunkX;
    private int chunkZ;
    private Chunk chunk;
    //private SPacketChunkData packetIn;

    public ServerGenerateChunkEvent(Chunk chunk, int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkZ = chunkY;
        this.chunk = chunk;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public Chunk getChunk() {
        return chunk;
    }
}
