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

import com.sasha.adorufu.AdorufuMod;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetHandlerLoginClient.class, priority = 999)
public class MixinNetHandlerLoginClient {
    @Inject(method = "onDisconnect", at = @At("HEAD"))
    public void onDisconnect(ITextComponent reason, CallbackInfo info) {
        AdorufuMod.logErr(true, "Failed to connect! (" + reason.getUnformattedText() + ")");
    }
}
