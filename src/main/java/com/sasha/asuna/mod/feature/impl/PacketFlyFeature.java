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
import net.minecraft.network.play.client.CPacketPlayer;

@FeatureInfo(description = "NCP Flight bypass. Useful for 2b2t's nether roof.")
public class PacketFlyFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {
    public PacketFlyFeature() {
        super("PacketFly", AsunaCategory.MOVEMENT);
    }

    private static double[] moveLooking(int ignored) {
        return new double[]{AsunaMod.minecraft.player.rotationYaw * 360 / 360 * 180 / 180, ignored};
    }

    @Override
    public void onTick() {
        if (!this.isEnabled())
            return;
        double[] dir = moveLooking(0);
        double xDir = dir[0];
        double zDir = dir[1];
        AsunaMod.minecraft.player.motionX = 0;
        AsunaMod.minecraft.player.motionY = 0;
        AsunaMod.minecraft.player.motionZ = 0;
        AsunaMod.minecraft.player.connection.sendPacket(
                new CPacketPlayer.PositionRotation
                        (AsunaMod.minecraft.player.posX + AsunaMod.minecraft.player.motionX + (AsunaMod.minecraft.gameSettings.keyBindForward.isKeyDown() ? 0.0624 : 0) - (AsunaMod.minecraft.gameSettings.keyBindBack.isKeyDown() ? 0.0624 : 0)
                                , AsunaMod.minecraft.player.posY + (AsunaMod.minecraft.gameSettings.keyBindJump.isKeyDown() ? 0.0624 : 0) - (AsunaMod.minecraft.gameSettings.keyBindSneak.isKeyDown() ? 0.0624 : 0),
                                AsunaMod.minecraft.player.posZ + AsunaMod.minecraft.player.motionZ + (AsunaMod.minecraft.gameSettings.keyBindRight.isKeyDown() ? 0.0624 : 0) - (AsunaMod.minecraft.gameSettings.keyBindLeft.isKeyDown() ? 0.0624 : 0)
                                , AsunaMod.minecraft.player.rotationYaw, AsunaMod.minecraft.player.rotationPitch, false));
        AsunaMod.minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(AsunaMod.minecraft.player.posX + AsunaMod.minecraft.player.motionX, AsunaMod.minecraft.player.posY - 42069, AsunaMod.minecraft.player.posZ + AsunaMod.minecraft.player.motionZ, AsunaMod.minecraft.player.rotationYaw, AsunaMod.minecraft.player.rotationPitch, true));
    }
}
