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

package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.misc.ModuleState;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.block.Block;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
@ModuleInfo(description = "Makes chosen blocks invisible so that you can find ores or other blocks.")
public class ModuleXray extends AdorufuModule {
    public static ArrayList<Block> xrayBlocks = new ArrayList<>();
    private boolean wasNightVisionsOff = false;

    public ModuleXray() {
        super("XRay", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        if (!ModuleManager.getModule("NightVision").isEnabled()) {
            ModuleManager.getModule("NightVision").forceState(ModuleState.ENABLE, true, true);
            wasNightVisionsOff = true;
        }
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (wasNightVisionsOff) {
            ModuleManager.getModule("NightVision").forceState(ModuleState.DISABLE, true, true);
            wasNightVisionsOff = false;
        }
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onTick() {

    }

    public static class XrayManager {

        public static boolean isXray;

        public static boolean isXrayBlock(Block b) {
            return xrayBlocks.contains(b);
        }
    }
}
