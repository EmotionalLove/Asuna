package com.sasha.adorufu.remote;



import com.sasha.adorufu.remote.packet.Packet;

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

/**
 * The code in here is very "hacky" and ghetto. As long as it does it's job, I really don't care.
 */
public class PacketProcessor {
    private Socket server;
    private PrintWriter writer;
    private ScheduledExecutorService scheduler;

    public ScheduledFuture<?> keepAliveFuture;

    public PacketProcessor(Socket client) {
        this.scheduler = Executors.newScheduledThreadPool(4);
        this.server = client;
        try {
            this.writer = new PrintWriter(this.server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
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
            int status = 0; // 0 = ready for new packet // 1 = waiting for id // 2 = waitint for PCKEND
            int pckId = 0;
            while ((data = in.readLine()) != null) {
                if (data.startsWith("PCKSTART") && status == 0) {
                    status = 1;
                    continue;
                }
                if (status == 1) {
                    pckId = Integer.parseInt(data);
                    status = 2;
                }
                if (status == 2) {
                    pckArr.add(data);
                    if (data.equals("PCKEND")) {
                        switch (pckId) {
                            case -1:

                            case -2:

                        }
                        pckArr.clear();
                        pckId = 0;
                        status = 0;
                    }
                }
            }
        } catch (Exception e) {
            this.server.close();
            System.out.println("disconnected due to " + e.getMessage());
            if (keepAliveFuture != null) keepAliveFuture.cancel(true);
            // relaunch
        }
    }

    public static ArrayList<String> formatPacket(Class<? extends Packet.Outgoing> pckClass, Object instance) {
        ArrayList<String> pckArr = new ArrayList<>();
        try {
            pckArr.add("PCKSTART");
            Packet.Outgoing pck = (Packet.Outgoing) instance;
            pckArr.add(""+pck.getId());
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
    public void send(ArrayList<String> s) {
        if (s.isEmpty()) {
            return;
        }
        for (String s1 : s) {
            this.writer.println((String)s1);
            this.writer.flush();
        }

    }

    public ScheduledExecutorService getScheduler() {
        return this.scheduler;
    }
}
