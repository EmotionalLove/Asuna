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

package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.modules.ModulePowerBow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Created by Sasha at 12:45 PM on 9/16/2018
 */
@Mixin(value = ItemBow.class, priority = 999)
public class MixinItemBow {

    @Inject(method = "onPlayerStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemBow;getArrowVelocity(I)F"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft, CallbackInfo info, EntityPlayer entityplayer, boolean flag, ItemStack itemstack, int i) {
        if (ModuleManager.getModule(ModulePowerBow.class).isEnabled()) {
            i *= 2;
        }
    }
}
