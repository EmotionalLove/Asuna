package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketPlayerListItem;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by Sasha at 7:07 PM on 8/30/2018
 */
public class ModuleJoinLeaveMessages extends AdorufuModule implements SimpleListener {

    private LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();

    public ModuleJoinLeaveMessages() {
        super("JoinLeaveMessages", AdorufuCategory.CHAT, false, true);
        this.addOption("Greeter", false);
    }

    @Override
    public void onEnable() {
        if (AdorufuMod.minecraft.getConnection() == null) {
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        nameMap.clear();
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        if (AdorufuMod.minecraft.getConnection() == null) {
            this.toggle();
        }
        LinkedHashMap<String, Boolean> suffixMap = new LinkedHashMap<>();
        suffixMap.put("Greeter", this.getOption("Greeter"));
        this.setSuffix(suffixMap);
    }

    /**
     * When a tab entry is removed, the player's name is already set to null by the client.
     * We have to cache the player's name to their UUID when they join so we can use it later.
     */
    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e) {
        if (!this.isEnabled()) return;
        if (e.getRecievedPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem pck = (SPacketPlayerListItem) e.getRecievedPacket();
            //AdorufuMod.logMsg("meme");
            for (SPacketPlayerListItem.AddPlayerData item : pck.getEntries()) {
                switch (pck.getAction()) {
                    case REMOVE_PLAYER:
                        if (nameMap.containsKey(item.getProfile().getId())) {
                            return;
                        }
                        playerLeave(nameMap.get(item.getProfile().getId()));
                        nameMap.remove(item.getProfile().getId());
                        break;
                    case ADD_PLAYER:
                        playerJoin(item.getProfile().getName());
                        nameMap.put(item.getProfile().getId(), item.getProfile().getName());
                        break;
                    default:
                        return;
                }
            }
        }
    }

    private void playerLeave(String name) {
        AdorufuMod.logMsg("\2477" + name + " left.");
    }
    private void playerJoin(String name) {
        AdorufuMod.logMsg("\2477" + name + " joined.");
    }

}
