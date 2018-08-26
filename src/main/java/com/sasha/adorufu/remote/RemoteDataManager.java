package com.sasha.adorufu.remote;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudRegister;
import com.sasha.eventsys.SimpleEventManager;

import java.net.InetAddress;

/**
 * This will (eventually) be used to allow the user to save their keybinds to the cloud and retrieve their
 * settings on other machines.
 *
 * it'll be based around a text-based TCP system to a remote server. It will feature authentication.
 *
 * This will be completely optional for the user, whether they want to use this service or not.
 */
public class RemoteDataManager {

    //public static RemoteDataManager INSTANCE;
    public SimpleEventManager EVENT_MANAGER;
    public boolean loggedIn = false;
    public String adorufuSessionId;
    public String username;

    public void connect() {
        AdorufuMod.EVENT_MANAGER.registerListener(new GuiCloudLogin.GuiCloudLoginEventHandler());
        AdorufuMod.EVENT_MANAGER.registerListener(new GuiCloudRegister.GuiCloudRegisterEventHandler());
        try {
            AdorufuDataClient client = new AdorufuDataClient(InetAddress.getByName(/*todo*/"127.0.0.1"), Integer.parseInt("42069"));
            AdorufuMod.logMsg(true, "\r\nConnected to data server: " + client.socket.getInetAddress() + ":" + client.socket.getPort());
            client.start();
        }catch (Exception ex) {

        }
    }
}
