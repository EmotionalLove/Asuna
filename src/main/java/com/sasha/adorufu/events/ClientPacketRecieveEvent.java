package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.network.Packet;

/**
 * Created by Sasha on 08/08/2018 at 8:03 PM
 **/
public class ClientPacketRecieveEvent extends SimpleCancellableEvent {

    private Packet<?> recievedPacket;

    public ClientPacketRecieveEvent(Packet<?> recievedPacket){
        this.recievedPacket = recievedPacket;
    }

    public Packet<?> getRecievedPacket() {
        return recievedPacket;
    }

    public void setRecievedPacket(Packet<?> recievedPacket) {
        this.recievedPacket = recievedPacket;
    }
}
