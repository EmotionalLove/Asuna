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

package com.sasha.adorufu.mod.module.modules;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import com.sasha.adorufu.mod.module.ModuleInfo;

/**
 * Created by Sasha at 3:48 PM on 8/27/2018
 */
@ModuleInfo(description = "Plays the creative mode exclusive music in other gamemodes.")
public class ModuleCreativeMusic extends AdorufuModule {
    public ModuleCreativeMusic() {
        super("CreativeMusic", AdorufuCategory.MISC, false);
    }

    @Override
    public void onEnable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.getMusicTicker().update();
    }

    @Override
    public void onTick() {

    }
}
