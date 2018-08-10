package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleTracers;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 09/08/2018 at 7:37 PM
 **/
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V"))
    public void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo info){
        ModuleManager.renderModules();
    }
    @Inject(method = "applyBobbing", at = @At("HEAD"), cancellable = true)
    public void applyBobbing(float partialTicks, CallbackInfo info){
        if (ModuleTracers.i > 0){
            info.cancel();
            //todo make tracers bob against camera so that we dont need to do this
        }
    }
}
