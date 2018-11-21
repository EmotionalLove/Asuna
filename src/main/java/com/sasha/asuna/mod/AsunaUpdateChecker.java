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

package com.sasha.asuna.mod;

import com.sasha.asuna.api.AsunaPluginLoader;
import com.sasha.asuna.mod.events.client.ClientPacketRecieveEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketServerDifficulty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AsunaUpdateChecker implements SimpleListener {

    public static String latestVer = AsunaMod.VERSION;

    public static boolean checkForUpdates() {
        try {
            URL url = new URL("http://2b2tmuseum.com/xdolfver.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            if ((latestVer = br.readLine()).equals(AsunaMod.VERSION)) {
                br.close();
                return false;
            }
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e) {
        if (e.getRecievedPacket() instanceof SPacketServerDifficulty) {
            if (AsunaPluginLoader.getLoadedPlugins().size() > 0) {
                AsunaMod.scheduler.schedule(() -> {
                    AsunaMod.logWarn(false, "\2474There are " + AsunaPluginLoader.getLoadedPlugins().size() + " plugins" +
                            " loaded. Please make sure that you know what these plugins are doing, as developers can " +
                            "put malicious code in their plugins and gain access to your coordinates/session ids/and other sensitive info.");
                }, 10, TimeUnit.SECONDS);
            }
            if (!checkForUpdates()) {
                return;
            }
            AsunaMod.scheduler.schedule(() -> {
                AsunaMod.logMsg("\247eThere is an update available for Asuna!");
                AsunaMod.logMsg("https://github.com/EmotionalLove/Asuna/releases");
                AsunaMod.logMsg("Run \247l-update");
                AsunaMod.logMsg("Current Version \247l" + AsunaMod.VERSION);
                AsunaMod.logMsg("Latest Version \247l" + latestVer);
            }, 10, TimeUnit.SECONDS);
        }
    }
}
