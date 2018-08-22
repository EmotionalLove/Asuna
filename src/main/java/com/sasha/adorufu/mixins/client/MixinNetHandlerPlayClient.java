package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientItemSpawnEvent;
import com.sasha.adorufu.events.ServerGenerateChunkEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetHandlerPlayClient.class, priority = 999)
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
    @Inject(method = "handleSpawnObject", at = @At(value = "NEW", target = "net/minecraft/entity/item/EntityItem"))
    public void handleSpawnObject(SPacketSpawnObject packetIn, CallbackInfo info) {
        ClientItemSpawnEvent event = new ClientItemSpawnEvent((int)packetIn.getX(), (int)packetIn.getY(),(int) packetIn.getZ());
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
    }

}
