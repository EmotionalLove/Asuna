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

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.simplesettings.annotation.Setting;

@FeatureInfo(description = "Lock your yaw to a specified angle")
public class YawLockFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    @Setting
    public static int yawDegrees = 90;

    public YawLockFeature() {
        super("YawLock", AsunaCategory.MOVEMENT);
    }


    @Override
    public void onTick() {
        if (this.isEnabled()) {
            AsunaMod.minecraft.player.rotationYaw = yawDegrees;
            this.setSuffix(yawDegrees + "\u00b0");
        }
    }
}
