package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.inventory.Container;

/**
 * Created by Sasha at 12:52 PM on 9/2/2018
 */
public class ClientPlayerInventoryCloseEvent extends SimpleCancellableEvent {

    private Container container;

    public ClientPlayerInventoryCloseEvent(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }
}
