package com.sasha.adorufu;

import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketServerDifficulty;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AdorufuUpdateChecker implements SimpleListener {

    public static String latestVer = AdorufuMod.VERSION;

    public static boolean checkForUpdates() {
        try {
            URL url = new URL("http://2b2tmuseum.com/xdolfver.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            if ((latestVer = br.readLine()).equals(AdorufuMod.VERSION)) {
                br.close();
                return false;
            }
            br.close();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e) {
        if (e.getRecievedPacket() instanceof SPacketServerDifficulty) {
            if (!checkForUpdates()) {
                return;
            }
            AdorufuMod.scheduler.schedule(() -> {
                AdorufuMod.logMsg("\247eThere is an update available for Adorufu.");
                AdorufuMod.logMsg("http://github.com/EmotionalLove/Adorufu");
                AdorufuMod.logMsg("Current Version \247l" + AdorufuMod.VERSION);
                AdorufuMod.logMsg("Latest Version \247l" + latestVer);
            }, 10, TimeUnit.SECONDS);
        }
    }
}
