/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.mixin.client;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientEnderPearlSpawnEvent;
import com.sasha.asuna.mod.events.client.ClientItemSpawnEvent;
import com.sasha.asuna.mod.events.playerclient.PlayerKnockbackEvent;
import com.sasha.asuna.mod.events.server.ServerLoadChunkEvent;
import com.sasha.asuna.mod.feature.impl.AutoReconnectFeature;
import com.sasha.asuna.mod.gui.GuiDisconnectedAuto;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.world.Explosion;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = NetHandlerPlayClient.class, priority = 999)
public class MixinNetHandlerPlayClient {
    @Shadow
    private Minecraft client;

    @Shadow
    @Final
    private GuiScreen guiScreenServer;

    @Inject(method = "handleChunkData",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleChunkData(SPacketChunkData packetIn, CallbackInfo info, /*local*/ Chunk chunk) {
        ServerLoadChunkEvent event = new ServerLoadChunkEvent(chunk, packetIn, chunk.x, chunk.z);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
    }

    @Inject(method = "handleSpawnObject", at = @At(value = "NEW", target = "net/minecraft/entity/item/EntityItem"))
    public void handleSpawnObject(SPacketSpawnObject packetIn, CallbackInfo info) {
        ClientItemSpawnEvent event = new ClientItemSpawnEvent((int) packetIn.getX(), (int) packetIn.getY(), (int) packetIn.getZ());
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
    }

    @Inject(method = "handleSpawnObject", at = @At(value = "NEW", target = "net/minecraft/entity/item/EntityEnderPearl"))
    public void handleSpawnObject$0(SPacketSpawnObject packetIn, CallbackInfo info) {
        ClientEnderPearlSpawnEvent event = new ClientEnderPearlSpawnEvent((int) packetIn.getX(), (int) packetIn.getY(), (int) packetIn.getZ());
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
    }

    @Inject(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Explosion;doExplosionB(Z)V"), cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleExplosion(SPacketExplosion packetIn, CallbackInfo info, Explosion explosion) {
        explosion.doExplosionB(true);
        PlayerKnockbackEvent event = new PlayerKnockbackEvent(packetIn.getMotionX(), packetIn.getMotionY(), packetIn.getMotionZ());
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) info.cancel();
        this.client.player.motionX += event.getMotionX();
        this.client.player.motionY += event.getMotionY();
        this.client.player.motionZ += event.getMotionZ();
        info.cancel();
    }

    @Redirect(
            method = "onDisconnect",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/Minecraft.displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"
            )
    )
    private void onDisconnectDisplayScreen(Minecraft mc, GuiScreen screen) {
        if (screen instanceof GuiDisconnected && Manager.Feature.isFeatureEnabled(AutoReconnectFeature.class)) {
            mc.displayGuiScreen(new GuiDisconnectedAuto(
                    new GuiMultiplayer(new GuiMainMenu()),
                    "disconnect.lost",
                    ((GuiDisconnected) screen).message,
                    AutoReconnectFeature.delay,
                    AutoReconnectFeature.serverData
            ));
            return;
        }
        mc.displayGuiScreen(screen);
    }
}
