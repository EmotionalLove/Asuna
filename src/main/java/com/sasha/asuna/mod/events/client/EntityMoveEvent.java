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

package com.sasha.asuna.mod.events.client;

import com.sasha.eventsys.SimpleCancellableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;

public class EntityMoveEvent extends SimpleCancellableEvent {

    private Entity entity;

    private MoverType moverType;
    private double x;
    private double y;
    private double z;

    public EntityMoveEvent(Entity entity, World world, int entityId, MoverType moverType, double x, double y, double z) {
        this.entity = entity;
        this.moverType = moverType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public MoverType getMoverType() {
        return moverType;
    }
}
