/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

import static com.sasha.asuna.mod.feature.impl.KillauraFeature.rotateTowardsEntity;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@FeatureInfo(description = "Automatically hit nearby crystals")
public class CrystalAuraFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {
    public CrystalAuraFeature() {
        super("CrystalAura", AsunaCategory.COMBAT,
                new AsunaFeatureOptionBehaviour(true),
                new AsunaFeatureOption<>("Aura", true),
                new AsunaFeatureOption<>("Auto", false, e -> {
                    if (e) AsunaMod.logWarn(false, "This mode isn't currently available!");
                }),
                new AsunaFeatureOption<>("Auto All", false, e -> {
                    if (e) AsunaMod.logWarn(false, "This mode isn't currently available!");
                }));
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            this.setSuffix(this.getFormattableOptionsMap());
            if (this.getFormattableOptionsMap().get("Aura")) {
                for (Entity e : AsunaMod.minecraft.world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (AsunaMod.minecraft.player.getDistance(e) <= 3.8f) {
                            if (Mouse.isButtonDown(1) && AsunaMod.minecraft.player.inventory.getCurrentItem().getItem() != Items.END_CRYSTAL) {
                                return; // dont do anything if the player is eating or anything
                            }
                            if (e.isEntityAlive()) {
                                float yaw = AsunaMod.minecraft.player.rotationYaw;
                                float pitch = AsunaMod.minecraft.player.rotationPitch;
                                float yawHead = AsunaMod.minecraft.player.rotationYawHead;
                                boolean wasSprinting = AsunaMod.minecraft.player.isSprinting();
                                rotateTowardsEntity(e);
                                AsunaMod.minecraft.player.setSprinting(false);
                                AsunaMod.minecraft.playerController.attackEntity(AsunaMod.minecraft.player, e);
                                AsunaMod.minecraft.player.swingArm(EnumHand.MAIN_HAND);
                                AsunaMod.minecraft.player.rotationYaw = yaw;
                                AsunaMod.minecraft.player.rotationPitch = pitch;
                                AsunaMod.minecraft.player.rotationYawHead = yawHead;
                                if (wasSprinting) {
                                    AsunaMod.minecraft.player.setSprinting(true);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
