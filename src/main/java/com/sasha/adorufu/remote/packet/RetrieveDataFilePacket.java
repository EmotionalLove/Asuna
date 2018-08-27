package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Sasha at 4:18 PM on 8/25/2018
 */
public class RetrieveDataFilePacket extends Packet.Incoming {

    private String response;
    private String[] fileData;

    public RetrieveDataFilePacket(PacketProcessor processor) {
        super(processor, -5);
    }

    @Override
    public void processIncomingPacket() {
        GuiCloudLogin.message = response;
        if (fileData == null) {
            return;
        }
        try {
            File file = new File("AdorufuData.yml");
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file);
            for (String fileDatum : this.fileData) {
                System.out.println(fileDatum);
                writer.println(fileDatum);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //AdorufuMod.REMOTE_DATA_MANAGER.EVENT_MANAGER.invokeEvent(new RetrieveDataFileEvent(this));
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.response = pckData.get(0);
        if (!response.equals("aOperation completed!")) {
            this.fileData = null;
            return;
        }
        String strData = pckData.get(1);
        this.fileData = strData.split("/\\{oof}");
    }
}
