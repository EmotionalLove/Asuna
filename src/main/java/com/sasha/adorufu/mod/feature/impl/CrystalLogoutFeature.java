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

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.AbstractAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.AdorufuCategory;
import com.sasha.adorufu.mod.feature.IAdorufuTickableFeature;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@FeatureInfo(description = "Automatically disconnect when a crystal is near you")
public class CrystalLogoutFeature extends AbstractAdorufuTogglableFeature implements IAdorufuTickableFeature {

    public CrystalLogoutFeature() {
        super("CrystalLogout", AdorufuCategory.COMBAT);
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            for (Entity e : AdorufuMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    //float attacktime = mc.player.getCooledAttackStrength(1);
                    if (AdorufuMod.minecraft.player.getDistance(e) <= 3.8f && !Mouse.isButtonDown(1)/* && mc.player.canEntityBeSeen(e)*/) {
                        if (e.isEntityAlive()) {
                            AdorufuMod.minecraft.player.connection.getNetworkManager().closeChannel(new TextComponentString("\2476You were disconnected because you came into range with en End Crystal"));
                            this.toggleState();
                            break;
                        }
                    }
                }
            }
        }
    }
}
