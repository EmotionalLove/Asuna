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

package com.sasha.adorufu.mod.events.adorufu;

import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha at 11:54 AM on 9/16/2018
 */
public class AdorufuBatteryLevelChangedEvent extends SimpleEvent {

    private int oldBatteryPercent;
    private int newBatteryPercent;
    private boolean isCharging;

    public AdorufuBatteryLevelChangedEvent(int oldBatteryPercent, int newBatteryPercent, boolean isCharging) {
        this.oldBatteryPercent = oldBatteryPercent;
        this.newBatteryPercent = newBatteryPercent;
        this.isCharging = isCharging;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public int getNewBatteryPercent() {
        return newBatteryPercent;
    }

    public int getOldBatteryPercent() {
        return oldBatteryPercent;
    }
}
