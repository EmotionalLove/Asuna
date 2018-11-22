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
import com.sasha.asuna.mod.events.client.ClientPacketSendEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Created by Sasha on 10/08/2018 at 12:47 PM
 **/
@FeatureInfo(description = "Makes your hunger last longer while sprinting/jumping/etc")
public class AntiHungerFeature extends AbstractAsunaTogglableFeature implements SimpleListener, IAsunaTickableFeature {

    public AntiHungerFeature() {
        super("AntiHunger"
                , AsunaCategory.MOVEMENT,
                new AsunaFeatureOptionBehaviour(true)
                , new AsunaFeatureOption<>("Aggressive", true)
                , new AsunaFeatureOption<>("Passive", false));
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
        if (!AsunaMod.minecraft.gameSettings.keyBindAttack.isPressed() || !AsunaMod.minecraft.gameSettings.keyBindAttack.isKeyDown()) {
            if (this.getOption("Passive")) {
                return;
            }
            AsunaMod.minecraft.getConnection().sendPacket(new CPacketPlayer(false));
        }
    }

    @SimpleEventHandler
    public void packetSent(ClientPacketSendEvent e) {
        if (!this.isEnabled()) {
            return;
        }
        if (AsunaMod.minecraft.player == null) return;
        if (AsunaMod.minecraft.player.motionY > 0.05 && this.getOption("Passive")) {
            return;
        }
        if (e.getSendPacket() instanceof CPacketPlayer) {
            if (!AsunaMod.minecraft.gameSettings.keyBindAttack.isPressed() || !AsunaMod.minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                ((CPacketPlayer) e.getSendPacket()).onGround = false;
            }
        }
    }
}
