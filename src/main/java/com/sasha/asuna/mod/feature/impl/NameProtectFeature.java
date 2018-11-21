/*
 * Copyright (c) Sasha Stevens 2018.
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
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

@FeatureInfo(description = "Hides your name on-screen")
public class NameProtectFeature extends AbstractAsunaTogglableFeature implements SimpleListener {
    public NameProtectFeature() {
        super("NameRedact", AsunaCategory.RENDER);
    }

    @SimpleEventHandler
    public void onChatRx(ClientPacketRecieveEvent e) {
        if (!this.isEnabled()) return;
        if (e.getRecievedPacket() instanceof SPacketChat) {
            SPacketChat chat = (SPacketChat) e.getRecievedPacket();
            if (!chat.getChatComponent().getUnformattedComponentText().contains(AsunaMod.minecraft.player.getName()))
                return;
            String str = chat.chatComponent.getFormattedText().replace(AsunaMod.minecraft.player.getName(), "[redacted]");
            chat.chatComponent = new TextComponentString(str);
        }
    }
}
