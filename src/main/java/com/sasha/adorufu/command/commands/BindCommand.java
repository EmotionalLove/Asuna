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

package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.misc.AdorufuMath;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.simplecmdsys.SimpleCommand;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@SimpleCommandInfo(description = "Set a module's keybind", syntax = {"<module> <key>", "<module> <'none'>"})
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
        if (AdorufuMath.determineKeyCode(this.getArguments()[1]) == null){
            AdorufuMod.logErr(false, "That's not a valid key!");
            return;
        }
        AtomicBoolean found = new AtomicBoolean(false);
        ModuleManager.moduleRegistry.forEach(mod -> {
            if (mod.getModuleName().equalsIgnoreCase(this.getArguments()[0])){
                mod.setKeyBind(AdorufuMath.determineKeyCode(this.getArguments()[1]));
                AdorufuMod.logMsg(false, "Changed " + mod.getModuleName() + "'s keybind!");
                AdorufuMod.scheduler.schedule(() -> {
                    try {
                        AdorufuMod.DATA_MANAGER.saveModuleBind(mod);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 0, TimeUnit.NANOSECONDS);
                found.set(true);
                return;
            }
        });
        if (!found.get()) {
            AdorufuMod.logErr(false, "Couldn't find the specified module.");
        }
    }
}
