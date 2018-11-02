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

package com.sasha.adorufu.mod.command.commands;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@SimpleCommandInfo(description = "Set a feature's keybind", syntax = {"<feature> <key>", "<feature> <'none'>"})
public class BindCommand extends SimpleCommand {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length != 2){
            AdorufuMod.logErr(false, "Arguments required! Try \"-help command bind\"");
            return;
        }
        boolean none = false;
        if (this.getArguments()[1].equalsIgnoreCase("none")) {
            none = true;
        }
        this.getArguments()[1] = this.getArguments()[1].toUpperCase();
        if (!none && Keyboard.getKeyIndex(this.getArguments()[1]) == Keyboard.KEY_NONE){
            AdorufuMod.logErr(false, "That's not a valid key!");
            return;
        }
        AtomicBoolean found = new AtomicBoolean(false);
        final boolean finalNone = none;
        Manager.Feature.getTogglableFeatures().forEachRemaining(mod -> {
            if (mod.getFeatureName().equalsIgnoreCase(this.getArguments()[0])){
                mod.setKeycode(finalNone ? 0 : Keyboard.getKeyIndex(this.getArguments()[1]));
                AdorufuMod.logMsg(false, "Changed " + mod.getFeatureName() + "'s keybind!");
                found.set(true);
            }
        });
        if (!found.get()) {
            AdorufuMod.logErr(false, "Couldn't find the specified feature.");
        }
    }
}
