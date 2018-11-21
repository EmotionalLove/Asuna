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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientPacketSendEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 12/08/2018 at 2:48 PM
 **/
@FeatureInfo(description = "Walk on water!")
public class JesusFeature extends AbstractAsunaTogglableFeature
        implements SimpleListener, IAsunaTickableFeature {
    public static AxisAlignedBB WATER_JESUS_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.99, 1);
    public static JesusFeature INSTANCE;
    private boolean waterjump = false;

    public JesusFeature() {
        super("Jesus", AsunaCategory.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            if (AsunaMod.minecraft.player.isInWater()) {
                AsunaMod.minecraft.player.motionY = 0.11;
                waterjump = true;
                return;
            }
            if (waterjump) {
                AsunaMod.minecraft.player.jump();
                waterjump = false;
            }
        }
    }

    @SimpleEventHandler
    public void onPacketTx(ClientPacketSendEvent e) {
        if (!this.isEnabled()) return;
        if (e.getSendPacket() instanceof CPacketPlayer) {
            if (isOverWater(AsunaMod.minecraft.player) && !AsunaMod.minecraft.gameSettings.keyBindSneak.isPressed()) {
                int t = AsunaMod.minecraft.player.ticksExisted % 2;
                if (t == 0) {
                    ((CPacketPlayer) e.getSendPacket()).y += 0.02;
                }
            }
        }
    }

    public boolean doJesus() {
        try {
            return this.isEnabled() && !inLiquid(AsunaMod.minecraft.player, false) && !AsunaMod.minecraft.gameSettings.keyBindSneak.isKeyDown() && !(AsunaMod.minecraft.player.fallDistance > 2);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isOverWater(Entity e) {
        try {
            BlockPos pos = e.getPosition();
            IBlockState state = AsunaMod.minecraft.world.getBlockState(pos.add(0, -1, 0));
            if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.LAVA) {
                return !AsunaMod.minecraft.player.isInWater() && state.getBlock() != Blocks.FLOWING_WATER && state.getBlock() != Blocks.FLOWING_LAVA;
            }
            return false;
        } catch (Exception ee) {
            return false;
        }
    }

    private boolean inLiquid(Entity e, boolean invert) {
        try {
            double Y = invert ? e.getPosition().getY() + 0.1 : e.getPosition().getY() + 0.1;
            Block block = AsunaMod.minecraft.world.getBlockState(new BlockPos(e.getPosition().getX(), Y, e.getPosition().getZ())).getBlock();
            return block instanceof BlockLiquid && block != Blocks.FLOWING_WATER && block != Blocks.FLOWING_LAVA;
        } catch (Exception eee) {
            return false;
        }
    }
}
