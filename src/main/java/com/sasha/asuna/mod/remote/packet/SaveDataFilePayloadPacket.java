/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.exception.AsunaSuspicousDataFileException;
import com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.asuna.mod.remote.PacketData;
import com.sasha.asuna.mod.remote.PacketProcessor;

import java.io.*;

/**
 * Created by Sasha at 11:44 AM on 8/26/2018
 */
public class SaveDataFilePayloadPacket extends Packet.Outgoing {

    @PacketData
    private String sessionId;
    @PacketData
    private String payload;

    public SaveDataFilePayloadPacket(PacketProcessor processor) {
        super(processor, 7);
        this.sessionId = AsunaMod.REMOTE_DATA_MANAGER.asunaSessionId;
        File file = new File("AsunaSettingData.yml");
        if (!file.exists()) {
            return; // nothing to do.
        }
        if (file.length() > 1000000 /*bytes*/) {/* Make sure malicious users can't upload extraordinary large data files. Needs server-side check as well*/
            GuiCloudLogin.message = "4Failure saving data file.";
            throw new AsunaSuspicousDataFileException("The data file's size cannot exceed 1MB (it should only be a few KB) (yours is " + file.getTotalSpace() + " bytes)");
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder b = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                b.append(line).append("/{oof}");
            }
            this.payload = b.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispatchPck() {
        this.getProcessor().send(PacketProcessor.formatPacket(SaveDataFilePayloadPacket.class, this));
    }
}
