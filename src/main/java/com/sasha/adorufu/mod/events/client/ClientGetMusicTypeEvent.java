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

package com.sasha.adorufu.mod.events.client;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.client.audio.MusicTicker;

/**
 * Created by Sasha at 9:25 AM on 9/20/2018
 */
public class ClientGetMusicTypeEvent extends SimpleEvent {

    private MusicTicker.MusicType musicType;

    public ClientGetMusicTypeEvent(MusicTicker.MusicType musicType) {
        this.musicType = musicType;
    }

    public MusicTicker.MusicType getMusicType() {
        return musicType;
    }

    public void setMusicType(MusicTicker.MusicType musicType) {
        this.musicType = musicType;
    }
}
