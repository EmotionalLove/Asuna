package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import com.sasha.xdolf.events.ClientPacketSendEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

/**
 * Created by Sasha on 08/08/2018 at 8:08 PM
 **/
@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Shadow protected abstract void dispatchPacket(Packet<?> inPacket, @Nullable GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    @Shadow public INetHandler packetListener;

    @Shadow public Channel channel;

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;flushOutboundQueue()V"), cancellable = true)
    public void sendPacket(Packet<?> packetIn, CallbackInfo info){
        ClientPacketSendEvent event = new ClientPacketSendEvent(packetIn);
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()){
            info.cancel();
            return;
        }
        this.dispatchPacket(event.getSendPacket(), null);
        info.cancel();
    }
    /**
     * @author Sasha Stevens
     * @reason Setup packet recieve event without the unnecessary wizardry
     */
    @Inject(method = "channelRead0",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V"), cancellable = true)
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo info) throws Exception {
        ClientPacketRecieveEvent event = new ClientPacketRecieveEvent(p_channelRead0_2_);
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()){
            info.cancel();
            return;
        }
        ((Packet<INetHandler>)event.getRecievedPacket()).processPacket(this.packetListener);
        info.cancel();
    }
}
