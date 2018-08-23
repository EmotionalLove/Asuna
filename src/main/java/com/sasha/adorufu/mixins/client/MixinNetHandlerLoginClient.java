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
