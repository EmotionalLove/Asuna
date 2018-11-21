/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.events.server;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class ServerLoadChunkEvent extends SimpleEvent {

    private int chunkX;
    private int chunkZ;
    private Chunk chunk;
    private SPacketChunkData packetIn;

    public ServerLoadChunkEvent(Chunk chunk, SPacketChunkData packetIn, int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkZ = chunkY;
        this.chunk = chunk;
        this.packetIn = packetIn;
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

    public SPacketChunkData getPacketIn() {
        return packetIn;
    }
}
