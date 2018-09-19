/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.mod.remote.packet;


import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.mod.remote.PacketProcessor;

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
