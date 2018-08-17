package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.network.play.server.SPacketChat;

import java.util.ArrayList;

public class ModuleClientIgnore extends XdolfModule implements SimpleListener {
    public static ArrayList<String> ignorelist = new ArrayList<>();
    public ModuleClientIgnore() {
        super("ClientIgnore", XdolfCategory.CHAT, false);
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
                if (msg.contains("<" + s + ">")){
                    e.setCancelled(true);
                }
            }
        }
    }
}
