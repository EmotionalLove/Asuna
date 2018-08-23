package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientRenderFireOverlayEvent;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRenderer.class, priority = 999)
public class MixinItemRenderer {

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    private void renderFireInFirstPerson(CallbackInfo info) {
        ClientRenderFireOverlayEvent event = new ClientRenderFireOverlayEvent();
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) info.cancel();
    }
}
