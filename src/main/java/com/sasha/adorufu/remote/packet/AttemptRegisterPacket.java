package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

public class AttemptRegisterPacket extends Packet.Outgoing {

    @PacketData
    private String username;
    @PacketData private String password; //todo encryption.
    @PacketData private String passwordConfirm; //todo encryption.

    public AttemptRegisterPacket(PacketProcessor processor) {
        super(processor, 6);
    }

    public void setCredentials(String username, String password, String passwordConfirm) {
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.username = username;
    }
    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(AttemptRegisterPacket.class, this));
    }
}
