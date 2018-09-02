package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientEnderPearlSpawnEvent;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = NetHandlerPlayClient.class, priority = 999)
public class MixinNetHandlerPlayClient {

    @Shadow
    private WorldClient clientWorldController;

    @Inject(method = "handleChunkData",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getChunkFromChunkCoords(II)Lnet/minecraft/world/chunk/Chunk;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleChunkData(SPacketChunkData packetIn, CallbackInfo info, /*local*/ Chunk chunk) {
        ServerGenerateChunkEvent event = new ServerGenerateChunkEvent(chunk, packetIn, chunk.x, chunk.z);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
    }

    @Inject(method = "handleSpawnObject", at = @At(value = "NEW", target = "net/minecraft/entity/item/EntityItem"))
    public void handleSpawnObject(SPacketSpawnObject packetIn, CallbackInfo info) {
        ClientItemSpawnEvent event = new ClientItemSpawnEvent((int) packetIn.getX(), (int) packetIn.getY(), (int) packetIn.getZ());
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
    }
    @Inject(method = "handleSpawnObject", at = @At(value = "NEW", target = "net/minecraft/entity/item/EntityEnderPearl"))
    public void handleSpawnObject$0(SPacketSpawnObject packetIn, CallbackInfo info) {
        ClientEnderPearlSpawnEvent event = new ClientEnderPearlSpawnEvent((int) packetIn.getX(), (int) packetIn.getY(), (int) packetIn.getZ());
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
    }
    /* These methods collide with optifine's injections and break stuff! ;-;
    @Inject(method = "handlePlayerListItem", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void handlePlayerListItem(SPacketPlayerListItem packetIn, CallbackInfo info, SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata) {
        try {
            ServerPlayerLeaveEvent event = new ServerPlayerLeaveEvent(spacketplayerlistitem$addplayerdata.getProfile());
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        }catch (Exception e) {e.printStackTrace();}
    }
    @Inject(method = "handlePlayerListItem", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void handlePlayerListItem$0(SPacketPlayerListItem packetIn, CallbackInfo info, SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata, NetworkPlayerInfo networkPlayerInfo) {
        try {
            ServerPlayerJoinEvent event = new ServerPlayerJoinEvent(networkPlayerInfo.getGameProfile());
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        }catch (Exception e){e.printStackTrace();}
    }
*/
}
