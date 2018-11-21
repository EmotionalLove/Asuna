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
import com.sasha.asuna.mod.misc.PlayerIdentity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;

@FeatureInfo(description = "Show who tamed a tameable entity")
public class TamedIdentityFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {


    public TamedIdentityFeature() {
        super("TamedIdentity", AsunaCategory.RENDER);
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            for (Entity entity : AsunaMod.minecraft.world.getLoadedEntityList()) {
                if (entity instanceof EntityTameable) {
                    EntityTameable tameableEntity = (EntityTameable) entity;
                    if (tameableEntity.isTamed() && tameableEntity.getOwnerId() != null) {
                        tameableEntity.setAlwaysRenderNameTag(true);
                        PlayerIdentity identity = AsunaMod.DATA_MANAGER.getPlayerIdentity(tameableEntity.getOwnerId().toString());
                        tameableEntity.setCustomNameTag(identity.getDisplayName());
                    }
                }
                if (entity instanceof AbstractHorse) {
                    AbstractHorse tameableEntity = (AbstractHorse) entity;
                    if (tameableEntity.isTame() && tameableEntity.getOwnerUniqueId() != null) {
                        tameableEntity.setAlwaysRenderNameTag(true);
                        PlayerIdentity identity = AsunaMod.DATA_MANAGER.getPlayerIdentity(tameableEntity.getOwnerUniqueId().toString());
                        tameableEntity.setCustomNameTag(identity.getDisplayName());
                    }
                }
            }
        }
    }
}
