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

package com.sasha.asuna.mod.mixin.client;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.playerclient.PlayerJumpEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = EntityLivingBase.class, priority = 999)
public abstract class MixinEntityLivingBase {


    @Shadow public float moveForward;
    @Shadow public float moveStrafing;

    @Shadow @Nullable public abstract PotionEffect getActivePotionEffect(Potion potionIn);

    /**
     * @author Sasha Stevens
     * @reason bc ZOZZLE ofc
     */
    @Inject(method = "getJumpUpwardsMotion", at = @At("HEAD"), cancellable = true)
    protected void getJumpUpwardsMotion(CallbackInfoReturnable<Float> info) {
        PlayerJumpEvent event = new PlayerJumpEvent(0.42f);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        info.setReturnValue(event.getJumpHeight());
        info.cancel();
    }
}
