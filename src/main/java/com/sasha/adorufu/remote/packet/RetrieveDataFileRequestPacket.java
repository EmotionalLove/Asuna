package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

/**
 * Created by Sasha at 4:16 PM on 8/25/2018
 */
public class RetrieveDataFileRequestPacket extends Packet.Outgoing {

    @PacketData
    private String sessionId;

    public RetrieveDataFileRequestPacket(PacketProcessor processor, String sessionId) {
        super(processor, 5);
        this.sessionId = sessionId;
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(RetrieveDataFileRequestPacket.class, this));
    }
}
