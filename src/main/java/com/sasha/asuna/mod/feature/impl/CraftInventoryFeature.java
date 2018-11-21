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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.events.server.ServerPlayerInventoryCloseEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.inventory.ContainerPlayer;

/**
 * Created by Sasha at 12:44 PM on 9/2/2018
 */
@FeatureInfo(description = "Use the crafting slots in your inventory as inventory spaces. Also makes illegal items usable on 2b2t.")
public class CraftInventoryFeature extends AbstractAsunaTogglableFeature implements SimpleListener,
        IAsunaTickableFeature {
    public CraftInventoryFeature() {
        super("CraftInventory", AsunaCategory.MOVEMENT,
                new AsunaFeatureOption<>("Normal", true),
                new AsunaFeatureOption<>("Illegals", false));
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
    }


    @SimpleEventHandler
    public void onPckCloseInv(ServerPlayerInventoryCloseEvent e) {
        if (!this.isEnabled()) return;
        if (e.getContainer() instanceof ContainerPlayer) {
            e.setCancelled(true);
        }
        if (this.getFormattableOptionsMap().get("Illegals") && !e.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
