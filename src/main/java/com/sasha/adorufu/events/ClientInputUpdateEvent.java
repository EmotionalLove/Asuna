package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.util.MovementInput;

/**
 * Created by Sasha at 6:10 AM on 8/29/2018
 */
public class ClientInputUpdateEvent extends SimpleCancellableEvent {

    private MovementInput movementInput;

    public ClientInputUpdateEvent(MovementInput movementInput) {
        this.movementInput= movementInput;
    }

    public MovementInput getMovementInput() {
        return movementInput;
    }
}
