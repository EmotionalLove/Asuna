package com.sasha.adorufu.remote.packet;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;

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
        GuiCloudLogin.message = this.message;
        if (registrationSuccessful) {
            AdorufuMod.minecraft.displayGuiScreen(new GuiCloudLogin());
        }
        else {
            AdorufuMod.logErr(true, "Data Server returned the \"failed login\" statuscode!");
        }
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        registrationSuccessful = Boolean.parseBoolean(pckData.get(0));
        this.message = pckData.get(1);
    }
}
