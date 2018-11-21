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
import com.sasha.asuna.mod.events.client.ClientGetMusicTypeEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.audio.MusicTicker;

/**
 * Created by Sasha at 3:48 PM on 8/27/2018
 */
@FeatureInfo(description = "Plays the creative mode exclusive music in other gamemodes.")
public class MinecraftMusicFeature extends AbstractAsunaTogglableFeature implements SimpleListener {
    public MinecraftMusicFeature() {
        super("MinecraftMusic", AsunaCategory.MISC,
                new AsunaFeatureOptionBehaviour(true),
                new AsunaFeatureOption<>("Creative", true),
                new AsunaFeatureOption<>("Survival", false),
                new AsunaFeatureOption<>("Nether", false),
                new AsunaFeatureOption<>("End", false),
                new AsunaFeatureOption<>("Main Menu", false));
    }

    @Override
    public void onEnable() {
        AsunaMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onDisable() {
        AsunaMod.minecraft.getMusicTicker().update();
    }

    @SimpleEventHandler
    public void onMusicSelect(ClientGetMusicTypeEvent e) {
        if (!this.isEnabled()) return;
        this.getFormattableOptionsMap().forEach((option, bool) -> {
            if (bool) {
                switch (option.toLowerCase()) {
                    case "creative":
                        e.setMusicType(MusicTicker.MusicType.CREATIVE);
                        break;
                    case "survival":
                        e.setMusicType(MusicTicker.MusicType.GAME);
                        break;
                    case "nether":
                        e.setMusicType(MusicTicker.MusicType.NETHER);
                        break;
                    case "end":
                        e.setMusicType(MusicTicker.MusicType.END);
                        break;
                    case "main Menu":
                        e.setMusicType(MusicTicker.MusicType.MENU);
                        break;
                    default:
                        e.setMusicType(MusicTicker.MusicType.CREATIVE);
                }
            }
        });
    }
}
