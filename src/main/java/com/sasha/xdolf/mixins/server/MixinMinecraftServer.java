package com.sasha.xdolf.mixins.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 06/08/2018 at 9:48 PM
 **/
@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "initialWorldChunkLoad", at = @At("HEAD"), cancellable = true)
    public void initialWorldChunkLoad(CallbackInfo callbackInfo){
        System.out.print("ksk");
    }
}
