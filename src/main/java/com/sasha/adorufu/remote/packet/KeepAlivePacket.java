package com.sasha.adorufu.remote.packet;


import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

/**
 * Created by Sasha at 12:00 PM on 8/25/2018
 */
public class KeepAlivePacket extends Packet.Outgoing {

    @PacketData
    private String sessionId;

    public KeepAlivePacket(PacketProcessor processor, String sessionId) {
        super(processor, 3);
        this.sessionId = sessionId;
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(KeepAlivePacket.class, this));
    }
}
