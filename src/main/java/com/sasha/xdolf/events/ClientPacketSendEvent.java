package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.network.Packet;

/**
 * Created by Sasha on 08/08/2018 at 8:02 PM
 **/
public class ClientPacketSendEvent extends SimpleCancellableEvent {

    private Packet<?> sendPacket;

    public ClientPacketSendEvent(Packet<?> sendPacket){
        this.sendPacket= sendPacket;
    }

    public Packet<?> getSendPacket() {
        return sendPacket;
    }

    public void setSendPacket(Packet<?> sendPacket) {
        this.sendPacket = sendPacket;
    }
}
