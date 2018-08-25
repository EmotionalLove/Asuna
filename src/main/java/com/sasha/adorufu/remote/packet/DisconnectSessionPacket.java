package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.PacketProcessor;

import java.util.ArrayList;

/**
 * Created by Sasha at 12:01 PM on 8/25/2018
 */
public class DisconnectSessionPacket extends Packet.Incoming {

    private String reason;

    public DisconnectSessionPacket(PacketProcessor processor) {
        super(processor, -2);
    }

    @Override
    public void processIncomingPacket() {
        AdorufuMod.logErr(true, "Data server forcefully disconnected us: " + reason);
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.reason = pckData.get(0);
    }
}
