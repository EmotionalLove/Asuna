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

package com.sasha.asuna.mod.desktop;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientScreenChangedEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sasha at 6:37 AM on 9/12/2018
 */
public class AsunaSystemTrayEventHandler implements SimpleListener {

    @SimpleEventHandler
    public void onMenuChange(ClientScreenChangedEvent e) {
        if (AsunaMod.TRAY_MANAGER.trayIcon != null) {
            AsunaMod.scheduler.schedule(() -> AsunaMod.TRAY_MANAGER.rebuild(), 0, TimeUnit.NANOSECONDS);
        }
    }
}
