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

package com.sasha.adorufu.mod.feature.impl;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.events.client.ClientGetMusicTypeEvent;
import com.sasha.adorufu.mod.feature.AbstractAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.AdorufuCategory;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOption;
import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOptionBehaviour;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.audio.MusicTicker;

/**
 * Created by Sasha at 3:48 PM on 8/27/2018
 */
@FeatureInfo(description = "Plays the creative mode exclusive music in other gamemodes.")
public class MinecraftMusicFeature extends AbstractAdorufuTogglableFeature implements SimpleListener {
    public MinecraftMusicFeature() {
        super("MinecraftMusic", AdorufuCategory.MISC,
                new AdorufuFeatureOptionBehaviour(true),
                new AdorufuFeatureOption<>("Creative", true),
                new AdorufuFeatureOption<>("Survival", false),
                new AdorufuFeatureOption<>("Nether", false),
                new AdorufuFeatureOption<>("End", false),
                new AdorufuFeatureOption<>("Main Menu", false));
    }

    @Override
    public void onEnable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @SimpleEventHandler
    public void onMusicSelect(ClientGetMusicTypeEvent e) {
        if (!this.isEnabled()) return;
        this.getFormattableOptionsMap().forEach((option, bool) -> {
            if (bool) {
                switch (option.toLowerCase()) {
                    case "Creative":
                        e.setMusicType(MusicTicker.MusicType.CREATIVE);
                        break;
                    case "Survival":
                        e.setMusicType(MusicTicker.MusicType.GAME);
                        break;
                    case "Nether":
                        e.setMusicType(MusicTicker.MusicType.NETHER);
                        break;
                    case "End":
                        e.setMusicType(MusicTicker.MusicType.END);
                        break;
                    case "Main Menu":
                        e.setMusicType(MusicTicker.MusicType.MENU);
                        break;
                    default:
                        e.setMusicType(MusicTicker.MusicType.CREATIVE);
                }
            }
        });
    }
}
