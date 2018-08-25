package com.sasha.adorufu.remote;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class AdorufuDataClient {
    public Socket socket;
    private Scanner scanner;

    public AdorufuDataClient(InetAddress serverAddress, int serverPort, String username, String passwd) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
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

    }
}
