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
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.gui.hud.Direction;
import com.sasha.asuna.mod.misc.AsunaMath;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha at 3:55 PM on 9/23/2018
 */
public class AutoWalkFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {
    private static int timer = 0;

    public AutoWalkFeature() {
        super("AutoWalk", AsunaCategory.MOVEMENT,
                new AsunaFeatureOption<>("Normal", true),
                new AsunaFeatureOption<>("Pathfinder", false, e -> {
                    if (e) AsunaMod.logWarn(false, "This feature is obsolete. Try using the \"-path\" command instead!");
                }));
    }

    @Deprecated
    private static boolean isBlockBlockingInFront() {
        Direction dir = AsunaMath.getCardinalDirection(AsunaMod.minecraft.player.rotationYaw);
        if (dir == Direction.posX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 1.2, AsunaMod.minecraft.player.posY, AsunaMod.minecraft.player.posZ)).getBlock();
            return b.fullBlock;
        }
        if (dir == Direction.posZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY, AsunaMod.minecraft.player.posZ + 1.2)).getBlock();
            return b.fullBlock;
        }
        if (dir == Direction.negX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX - 1.2, AsunaMod.minecraft.player.posY, AsunaMod.minecraft.player.posZ)).getBlock();
            return b.fullBlock;
        }
        if (dir == Direction.negZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY, AsunaMod.minecraft.player.posZ - 1.2)).getBlock();
            return b.fullBlock;
        }
        return false;
    }

    @Deprecated
    private static boolean isHoleInFront() {
        Direction dir = AsunaMath.getCardinalDirection(AsunaMod.minecraft.player.rotationYaw);
        if (dir == Direction.posX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 1.2, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b == Blocks.AIR && bb.getDefaultState().isNormalCube();
        }
        if (dir == Direction.posZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ + 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ + 2.2)).getBlock();
            return b == Blocks.AIR && bb.getDefaultState().isNormalCube();
        }
        if (dir == Direction.negX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX - 1.2, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX - 2.2, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b == Blocks.AIR && bb.getDefaultState().isNormalCube();
        }
        if (dir == Direction.negZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ - 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY - 1, AsunaMod.minecraft.player.posZ - 2.2)).getBlock();
            return b == Blocks.AIR && bb.getDefaultState().isNormalCube();
        }
        return false;
    }

    @Deprecated
    private static boolean isObstacleThere() {
        Direction dir = AsunaMath.getCardinalDirection(AsunaMod.minecraft.player.rotationYaw);
        if (dir == Direction.posX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 1.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.posZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ + 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.negX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX - 1.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.negZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ - 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        return false;
    }

    @Deprecated
    private static boolean isObstacleThereManual(float yaw) {
        Direction dir = AsunaMath.getCardinalDirection(yaw);
        if (dir == Direction.posX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 1.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.posZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ + 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.negX) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX - 1.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        if (dir == Direction.negZ) {
            Block b = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ - 1.2)).getBlock();
            Block bb = AsunaMod.minecraft.world.getBlockState(new BlockPos(AsunaMod.minecraft.player.posX + 2.2, AsunaMod.minecraft.player.posY + 1, AsunaMod.minecraft.player.posZ)).getBlock();
            return b != Blocks.AIR || bb != Blocks.AIR;
        }
        return false;
    }

    @Override
    public void onTick() {
        this.setSuffix(this.getFormattableOptionsMap());
        AsunaMod.setPressed(AsunaMod.minecraft.gameSettings.keyBindForward, true);
        if (this.getFormattableOptionsMap().get("Pathfinder")) {
            timer++;
            if (timer >= 20) {
                if (isObstacleThere()) {
                    if (!isObstacleThereManual(AsunaMod.minecraft.player.rotationYaw + 90)) {
                        boolean wasToggled = false;
                        if (Manager.Feature.isFeatureEnabled(YawLockFeature.class)) {
                            Manager.Feature.findFeature(YawLockFeature.class).toggleState();
                            wasToggled = true;
                        }
                        float oldRot = AsunaMod.minecraft.player.rotationYaw;
                        AsunaMod.minecraft.player.rotationYaw += 90;
                        WalkThread wt = new WalkThread("AutoWalker", oldRot, wasToggled);
                        wt.start();
                        timer = 0;
                    } else if (!isObstacleThereManual(AsunaMod.minecraft.player.rotationYaw - 90)) {
                        boolean wasToggled = false;
                        if (Manager.Feature.isFeatureEnabled(YawLockFeature.class)) {
                            Manager.Feature.findFeature(YawLockFeature.class).toggleState();
                            wasToggled = true;
                        }
                        float oldRot = AsunaMod.minecraft.player.rotationYaw;
                        AsunaMod.minecraft.player.rotationYaw -= 90;
                        WalkThread wt = new WalkThread("AutoWalker", oldRot, wasToggled);
                        wt.start();
                        timer = 0;
                    } else {
                        AsunaMod.logWarn(false, "Can't evade obstacle, manual input needed!");
                    }
                }
                if (isBlockBlockingInFront()) {
                    AsunaMod.minecraft.player.jump();
                    timer = 0;
                }
                if (isHoleInFront()) {
                    AsunaMod.minecraft.player.jump();
                    timer = 0;
                }
            }
        }
    }
}

@Deprecated
class WalkThread implements Runnable {
    private Thread t;
    private String threadName;
    private float oldYaw;
    private boolean needsYaw;

    public WalkThread(String name, float oldYaw, boolean needsYaw) {
        threadName = name;
        System.out.println("Creating " + threadName);
        //this.args = args;
        this.oldYaw = oldYaw;
        this.needsYaw = needsYaw;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Minecraft.getMinecraft().player.rotationYaw = this.oldYaw;
        if (this.needsYaw) {
            Manager.Feature.findFeature(YawLockFeature.class).toggleState();
        }
    }

    public void start() {
        //System.out.println("Starting " +  threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
