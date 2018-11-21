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
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

@FeatureInfo(description = "Press the spacebar to go faster")
public class ElytraBoostFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    @Setting
    private static float limit = 2.5f;

    public ElytraBoostFeature() {
        super("ElytraBoost", AsunaCategory.MOVEMENT);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            //this updates + displays flight speed
            if (AsunaMod.minecraft.player.isElytraFlying()) {
                double speed = Math.abs(AsunaMod.minecraft.player.motionX) + Math.abs(AsunaMod.minecraft.player.motionZ);
                this.setSuffix(round(speed, 2) + "");
            } else {
                this.setSuffix("N/A");
            }

            //actual code with limit (flightLimit)
            if (AsunaMod.minecraft.player.isElytraFlying() && AsunaMod.minecraft.gameSettings.keyBindJump.isKeyDown()) {
                float f1 = AsunaMod.minecraft.player.rotationYaw * 0.017453292F;
                if ((Math.abs(AsunaMod.minecraft.player.motionX) + Math.abs(AsunaMod.minecraft.player.motionZ)) < limit) {
                    AsunaMod.minecraft.player.motionX -= (double) (MathHelper.sin(f1) * 0.15f);
                    AsunaMod.minecraft.player.motionZ += (double) (MathHelper.cos(f1) * 0.15f);
                }
            }
        }
    }
}
