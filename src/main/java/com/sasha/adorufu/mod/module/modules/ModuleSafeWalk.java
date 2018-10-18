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

package com.sasha.adorufu.mod.module.modules;

import com.sasha.adorufu.mod.events.client.EntityMoveEvent;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;

import static com.sasha.adorufu.mod.AdorufuMod.minecraft;

public class ModuleSafeWalk extends AdorufuModule implements SimpleListener {
    public ModuleSafeWalk() {
        super("SafeWalk", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
    @SimpleEventHandler
    public void onMove(EntityMoveEvent e) {
        if (!this.isEnabled()) return;
        double d2 = e.getX();
        if ((e.getMoverType() == MoverType.SELF || e.getMoverType() == MoverType.PLAYER) && e.getEntity().onGround && this.isEnabled() && e.getEntity() instanceof EntityPlayer) {
            for (double d5 = 0.05D; e.getX() != 0.0D && minecraft.world.getCollisionBoxes(e.getEntity(), e.getEntity().getEntityBoundingBox().offset(e.getX(), (double) (-e.getEntity().stepHeight), 0.0D)).isEmpty(); d2 = e.getX()) {
                if (e.getX() < 0.05D && e.getX() >= -0.05D) {
                    e.setX(0.0D);
                } else if (e.getX() > 0.0D) {
                    e.setX(e.getX() - 0.05d);
                } else {
                    e.setX(e.getX() + 0.05d);
                }
            }
        }
    }
}
