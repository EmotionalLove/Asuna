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

package com.sasha.asuna.mod.remote;


import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.remote.packet.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudLogin.previouslyConnected;

/**
 * The code in here is very "hacky" and ghetto. As long as it does it's job, I really don't care.
 */
public class PacketProcessor {
    public ScheduledFuture<?> keepAliveFuture;
    private Socket server;
    private PrintWriter writer;
    private ScheduledExecutorService scheduler;

    public PacketProcessor(Socket client) {
        this.scheduler = Executors.newScheduledThreadPool(4);
        this.server = client;
        try {
            this.writer = new PrintWriter(this.server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        keepAliveFuture = scheduler.scheduleWithFixedDelay(() -> {
            if (AsunaMod.REMOTE_DATA_MANAGER.asunaSessionId != null) {
                new KeepAlivePacket(this, AsunaMod.REMOTE_DATA_MANAGER.asunaSessionId).dispatchPck();
            }
        }, 13, 13, TimeUnit.SECONDS);
    }

    public static ArrayList<String> formatPacket(Class<? extends Packet.Outgoing> pckClass, Object instance) {
        ArrayList<String> pckArr = new ArrayList<>();
        try {
            pckArr.add("PCKSTART");
            Packet.Outgoing pck = (Packet.Outgoing) instance;
            pckArr.add("" + pck.getId());
            for (Field field : pckClass.getDeclaredFields()) {
                field.setAccessible(true);
                PacketData dd = field.getDeclaredAnnotation(PacketData.class);
                if (dd != null) {
                    Object obj = field.get(instance);
                    pckArr.add(obj.toString());
                }
            }
            pckArr.add("PCKEND");
            return pckArr;
        } catch (Exception e) {
            System.out.println("ERROR WHILE FORMATTING PACKET DATA. PACKET WILL !!!NOT!!! BE SENT!");
            e.printStackTrace();
            return pckArr;
        }
    }

    public Socket getServer() {
        return server;
    }

    public void listen() throws Exception {
        String data;
        BufferedReader in = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
        try {
            ArrayList<String> pckArr = new ArrayList<>();
            int status = 0;
            // 0 = waiting for PCKSTART (no packets are being processed atm)
            // 1 = waiting for the packet id (it should ALWAYS be sent right after PCKSTART, otherwise bad things will happen)
            // 2 = waiting for PCKEND
            int pckId = 0;
            while ((data = in.readLine()) != null) {
                if (data.startsWith("PCKSTART") && status == 0) {
                    status = 1;
                    continue;
                }
                if (status == 1) {
                    pckId = Integer.parseInt(data);
                    status = 2;
                    continue;
                }
                if (status == 2) {
                    if (data.equals("PCKEND")) {
                        switch (pckId) {
                            case -1:
                                LoginResponsePacket lrp = new LoginResponsePacket(this);
                                lrp.setDataVars(pckArr);
                                lrp.processIncomingPacket();
                                break;
                            case -2:
                                DisconnectSessionPacket dsp = new DisconnectSessionPacket(this);
                                dsp.setDataVars(pckArr);
                                dsp.processIncomingPacket();
                                break;
                            case -4:
                                SaveDataFilePacket sdfp = new SaveDataFilePacket(this);
                                sdfp.setDataVars(pckArr);
                                sdfp.processIncomingPacket();
                                break;
                            case -5:
                                RetrieveDataFilePacket rdp = new RetrieveDataFilePacket(this);
                                rdp.setDataVars(pckArr);
                                rdp.processIncomingPacket();
                                break;
                            case -6:
                                RegisterResponsePacket rrp = new RegisterResponsePacket(this);
                                rrp.setDataVars(pckArr);
                                rrp.processIncomingPacket();
                                break;
                            case -7:
                                SaveDataFilePayloadResponsePacket sdfprp = new SaveDataFilePayloadResponsePacket(this);
                                sdfprp.setDataVars(pckArr);
                                sdfprp.processIncomingPacket();
                                break;
                            default:
                                AsunaMod.logWarn(true, "Unregistered incoming packet. ID: " + pckId);
                        }
                        pckArr.clear();
                        pckId = 0;
                        status = 0;
                        continue;
                    }
                    pckArr.add(data);
                }
            }
        } catch (Exception e) {
            this.server.close();
            e.printStackTrace();
            AsunaMod.logErr(true, "disconnected due to " + e.getMessage());
            if (keepAliveFuture != null) keepAliveFuture.cancel(true);
            keepAliveFuture = null;
            previouslyConnected = false;
            // relaunch
        }
    }

    public void send(ArrayList<String> s) {
        if (s.isEmpty()) {
            return;
        }
        for (String s1 : s) {
            this.writer.println((String) s1);
            this.writer.flush();
        }

    }

    public ScheduledExecutorService getScheduler() {
        return this.scheduler;
    }
}
