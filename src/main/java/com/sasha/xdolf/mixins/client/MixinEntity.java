package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientEntityCollideEvent;
import com.sasha.xdolf.module.ModuleManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollision(Entity entityIn, CallbackInfo info) {
        ClientEntityCollideEvent event = new ClientEntityCollideEvent(entityIn);
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
