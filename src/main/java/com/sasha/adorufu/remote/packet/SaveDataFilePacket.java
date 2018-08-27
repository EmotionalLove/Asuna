package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.exception.AdorufuSuspicousDataFileException;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sasha at 12:15 PM on 8/25/2018
 */
public class SaveDataFilePacket extends Packet.Incoming {

    private String response;
    private boolean proceed;

    public SaveDataFilePacket(PacketProcessor processor) {
        super(processor, -4);
    }

    @Override
    public void processIncomingPacket() {
        File file = new File("AdorufuData.yml");
        if (!file.exists()) {
            return; // nothing to do.
        }
        if (file.length() > 1000000 /*bytes*/) {/* Make sure malicious users can't upload extraordinary large data files. Needs server-side check as well*/
            GuiCloudLogin.message = "4Failure saving data file. Relaunch and try again.";
            throw new AdorufuSuspicousDataFileException("The data file's size cannot exceed 1MB (it should only be a few KB) (yours is " + file.getTotalSpace() + " bytes)");
        }
        GuiCloudLogin.message = response;
        if (this.proceed) {
            new SaveDataFilePayloadPacket(this.getProcessor()).dispatchPck();
        }
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.response = pckData.get(0);
        this.proceed = Boolean.parseBoolean(pckData.get(1));
    }
}
