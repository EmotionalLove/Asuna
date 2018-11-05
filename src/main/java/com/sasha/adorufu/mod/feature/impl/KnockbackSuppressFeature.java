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

package com.sasha.adorufu.mod.feature.impl;

import com.sasha.adorufu.mod.events.playerclient.PlayerKnockbackEvent;
import com.sasha.adorufu.mod.feature.AbstractAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.AdorufuCategory;
import com.sasha.adorufu.mod.feature.IAdorufuTickableFeature;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOption;
import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOptionBehaviour;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;


/**
 * Created by Sasha at 7:59 PM on 9/2/2018
 */
@FeatureInfo(description = "Reduce or completely ignore knockback")
public class KnockbackSuppressFeature extends AbstractAdorufuTogglableFeature
        implements SimpleListener, IAdorufuTickableFeature {
    public KnockbackSuppressFeature() {
        super("KnockbackSuppress", AdorufuCategory.COMBAT,
                new AdorufuFeatureOptionBehaviour(true),
                new AdorufuFeatureOption<>("Ignore", true),
                new AdorufuFeatureOption<>("Reduce", false));
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
    }

    @SimpleEventHandler
    public void onPlayerKnockBack(PlayerKnockbackEvent e) {
        if (!this.isEnabled()) return;
        if (this.getOption("Ignore")) {
            e.setCancelled(true);
            return;
        }
        e.setMotionX(e.getMotionX() / 3);
        e.setMotionY(e.getMotionY() / 3);
        e.setMotionZ(e.getMotionZ() / 3);
    }
}
