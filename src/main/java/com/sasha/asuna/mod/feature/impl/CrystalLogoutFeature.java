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
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@FeatureInfo(description = "Automatically disconnect when a crystal is near you")
public class CrystalLogoutFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public CrystalLogoutFeature() {
        super("CrystalLogout", AsunaCategory.COMBAT);
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            for (Entity e : AsunaMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    if (AsunaMod.minecraft.player.getDistance(e) <= 3.8f && !Mouse.isButtonDown(1) && e.isEntityAlive()) {
                        AsunaMod.minecraft.player.connection.getNetworkManager()
                                .closeChannel(new TextComponentString("\2476You were disconnected because you came into range with en End Crystal"));
                        this.toggleState();
                        break;
                    }
                }
            }
        }
    }
}
