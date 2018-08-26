package com.sasha.adorufu.remote.packet;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.packet.events.RegisterResponseEvent;

import java.util.ArrayList;

/**
 * Created by Sasha at 11:41 AM on 8/25/2018
 */
public class RegisterResponsePacket extends Packet.Incoming {

    private boolean registrationSuccessful;
    private String message;

    public RegisterResponsePacket(PacketProcessor processor) {
        super(processor, -6);
    }

    public String getResponse() {
        return message;
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    @Override
    public void processIncomingPacket() {
        AdorufuMod.REMOTE_DATA_MANAGER.EVENT_MANAGER.invokeEvent(new RegisterResponseEvent(this));
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        registrationSuccessful = Boolean.parseBoolean(pckData.get(0));
        this.message = pckData.get(1);
    }
}
