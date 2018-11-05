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

package com.sasha.adorufu.mod.mixin.client;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.events.client.ClientPacketRecieveEvent;
import com.sasha.adorufu.mod.events.client.ClientPacketSendEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Sasha on 08/08/2018 at 8:08 PM
 **/
@Mixin(value = NetworkManager.class, priority = 999)
public abstract class MixinNetworkManager {

    @Shadow
    public INetHandler packetListener;
    @Shadow
    public Channel channel;
    @Shadow
    @Final
    public Queue<NetworkManager.InboundHandlerTuplePacketListener> outboundPacketsQueue;
    @Shadow
    @Final
    public ReentrantReadWriteLock readWriteLock;

    @Shadow
    protected abstract void dispatchPacket(Packet<?> inPacket, @Nullable GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    @Shadow
    public abstract boolean isChannelOpen();

    @Shadow
    protected abstract void flushOutboundQueue();

    /**
     * @author
     */
    @Overwrite
    public void sendPacket(Packet<?> packetIn) {
        if (this.isChannelOpen()) {
            ClientPacketSendEvent event = new ClientPacketSendEvent(packetIn);
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
            if (event.isCancelled()) return;
            this.flushOutboundQueue();
            this.dispatchPacket(event.getSendPacket(), (GenericFutureListener[]) null);
        } else {
            this.readWriteLock.writeLock().lock();

            try {
                this.outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(packetIn, new GenericFutureListener[0]));
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        }
    }

    /*@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;flushOutboundQueue()V"), cancellable = true)
    public void sendPacket(Packet<?> packetIn, CallbackInfo info) {
        ClientPacketSendEvent event = new ClientPacketSendEvent(packetIn);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
            return;
        }
        this.dispatchPacket(event.getSendPacket(), null);
        info.cancel();
    }*/

    /**
     * @author
     */
    @Overwrite
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_) throws Exception {
        if (this.channel.isOpen()) {
            try {
                ClientPacketRecieveEvent event = new ClientPacketRecieveEvent(p_channelRead0_2_);
                AdorufuMod.EVENT_MANAGER.invokeEvent(event);
                if (event.isCancelled()) {
                    return;
                }
                ((Packet<INetHandler>) event.getRecievedPacket()).processPacket(this.packetListener);
            } catch (ThreadQuickExitException ignored) {
                //
            }
        }
    }
/*
    @Inject(method = "channelRead0",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V"), cancellable = true)
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo info) throws Exception {
        ClientPacketRecieveEvent event = new ClientPacketRecieveEvent(p_channelRead0_2_);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
            return;
        }
        ((Packet<INetHandler>) event.getRecievedPacket()).processPacket(this.packetListener);
        info.cancel();
    }*/
}
