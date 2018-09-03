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
