package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;

import java.util.ArrayList;

/**
 * Created by Sasha at 6:01 PM on 8/26/2018
 */
public class SaveDataFilePayloadResponsePacket extends Packet.Incoming {

    private String response;

    public SaveDataFilePayloadResponsePacket(PacketProcessor processor) {
        super(processor, -7);
    }

    @Override
    public void processIncomingPacket() {
        GuiCloudLogin.message = response;
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.response = pckData.get(0);
    }
}
