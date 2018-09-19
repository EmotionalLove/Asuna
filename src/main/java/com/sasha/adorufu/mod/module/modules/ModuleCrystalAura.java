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

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.module.ModuleInfo;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

import static com.sasha.adorufu.mod.module.modules.ModuleKillaura.rotateTowardsEntity;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@ModuleInfo(description = "Automatically hit nearby crystals")
public class ModuleCrystalAura extends AdorufuModule {
    public ModuleCrystalAura() {
        super("CrystalAura", AdorufuCategory.COMBAT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            for (Entity e : AdorufuMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    //float attacktime = AdorufuMod.minecraft.player.getCooledAttackStrength(1);
                    if (AdorufuMod.minecraft.player.getDistance(e) <= 3.8f/* && AdorufuMod.minecraft.player.canEntityBeSeen(e)*/) {
                        if(Mouse.isButtonDown(1) && AdorufuMod.minecraft.player.inventory.getCurrentItem().getItem() != Items.END_CRYSTAL){
                            return; //dont fuggin do anythint
                        }
                        if (e.isEntityAlive()) {
                            float yaw =  AdorufuMod.minecraft.player.rotationYaw;
                            float pitch =  AdorufuMod.minecraft.player.rotationPitch;
                            float yawh =  AdorufuMod.minecraft.player.rotationYawHead;
                            boolean wasSprinting = AdorufuMod.minecraft.player.isSprinting();
                            rotateTowardsEntity(e);
                            AdorufuMod.minecraft.player.setSprinting(false);
                            AdorufuMod.minecraft.playerController.attackEntity(AdorufuMod.minecraft.player, e);
                            AdorufuMod.minecraft.player.swingArm(EnumHand.MAIN_HAND);
                            AdorufuMod.minecraft.player.rotationYaw = yaw;
                            AdorufuMod.minecraft.player.rotationPitch = pitch;
                            AdorufuMod.minecraft.player.rotationYawHead = yawh;
                            if (wasSprinting) {
                                AdorufuMod.minecraft.player.setSprinting(true);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
