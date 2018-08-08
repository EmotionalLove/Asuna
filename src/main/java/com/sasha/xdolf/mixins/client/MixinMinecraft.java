package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 06/08/2018 at 9:48 PM
 **/
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "initialWorldChunkLoad", at = @At("HEAD"), cancellable = true)
    public void initialWorldChunkLoad(CallbackInfo callbackInfo){
        XdolfMod.logger.log(Level.FATAL,"086 is the best tysm <3 (btw this is a mixin)");
    }
}
