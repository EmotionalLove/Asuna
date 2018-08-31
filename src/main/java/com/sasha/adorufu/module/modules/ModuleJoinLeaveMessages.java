package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketPlayerListItem;

/**
 * Created by Sasha at 7:07 PM on 8/30/2018
 */
public class ModuleJoinLeaveMessages extends AdorufuModule implements SimpleListener {
    public ModuleJoinLeaveMessages() {
        super("JoinLeaveMessages", AdorufuCategory.CHAT, false);
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

    private void playerJoin(String name) {
        AdorufuMod.logMsg("\2477" + name + " joined.");
    }
    private void playerLeave(String name) {
        AdorufuMod.logMsg("\2477" + name + " left.");
    }

    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e) {
        if (!this.isEnabled()) return;
        if (e.getRecievedPacket() instanceof SPacketPlayerListItem /* the packet that's sent when the server adds
        someone to the tablist*/) {
            SPacketPlayerListItem pck = (SPacketPlayerListItem) e.getRecievedPacket();
            switch (pck.getAction()) {
                case ADD_PLAYER:
                    pck.getEntries().forEach(entry -> playerJoin(entry.getProfile().getName()));
                    break;
                case REMOVE_PLAYER:
                    pck.getEntries().forEach(entry -> playerLeave(entry.getProfile().getName())); //todo fix npe
                    break;
            }
        }
    }
}
