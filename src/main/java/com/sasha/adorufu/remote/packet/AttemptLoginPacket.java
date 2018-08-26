package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.packet.Packet;

public class AttemptLoginPacket extends Packet.Outgoing {

    @PacketData
    private String username;
    @PacketData private String password; //todo encryption.

    public AttemptLoginPacket(PacketProcessor processor) {
        super(processor, 1);
    }

    public void setCredentials(String username, String passwd) {
        this.username = username;
        this.password = passwd;
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(AttemptLoginPacket.class, this));
    }
}
