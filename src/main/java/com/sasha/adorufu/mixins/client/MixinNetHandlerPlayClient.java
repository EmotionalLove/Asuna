package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ServerGenerateChunkEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow private WorldClient clientWorldController;

    @Inject(method = "handleChunkData", at = @At("HEAD"), cancellable = true)
    public void handleChunkData(SPacketChunkData packetIn, CallbackInfo info) {
        Chunk chk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
        if (chk.isTerrainPopulated()) {
            ServerGenerateChunkEvent event = new ServerGenerateChunkEvent(chk.x, chk.z);
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        }
    }

}
