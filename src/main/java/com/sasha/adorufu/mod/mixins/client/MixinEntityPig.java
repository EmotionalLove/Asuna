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

package com.sasha.adorufu.mod.mixins.client;

import com.sasha.adorufu.mod.feature.impl.PigControlFeature;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(value = EntityPig.class, priority = 999)
public abstract class MixinEntityPig {
    @Shadow @Nullable public abstract Entity getControllingPassenger();

    /**
     * @author Sasha Stevens
     * @reason pig control
     */
    @Overwrite
    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();

        if (!(entity instanceof EntityPlayer))
        {
            return false;
        }
        else
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (Manager.Feature.isFeatureEnabled(PigControlFeature.class)) {
                return true;
            }
            return entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }
}
