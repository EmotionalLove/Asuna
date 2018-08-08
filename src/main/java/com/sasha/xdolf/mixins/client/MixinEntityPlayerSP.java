package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.events.PlayerXdolfCommandEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 08/08/2018 at 7:53 AM
 **/
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String message, CallbackInfo info) {
        if (message.startsWith(XdolfCommand.commandDelimetre)) {
            XdolfMod.EVENT_MANAGER.invokeEvent(new PlayerXdolfCommandEvent(message));
            info.cancel();
        }
    }
}
