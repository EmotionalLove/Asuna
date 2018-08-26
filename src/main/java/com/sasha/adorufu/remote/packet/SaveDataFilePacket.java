package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.exception.AdorufuSuspicousDataFileException;
import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.RemoteDataManager;
import com.sasha.adorufu.remote.packet.events.SaveDataResponseEvent;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sasha at 12:15 PM on 8/25/2018
 */
public class SaveDataFilePacket extends Packet.Incoming {

    private String response;
    private String fileData;

    public SaveDataFilePacket(PacketProcessor processor) {
        super(processor, -4);
    }

    @Override
    public void processIncomingPacket() {
        File file = new File("AdorufuData.yml");
        if (!file.exists()) {
            return; // nothing to do.
        }
        if (file.getTotalSpace() > 1000000 /*bytes*/) {/* Make sure malicious users can't upload extraordinary large data files. Needs server-side check as well*/
            throw new AdorufuSuspicousDataFileException("The data file's size cannot exceed 1MB (it should only be a few KB)");
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder b = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                b.append(line).append("\n");
            }
            this.fileData = b.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveDataResponseEvent event = new SaveDataResponseEvent(this);
        AdorufuMod.REMOTE_DATA_MANAGER.EVENT_MANAGER.invokeEvent(event);
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.response = pckData.get(0);
        this.fileData = pckData.get(1);
    }
}
