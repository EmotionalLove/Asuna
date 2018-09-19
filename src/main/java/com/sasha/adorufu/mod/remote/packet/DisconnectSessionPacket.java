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
import com.sasha.adorufu.mod.remote.packet.events.DisconnectSessionEvent;

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
