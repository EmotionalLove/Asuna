package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientOverlayRenderEvent;
import com.sasha.xdolf.gui.XdolfHUD;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinKillMyself{
    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void renderGameOverlay(float partialTicks, CallbackInfo info){
        XdolfHUD.renderScreen();
        ClientOverlayRenderEvent ev = new ClientOverlayRenderEvent(partialTicks);
        XdolfMod.EVENT_MANAGER.invokeEvent(ev);

    }
}