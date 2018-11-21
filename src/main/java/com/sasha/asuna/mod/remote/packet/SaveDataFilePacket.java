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

import com.sasha.asuna.mod.exception.AsunaSuspicousDataFileException;
import com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.asuna.mod.remote.PacketProcessor;

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
        File file = new File("AsunaSettingData.yml");
        if (!file.exists()) {
            return; // nothing to do.
        }
        if (file.length() > 1000000 /*bytes*/) {/* Make sure malicious users can't upload extraordinary large data files. Needs server-side check as well*/
            GuiCloudLogin.message = "4Failure saving data file. Relaunch and try again.";
            throw new AsunaSuspicousDataFileException("The data file's size cannot exceed 1MB (it should only be a few KB) (yours is " + file.getTotalSpace() + " bytes)");
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
