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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientInputUpdateEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;


/**
 * Created by Sasha on 11/08/2018 at 3:18 PM
 **/
@FeatureInfo(description = "Don't slow down whilst eating.")
public class NoSlowFeature extends AbstractAsunaTogglableFeature
        implements SimpleListener, IAsunaTickableFeature {
    public NoSlowFeature() {
        super("NoSlow", AsunaCategory.MOVEMENT,
                new AsunaFeatureOptionBehaviour(true),
                new AsunaFeatureOption<>("NCP", true),
                new AsunaFeatureOption<>("AAC", false));
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
    }

    @SimpleEventHandler
    public void onSlowDown(ClientInputUpdateEvent e) {
        if (this.isEnabled() && AsunaMod.minecraft.player.isHandActive()) {
            e.getMovementInput().moveForward /= this.getOption("NCP") ? 0.05f : 0.25f;
            e.getMovementInput().moveStrafe /= this.getOption("NCP") ? 0.05f : 0.25f;
        }
    }
}
