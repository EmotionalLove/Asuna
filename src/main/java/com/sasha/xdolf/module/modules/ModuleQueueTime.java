package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.network.play.server.SPacketChat;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.sasha.xdolf.module.modules.ModuleAutoIgnore.stripColours;

@ModuleInfo(description = "Show the estimated time left in queue in chat")
public class ModuleQueueTime extends XdolfModule implements SimpleListener {

    private static long estTime = 10000;
    private static int lastQueuePos = -1;
    private static int sameCount = 1;
    ArrayList<Long> avgs = new ArrayList<>();

    public ModuleQueueTime() {
        super("QueueTime", XdolfCategory.CHAT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }

    @SimpleEventHandler
    public void onChatRecieved(ClientPacketRecieveEvent ev) {
        if (!this.isEnabled()) return;
        if (ev.getRecievedPacket() instanceof SPacketChat){
            SPacketChat e = (SPacketChat) ev.getRecievedPacket();
            //XdolfMod.logMsg(false, stripColours(e.chatComponent.getUnformattedText()));
            if (stripColours(e.getChatComponent().getUnformattedText()).startsWith("Position in queue: ")) {
                int queuepos = Integer.parseInt(stripColours(e.chatComponent.getUnformattedText()).replace("Position in queue: ", ""));
                if (lastQueuePos == -1) {
                    lastQueuePos = queuepos;
                    XdolfMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "lCalculating...");
                    return;
                }
                if (queuepos == lastQueuePos) {
                    for (int i = 0; i < queuepos; i++) {
                        if (i != 0) {
                            estTime += (10000 / i) / sameCount;
                            continue;
                        }
                        estTime += 10000 / sameCount;
                    }
                    sameCount++;
                    long estTimeWhole = estTime * queuepos;
                    String tu = convert(estTimeWhole);
                    if (estTimeWhole > 0) {
                        XdolfMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "l" + tu);
                    } else {
                        XdolfMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "lCalculating...");
                        estTime = 10000;
                    }
                    return;
                }
                if (sameCount >= 2) {
                    sameCount--;
                }
                for (int i = 0; i < queuepos; i++) {
                    if (i != 0) {
                        estTime -= (10000 / i) / sameCount;
                        continue;
                    }
                    estTime -= 10000;
                }
                long estTimeWhole = estTime * queuepos;
                String tu = convert(estTimeWhole);
                if (estTimeWhole > 0) {
                    XdolfMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "l" + tu);
                } else {
                    XdolfMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "lCalculating...");
                    estTime = 10000;
                }
                lastQueuePos = queuepos;
            }
        }
    }
    private static String convert(long miliSeconds)
    {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        return String.format("%02dh %02dm %02ds", hrs, min, sec);
    }
    public Long handleAverages(long estTime) {
        if (avgs.size() > 20) {
            avgs.remove(0);
            avgs.add(estTime);
            long boi = 0;
            for (Long l : avgs) {
                boi+=l;
            }
            return boi / avgs.size();
        }
        avgs.add(estTime);
        long boi = 0;
        for (Long l : avgs) {
            boi+=l;
        }
        return boi / avgs.size();
    }
    public Long handleAverages() {
        if (avgs.size() < 1) {
            return -1L;
        }
        long boi = 0;
        for (Long l : avgs) {
            boi+=l;
        }
        return boi / avgs.size();
    }
}
