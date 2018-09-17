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

package com.sasha.adorufu.events.server;

import com.mojang.authlib.GameProfile;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 9:21 PM on 9/1/2018
 */
public class ServerPlayerJoinEvent extends SimpleEvent {

    private GameProfile gp;

    public ServerPlayerJoinEvent(GameProfile gp) {this.gp = gp;}

    public GameProfile getGameProfile() {
        return gp;
    }
}
