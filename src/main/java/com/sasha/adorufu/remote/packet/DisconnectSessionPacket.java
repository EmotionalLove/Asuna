package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.packet.events.DisconnectSessionEvent;

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
        GuiCloudLogin.message = reason;
        AdorufuMod.REMOTE_DATA_MANAGER.EVENT_MANAGER.invokeEvent(new DisconnectSessionEvent(this));
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.reason = pckData.get(0);
    }
}
