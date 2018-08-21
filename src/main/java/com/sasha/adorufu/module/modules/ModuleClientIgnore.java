package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.network.play.server.SPacketChat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ModuleInfo(description = "Ignore players on the client side.")
public class ModuleClientIgnore extends AdorufuModule implements SimpleListener {
    public static List<String> ignorelist = new ArrayList<>();
    public ModuleClientIgnore() {
        super("ClientIgnore", AdorufuCategory.CHAT, false);
        AdorufuMod.scheduler.schedule(() -> ignorelist = AdorufuMod.DATA_MANAGER.loadIgnorelist(), 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            this.setSuffix(ignorelist.size() + " ignored players");
        }
    }
    @SimpleEventHandler
    public void onPckRx(ClientPacketRecieveEvent e) {
        if (this.isEnabled() && e.getRecievedPacket() instanceof SPacketChat) {
            String msg = ModuleAutoIgnore.stripColours(((SPacketChat) e.getRecievedPacket()).getChatComponent().getUnformattedText());
            for (String s : ignorelist) {
                if (msg.toLowerCase().startsWith("<" + s.toLowerCase() + ">")){
                    e.setCancelled(true);
                }
            }
        }
    }
}
