package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

import java.io.File;

/**
 * Created by Sasha at 12:15 PM on 8/25/2018
 */
public class RequestSaveDataFilePacket extends Packet.Outgoing {

    @PacketData private String sessionId;
    @PacketData private long fileBytes;

    public RequestSaveDataFilePacket(PacketProcessor processor, String sessionID) {
        super(processor, 4);
        this.sessionId = sessionID;
    }

    @Override
    public void dispatchPck() {
        File file = new File("AdorufuData.yml");
        if (!file.exists()) {
            return;
        }
        this.fileBytes = file.length();
        this.getProcessor().send(PacketProcessor.formatPacket(RequestSaveDataFilePacket.class, this));
    }
}
