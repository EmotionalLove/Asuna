/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.events.client;

import com.sasha.eventsys.SimpleEvent;

public class ClientItemSpawnEvent extends SimpleEvent {

    private int x, y, z;

    public ClientItemSpawnEvent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getCoordinate() {
        return new int[]{this.x, this.y, this.z};
    }

}
