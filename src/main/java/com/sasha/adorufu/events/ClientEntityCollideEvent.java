package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.entity.Entity;

public class ClientEntityCollideEvent extends SimpleCancellableEvent {
    private Entity collidingEntity;
    public ClientEntityCollideEvent(Entity collidingEntity) {
        this.collidingEntity = collidingEntity;
    }

    public Entity getCollidingEntity() {
        return collidingEntity;
    }
}
