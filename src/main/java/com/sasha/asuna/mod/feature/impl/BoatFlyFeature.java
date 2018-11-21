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
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;

/**
 * Created by Sasha at 3:48 PM on 9/24/2018
 */
public class BoatFlyFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public BoatFlyFeature() {
        super("BoatFly", AsunaCategory.MOVEMENT,
                new AsunaFeatureOption<>("Yawlock", false),
                new AsunaFeatureOption<>("Gravity", true),
                new AsunaFeatureOption<>("All Entities", false));
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
        if (AsunaMod.minecraft.player.isRiding()) {
            if (AsunaMod.minecraft.player.getRidingEntity() instanceof EntityBoat || this.getFormattableOptionsMap().get("All Entities")) {
                Entity e = AsunaMod.minecraft.player.getRidingEntity();
                if (e == null) return;
                if (!this.getFormattableOptionsMap().get("Gravity")) {
                    e.setNoGravity(true);
                } else {
                    e.setNoGravity(false);
                }
                if (this.getFormattableOptionsMap().get("Yawlock"))
                    e.rotationYaw = (AsunaMod.minecraft.player.rotationYaw);
                // actual boatfly
                if (AsunaMod.minecraft.gameSettings.keyBindJump.isPressed()) {
                    e.motionY = 0.5f;
                    return;
                }
                if (AsunaMod.minecraft.gameSettings.keyBindPlayerList.isPressed()) {
                    e.motionY = -0.5f;
                    return;
                }
                e.motionY = 0f;
            }
        }
    }
}
