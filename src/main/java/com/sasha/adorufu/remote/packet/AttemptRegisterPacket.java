package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

public class AttemptRegisterPacket extends Packet.Outgoing {

    @PacketData private String username;
    @PacketData private String password; //todo encryption.
    @PacketData private String passwordConfirm; //todo encryption.

    public AttemptRegisterPacket(PacketProcessor processor) {
        super(processor, 6);
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(AttemptRegisterPacket.class, this));
    }
}
