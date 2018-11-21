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

package com.sasha.asuna.mod.mixin.client;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.asuna.AsunaCommandEvent;
import com.sasha.asuna.mod.events.server.ServerPlayerInventoryCloseEvent;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 08/08/2018 at 7:53 AM
 **/
@Mixin(value = EntityPlayerSP.class, priority = 999)
public abstract class MixinEntityPlayerSP extends MixinEntityPlayer {

    @Shadow public abstract void closeScreenAndDropStack();

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String message, CallbackInfo info) {
        if (message.startsWith(AsunaMod.COMMAND_PROCESSOR.getCommandPrefix())) {
            AsunaMod.EVENT_MANAGER.invokeEvent(new AsunaCommandEvent(message));
            info.cancel();
        }
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo info) {
        Manager.Feature.tickFeatures();
    }

    @Inject(method = "closeScreen", at = @At(value = "HEAD"), cancellable = true)
    public void closeScreen(CallbackInfo info) {
        ServerPlayerInventoryCloseEvent event = new ServerPlayerInventoryCloseEvent(this.openContainer);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            this.closeScreenAndDropStack();
            info.cancel();
        }
    }
}
