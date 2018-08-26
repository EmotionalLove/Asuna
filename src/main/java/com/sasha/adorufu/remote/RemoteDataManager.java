package com.sasha.adorufu.remote;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.eventsys.SimpleEventManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This will (eventually) be used to allow the user to save their keybinds to the cloud and retrieve their
 * settings on other machines.
 *
 * it'll be based around a text-based TCP system to a remote server. It will feature authentication.
 *
 * This will be completely optional for the user, whether they want to use this service or not.
 */
public class RemoteDataManager {

    public static RemoteDataManager INSTANCE;
    public SimpleEventManager EVENT_MANAGER;
    public boolean loggedIn = false;
    public String adorufuSessionId;

    public RemoteDataManager(String username, String password) throws UnknownHostException {
        INSTANCE = this;
        try {
            AdorufuDataClient client = new AdorufuDataClient(InetAddress.getByName(/*todo*/"2b2tmuseum.com"), Integer.parseInt("42069"), username, password);
            AdorufuMod.logMsg(true, "\r\nConnected to data server: " + client.socket.getInetAddress() + ":" + client.socket.getPort());
            client.start();
        }catch (Exception ex) {

        }
    }
}
