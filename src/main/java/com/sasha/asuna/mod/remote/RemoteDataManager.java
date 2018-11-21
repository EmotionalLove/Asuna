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

package com.sasha.asuna.mod.remote;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudControl;
import com.sasha.asuna.mod.gui.remotedatafilegui.GuiCloudLogin;
import com.sasha.asuna.mod.remote.packet.events.LoginResponseEvent;
import com.sasha.asuna.mod.remote.packet.events.RegisterResponseEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleEventManager;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.gui.GuiMainMenu;

import java.net.InetAddress;

/**
 * This will (eventually) be used to allow the user to save their keybinds to the cloud and retrieve their
 * settings on other machines.
 * <p>
 * it'll be based around a text-based TCP system to a remote server. It will feature authentication.
 * <p>
 * This will be completely optional for the user, whether they want to use this service or not.
 */
public class RemoteDataManager implements SimpleListener {

    public static String ip = "2b2tmuseum.com";
    public static int port = 42069;
    //public static RemoteDataManager INSTANCE;
    public SimpleEventManager EVENT_MANAGER = new SimpleEventManager();
    public boolean loggedIn = false;
    public String asunaSessionId;
    public String username;

    @SimpleEventHandler
    public void onLoginResponse(LoginResponseEvent e) {
        GuiCloudLogin.message = e.getPck().getResponse();
        if (e.getPck().isLoginSuccessful()) {
            AsunaMod.minecraft.displayGuiScreen(new GuiCloudControl(new GuiMainMenu()));
        }
    }

    @SimpleEventHandler
    public void onLoginResponse(RegisterResponseEvent e) {
        GuiCloudLogin.message = e.getPck().getResponse();
        if (e.getPck().isRegistrationSuccessful()) {
            AsunaMod.minecraft.displayGuiScreen(new GuiCloudLogin());
        }
    }

    public void connect() {
        AsunaMod.EVENT_MANAGER.registerListener(this);
        try {
            AsunaDataClient client = new AsunaDataClient(InetAddress.getByName(ip), port);
            AsunaMod.logMsg(true, "\r\nConnected to data server: " + client.socket.getInetAddress() + ":" + client.socket.getPort());
            client.start();
        } catch (Exception ex) {

        }
    }
}
