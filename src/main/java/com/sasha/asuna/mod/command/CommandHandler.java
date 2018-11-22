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

package com.sasha.asuna.mod.command;

import com.sasha.asuna.mod.events.asuna.AsunaCommandEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

import static com.sasha.asuna.mod.AsunaMod.COMMAND_PROCESSOR;

/**
 * Created by Sasha on 08/08/2018 at 7:51 AM
 **/
public class CommandHandler implements SimpleListener {

    @SimpleEventHandler
    public void onAsunaCommand(AsunaCommandEvent e) {
        if (e.getMsg().startsWith(COMMAND_PROCESSOR.getCommandPrefix()))
            COMMAND_PROCESSOR.processCommand(e.getMsg());
    }
}
