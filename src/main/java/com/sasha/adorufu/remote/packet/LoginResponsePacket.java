package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.PacketProcessor;
import com.sasha.adorufu.remote.RemoteDataManager;

import java.util.ArrayList;

/**
 * Created by Sasha at 11:41 AM on 8/25/2018
 */
public class LoginResponsePacket extends Packet.Incoming {

    private boolean loginSuccessful;

    public LoginResponsePacket(PacketProcessor processor) {
        super(processor, -1);
    }

    @Override
    public void processIncomingPacket() {
        if (loginSuccessful) {
            RemoteDataManager.INSTANCE.loggedIn = true;
        }
        else {
            AdorufuMod.logErr(true, "Data Server returned the \"failed login\" statuscode!");
        }
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        loginSuccessful = Boolean.parseBoolean(pckData.get(0));
    }
}
