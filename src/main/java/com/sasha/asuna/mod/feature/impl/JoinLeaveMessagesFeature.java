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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientPacketRecieveEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.simplesettings.annotation.Setting;
import net.minecraft.network.play.server.SPacketPlayerListItem;

import java.util.*;

/**
 * Created by Sasha at 7:07 PM on 8/30/2018
 */
@FeatureInfo(description = "Show client-sided join/leave messages, or use greeter to announce them.")
public class JoinLeaveMessagesFeature extends AbstractAsunaTogglableFeature
        implements SimpleListener, IAsunaTickableFeature {

    public static boolean defaultLoaded = false;
    @Setting public List<String> joinMessages = new ArrayList<>();
    @Setting public List<String> leaveMessages = new ArrayList<>();
    private LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();
    {
        joinMessages.add("Welcome [player] to [server]");
        leaveMessages.add("Bye [player]");
    }

    public JoinLeaveMessagesFeature() {
        super("JoinLeaveMessages", AsunaCategory.CHAT,
                new AsunaFeatureOption<>("Greeter", false));
    }

    @Override
    public void onEnable() {
        if (AsunaMod.minecraft.getConnection() == null) {
            this.toggleState();
        }
    }

    @Override
    public void onDisable() {
        nameMap.clear();
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        if (AsunaMod.minecraft.getConnection() == null) {
            this.toggleState();
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
            //AsunaMod.logMsg("meme");
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
        if (AsunaMod.minecraft.currentScreen != null) return;
        AsunaMod.logMsg("\2477" + name + " left.");
        if (this.getOption("Greeter")) {
            if (defaultLoaded) {
                AsunaMod.logErr(false, "You haven't defined any greeter messages in the AsunaData.yml file," +
                        " located in your .minecraft folder. You should go there and add some!");
                AsunaMod.logMsg("psst, tip: when writing your greeter messages, use \"[player]\" as a placeholder for the player's name. You can also use \"[server]\" as a placeholder for the current server's IP address");
            }
            Random rand = new Random();
            String greet = leaveMessages.get(Math.abs(rand.nextInt(leaveMessages.size() - 1)));
            greet = greet.replace("[player]", name).replace("[server]", AsunaMod.minecraft.getCurrentServerData().serverIP);
            AsunaMod.minecraft.player.sendChatMessage("> " + greet);
        }
    }

    private void playerJoin(String name) {
        if (AsunaMod.minecraft.currentScreen != null) return;
        AsunaMod.logMsg("\2477" + name + " joined.");
        if (this.getOption("Greeter")) {
            if (defaultLoaded) {
                AsunaMod.logErr(false, "You haven't defined any greeter messages in the AsunaData.yml file," +
                        " located in your .minecraft folder. You should go there and add some!");
                AsunaMod.logMsg("psst, tip: when writing your greeter messages, use \"[player]\" as a placeholder for the player's name. You can also use \"[server]\" as a placeholder for the current server's IP address");
                return;
            }
            Random rand = new Random();
            String greet = joinMessages.get(Math.abs(rand.nextInt(joinMessages.size() - 1)));
            greet = greet.replace("[player]", name).replace("[server]", AsunaMod.minecraft.getCurrentServerData().serverIP);
            AsunaMod.minecraft.player.sendChatMessage("> " + greet);
        }
    }
}
