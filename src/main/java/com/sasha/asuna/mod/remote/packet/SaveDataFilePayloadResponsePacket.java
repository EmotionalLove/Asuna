/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

package com.sasha.asuna.mod.remote.packet;

import com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.asuna.mod.remote.PacketProcessor;

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
