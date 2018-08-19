package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sasha.adorufu.AdorufuMod.logWarn;

@ModuleInfo(description = "Automatically ignore players that are spamming in chat.")
public class ModuleAutoIgnore extends AdorufuModule implements SimpleListener {

    public static HashMap<String/* Player's name */, Integer/* VL */> spamViolationMap = new HashMap<>();
    private static String lastMessage = "";
    private static int clock = 0;
    private static ArrayList<String> removalList = new ArrayList<>();

    public ModuleAutoIgnore() {
        super("AutoIgnore", AdorufuCategory.CHAT, false);
    }

    @Override
    public void onEnable() {
        if (!ModuleManager.getModuleByName("clientignore").isEnabled()) {
            logWarn(false, "You need to enable the ClientIgnore module for" +
                    " ignored players to actually be ignored.");
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        handleIgnoring();
        clock++;
        if (clock > 160) {
            for (HashMap.Entry<String, Integer> en : spamViolationMap.entrySet()) {
                if (en.getValue() > 0) {
                    spamViolationMap.put(en.getKey(), spamViolationMap.get(en.getKey()) - 2);
                }
            }
            clock = 0;
        }
    }
    @SimpleEventHandler
    public void onChat(ClientPacketRecieveEvent e) {
        if (this.isEnabled() && e.getRecievedPacket() instanceof SPacketChat) {
            String string = stripColours(((SPacketChat) e.getRecievedPacket()).getChatComponent().getUnformattedText());
            if (string.startsWith("<")) {
                int firstBracket = string.indexOf("<");
                int lastBracket = string.indexOf(">");
                String name = string.substring(firstBracket + 1, lastBracket);
                String message = string.replaceAll("<*?.> ", "").replaceAll("\\[*?.]", "");
                if (message.contains(lastMessage) && !spamViolationMap.containsKey(name)) {
                    spamViolationMap.put(name, 10);
                }
                else if (message.contains(lastMessage) && spamViolationMap.containsKey(name)) {
                    spamViolationMap.put(name, spamViolationMap.get(name) + 20);
                }
                else if (!spamViolationMap.containsKey(name)) {
                    spamViolationMap.put(name, 1);
                }
                else {
                    spamViolationMap.put(name, spamViolationMap.get(name) + 5);
                }
                lastMessage = message;
            }
        }
    }
    static String stripColours(String txt) {
        return txt.replaceAll("\247" + ".", "");
    }
    private static void handleIgnoring() {
        for (HashMap.Entry<String, Integer> entry : spamViolationMap.entrySet()) {
            if (entry.getValue() > 40 && !ModuleClientIgnore.ignorelist.contains(entry.getKey())) {
                ModuleClientIgnore.ignorelist.add(entry.getKey());
                try {
                    AdorufuMod.DATA_MANAGER.saveIgnorelist(ModuleClientIgnore.ignorelist);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AdorufuMod.minecraft.player.sendMessage(new TextComponentString("\247" + "1--------------------------------------------------"));
                AdorufuMod.minecraft.player.sendMessage(new TextComponentString("\247" + "4" + entry.getKey() + " " + "\247" + "6was automatically ignored."));
                AdorufuMod.minecraft.player.sendMessage(new TextComponentString("\247" + "1--------------------------------------------------"));
                removalList.add(entry.getKey());
            }
        }
        for (String keys : removalList) {
            spamViolationMap.remove(keys);
        }
        removalList.clear();
    }
}
