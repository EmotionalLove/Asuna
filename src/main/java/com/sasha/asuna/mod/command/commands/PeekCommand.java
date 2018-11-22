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

package com.sasha.asuna.mod.command.commands;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.impl.PeekFeature;
import com.sasha.asuna.mod.feature.impl.ShulkerSpyFeature;
import com.sasha.asuna.mod.misc.Manager;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import net.minecraft.inventory.IInventory;

/**
 * Created by Sasha on 08/08/2018 at 9:26 PM
 **/
@SimpleCommandInfo(description = "View another person's held shulkerbox contents, or your own.", syntax = {"[name]"})
public class PeekCommand extends SimpleCommand {
    public PeekCommand() {
        super("peek");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null) {
            Manager.Feature.findFeature(PeekFeature.class).setState(true, true);
            return;
        }
        String name = this.getArguments()[0].toLowerCase();
        if (!ShulkerSpyFeature.shulkerMap.containsKey(name.toLowerCase())) {
            AsunaMod.logErr(false, "Asuna hasn't seen this player hold a shulkerbox. Check your spelling.");
            return;
        }
        IInventory inv = ShulkerSpyFeature.shulkerMap.get(name.toLowerCase());
        if (inv == null) {
            AsunaMod.logErr(false, "Null inventory!");
            return;
        }
        AsunaMod.minecraft.displayGuiScreen(null);
        AsunaMod.minecraft.player.displayGUIChest(inv);
    }
}
