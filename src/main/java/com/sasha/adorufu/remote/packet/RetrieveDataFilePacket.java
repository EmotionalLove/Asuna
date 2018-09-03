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

package com.sasha.adorufu.remote.packet;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.AdorufuDataFileRetrievedEvent;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.remote.PacketProcessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Sasha at 4:18 PM on 8/25/2018
 */
public class RetrieveDataFilePacket extends Packet.Incoming {

    private String response;
    private String[] fileData;

    public RetrieveDataFilePacket(PacketProcessor processor) {
        super(processor, -5);
    }

    @Override
    public void processIncomingPacket() {
        GuiCloudLogin.message = response;
        if (fileData == null) {
            return;
        }
        try {
            File file = new File("AdorufuData.yml");
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file);
            for (String fileDatum : this.fileData) {
                System.out.println(fileDatum);
                writer.println(fileDatum);
            }
            writer.close();
            AdorufuMod.EVENT_MANAGER.invokeEvent(new AdorufuDataFileRetrievedEvent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataVars(ArrayList<String> pckData) {
        this.response = pckData.get(0);
        if (!response.equals("aOperation completed!")) {
            this.fileData = null;
            return;
        }
        String strData = pckData.get(1);
        this.fileData = strData.split("/\\{oof}");
    }
}
