package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.inventory.Container;

/**
 * Created by Sasha at 12:52 PM on 9/2/2018
 */
public class ServerPlayerInventoryCloseEvent extends SimpleCancellableEvent {

    private Container inv;

    public ServerPlayerInventoryCloseEvent(Container container) {
        this.inv = container;
    }

    public Container getContainer() {
        return inv;
    }
}
