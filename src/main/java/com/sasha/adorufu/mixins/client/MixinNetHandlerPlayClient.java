/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientEnderPearlSpawnEvent;
import com.sasha.adorufu.events.ClientItemSpawnEvent;
import com.sasha.adorufu.events.PlayerKnockbackEvent;
import com.sasha.adorufu.events.ServerGenerateChunkEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.world.Explosion;
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

    @Shadow private Minecraft gameController;

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
    @Inject(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Explosion;doExplosionB(Z)V"), cancellable = true,
    locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleExplosion(SPacketExplosion packetIn, CallbackInfo info, Explosion explosion) {
        explosion.doExplosionB(true);
        PlayerKnockbackEvent event = new PlayerKnockbackEvent(packetIn.getMotionX(), packetIn.getMotionY(), packetIn.getMotionZ());
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) info.cancel();
        this.gameController.player.motionX += event.getMotionX();
        this.gameController.player.motionY += event.getMotionY();
        this.gameController.player.motionZ += event.getMotionZ();
        info.cancel();
    }
}
