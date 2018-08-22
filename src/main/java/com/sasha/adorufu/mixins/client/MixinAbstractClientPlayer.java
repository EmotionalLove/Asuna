package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.module.ModuleManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractClientPlayer.class, priority = 999)
public class MixinAbstractClientPlayer {
    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    public void isSpectator(CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.getModuleByName("Freecam").isEnabled()) {
            info.setReturnValue(true);
            info.cancel();
        }
    }
}
