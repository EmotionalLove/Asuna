package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.events.PlayerXdolfCommandEvent;
import com.sasha.xdolf.module.ModuleManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 08/08/2018 at 7:53 AM
 **/
@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal = 3), cancellable = true)
    public void renderGameOverlay(float partialTicks, CallbackInfo info) {
        XdolfMod.HUD.renderScreen();
    }
}