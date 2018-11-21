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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.asuna.AsunaBatteryLevelChangedEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;


/**
 * Created by Sasha on 08/08/2018 at 7:51 PM
 **/
@FeatureInfo(description = "Displays your computer's battery percent.")
public class BatteryLifeRenderableFeature extends AbstractAsunaTogglableFeature implements SimpleListener {

    public BatteryLifeRenderableFeature() {
        super("BatteryLife", AsunaCategory.GUI);
    }

    @SimpleEventHandler
    public void onBtCnge(AsunaBatteryLevelChangedEvent e) {
        if (this.isEnabled()) {
            if (e.isCharging()) return;
            if (e.getNewBatteryPercent() <= 20 && e.getOldBatteryPercent() > 20) {
                AsunaMod.logWarn(false, "Low battery! 20% remains.");
                return;
            }
            if (e.getNewBatteryPercent() <= 10 && e.getOldBatteryPercent() > 10) {
                AsunaMod.logWarn(false, "Very low battery! 10% remains.");
            }
        }
    }
}
