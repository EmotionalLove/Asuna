package com.sasha.adorufu.remote;

import java.net.InetAddress;
import java.net.Socket;

public class AdorufuDataClient {

    public Socket socket;
    public static PacketProcessor processor;

    public AdorufuDataClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
    }

    public void start() {
        PacketProcessor processor = new PacketProcessor(socket);
        new Thread(() -> {
            try {
                processor.listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        AdorufuDataClient.processor = processor;
    }
}
