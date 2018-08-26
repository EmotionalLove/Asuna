package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.RemoteDataManager;
import com.sasha.adorufu.remote.packet.events.RetrieveDataFileEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sasha at 4:18 PM on 8/25/2018
 */
public class RetrieveDataFilePacket extends Packet.Incoming {

    private List<String> fileData;

    public RetrieveDataFilePacket(PacketProcessor processor) {
        super(processor, -5);
    }

    @Override
    public void processIncomingPacket() {
        try {
            FileWriter writer = new FileWriter("AdorufuData.yml", false);
            this.fileData.forEach(e -> {
                try {
                    writer.write(e + "\r\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RemoteDataManager.INSTANCE.EVENT_MANAGER.invokeEvent(new RetrieveDataFileEvent(this));
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        String strData = pckData.get(0);
        this.fileData = Arrays.asList(strData.split("\\\\n"));
    }
}
