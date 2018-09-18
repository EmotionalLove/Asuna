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

package com.sasha.adorufu.api.adapter;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

/**
 * Created by Sasha at 6:00 PM on 9/17/2018
 */
public class BlockPosAdapter {

    public BlockPos add(double x, double y, double z) {
        return blockPos.add(x, y, z);
    }

    public BlockPos add(int x, int y, int z) {
        return blockPos.add(x, y, z);
    }

    public BlockPos add(Vec3i vec) {
        return blockPos.add(vec);
    }

    public BlockPos subtract(Vec3i vec) {
        return blockPos.subtract(vec);
    }

    public BlockPos up() {
        return blockPos.up();
    }

    public BlockPos up(int n) {
        return blockPos.up(n);
    }

    public BlockPos down() {
        return blockPos.down();
    }

    public BlockPos down(int n) {
        return blockPos.down(n);
    }

    public BlockPos north() {
        return blockPos.north();
    }

    public BlockPos north(int n) {
        return blockPos.north(n);
    }

    public BlockPos south() {
        return blockPos.south();
    }

    public BlockPos south(int n) {
        return blockPos.south(n);
    }

    public BlockPos west() {
        return blockPos.west();
    }

    public BlockPos west(int n) {
        return blockPos.west(n);
    }

    public BlockPos east() {
        return blockPos.east();
    }

    public BlockPos east(int n) {
        return blockPos.east(n);
    }

    public BlockPos offset(EnumFacing facing) {
        return blockPos.offset(facing);
    }

    public BlockPos offset(EnumFacing facing, int n) {
        return blockPos.offset(facing, n);
    }

    public BlockPos rotate(Rotation rotationIn) {
        return blockPos.rotate(rotationIn);
    }

    public BlockPos crossProduct(Vec3i vec) {
        return blockPos.crossProduct(vec);
    }

    public long toLong() {
        return blockPos.toLong();
    }

    public static BlockPos fromLong(long serialized) {
        return BlockPos.fromLong(serialized);
    }

    public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
        return BlockPos.getAllInBox(from, to);
    }

    public static Iterable<BlockPos> getAllInBox(int x1, int y1, int z1, int x2, int y2, int z2) {
        return BlockPos.getAllInBox(x1, y1, z1, x2, y2, z2);
    }

    public BlockPos toImmutable() {
        return blockPos.toImmutable();
    }

    public static Iterable<BlockPos.MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
        return BlockPos.getAllInBoxMutable(from, to);
    }

    public static Iterable<BlockPos.MutableBlockPos> getAllInBoxMutable(int x1, int y1, int z1, int x2, int y2, int z2) {
        return BlockPos.getAllInBoxMutable(x1, y1, z1, x2, y2, z2);
    }

    public int compareTo(Vec3i p_compareTo_1_) {
        return blockPos.compareTo(p_compareTo_1_);
    }

    public int getX() {
        return blockPos.getX();
    }

    public int getY() {
        return blockPos.getY();
    }

    public int getZ() {
        return blockPos.getZ();
    }

    public double getDistance(int xIn, int yIn, int zIn) {
        return blockPos.getDistance(xIn, yIn, zIn);
    }

    public double distanceSq(double toX, double toY, double toZ) {
        return blockPos.distanceSq(toX, toY, toZ);
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        return blockPos.distanceSqToCenter(xIn, yIn, zIn);
    }

    public double distanceSq(Vec3i to) {
        return blockPos.distanceSq(to);
    }

    private final BlockPos blockPos;

    public BlockPosAdapter(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

}
