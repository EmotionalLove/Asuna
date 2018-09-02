package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketPlayerListItem;

import java.util.*;

/**
 * Created by Sasha at 7:07 PM on 8/30/2018
 */
public class ModuleJoinLeaveMessages extends AdorufuModule implements SimpleListener {

    private LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();
    public static boolean defaultloaded = false;
    public static List<String> joinMessages = new ArrayList<>();
    public static List<String> leaveMessages = new ArrayList<>();

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
                        if (!nameMap.containsKey(item.getProfile().getId())) {
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
        if (AdorufuMod.minecraft.currentScreen != null) return;
        AdorufuMod.logMsg("\2477" + name + " left.");
        if (this.getOption("Greeter")) {
            if (defaultloaded) {
                AdorufuMod.logErr(false, "You haven't defined any greeter messages in the AdorufuData.yml file," +
                        " located in your .minecraft folder. You should go there and add some!");
                AdorufuMod.logMsg("psst, tip: when writing your greeter messages, use \"[player]\" as a placeholder for the player's name. You can also use \"[server]\" as a placeholder for the current server's IP address");
            }
            Random rand = new Random();
            String greet = leaveMessages.get(Math.abs(rand.nextInt(leaveMessages.size() - 1)));
            greet = greet.replace("[player]", name).replace("[server]", AdorufuMod.minecraft.getCurrentServerData().serverIP);
            AdorufuMod.minecraft.player.sendChatMessage("> " + greet);
        }
    }
    private void playerJoin(String name) {
        if (AdorufuMod.minecraft.currentScreen != null) return;
        AdorufuMod.logMsg("\2477" + name + " joined.");
        if (this.getOption("Greeter")) {
            if (defaultloaded) {
                AdorufuMod.logErr(false, "You haven't defined any greeter messages in the AdorufuData.yml file," +
                        " located in your .minecraft folder. You should go there and add some!");
                AdorufuMod.logMsg("psst, tip: when writing your greeter messages, use \"[player]\" as a placeholder for the player's name. You can also use \"[server]\" as a placeholder for the current server's IP address");
                return;
            }
            Random rand = new Random();
            String greet = joinMessages.get(Math.abs(rand.nextInt(joinMessages.size() - 1)));
            greet = greet.replace("[player]", name).replace("[server]", AdorufuMod.minecraft.getCurrentServerData().serverIP);
            AdorufuMod.minecraft.player.sendChatMessage("> " + greet);
        }
    }
}
