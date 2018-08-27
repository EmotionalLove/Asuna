package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.exception.AdorufuSuspicousDataFileException;
import com.sasha.adorufu.remote.PacketData;
import com.sasha.adorufu.remote.PacketProcessor;

import java.io.*;

/**
 * Created by Sasha at 11:44 AM on 8/26/2018
 */
public class SaveDataFilePayloadPacket extends Packet.Outgoing {

    @PacketData
    private String sessionId;
    @PacketData
    private String payload;

    public SaveDataFilePayloadPacket(PacketProcessor processor) {
        super(processor, 7);
        this.sessionId = AdorufuMod.REMOTE_DATA_MANAGER.adorufuSessionId;
        File file = new File("AdorufuData.yml");
        if (!file.exists()) {
            return; // nothing to do.
        }
        if (file.length() > 1000000 /*bytes*/) {/* Make sure malicious users can't upload extraordinary large data files. Needs server-side check as well*/
            throw new AdorufuSuspicousDataFileException("The data file's size cannot exceed 1MB (it should only be a few KB) (yours is " + file.getTotalSpace() + " bytes)");
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder b = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                b.append(line).append("/{oof}");
            }
            this.payload = b.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(SaveDataFilePayloadPacket.class, this));
    }
}
