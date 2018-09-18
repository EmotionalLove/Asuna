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

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Sasha at 5:57 PM on 9/17/2018
 */
public class AxisAlignedBBAdapter {

    public AxisAlignedBB setMaxY(double y2) {
        return axisAlignedBB.setMaxY(y2);
    }

    public AxisAlignedBB contract(double x, double y, double z) {
        return axisAlignedBB.contract(x, y, z);
    }

    public AxisAlignedBB expand(double x, double y, double z) {
        return axisAlignedBB.expand(x, y, z);
    }

    public AxisAlignedBB grow(double x, double y, double z) {
        return axisAlignedBB.grow(x, y, z);
    }

    public AxisAlignedBB grow(double value) {
        return axisAlignedBB.grow(value);
    }

    public AxisAlignedBB intersect(AxisAlignedBB other) {
        return axisAlignedBB.intersect(other);
    }

    public AxisAlignedBB union(AxisAlignedBB other) {
        return axisAlignedBB.union(other);
    }

    public AxisAlignedBB offset(double x, double y, double z) {
        return axisAlignedBB.offset(x, y, z);
    }

    public AxisAlignedBB offset(BlockPos pos) {
        return axisAlignedBB.offset(pos);
    }

    public AxisAlignedBB offset(Vec3d vec) {
        return axisAlignedBB.offset(vec);
    }

    public double calculateXOffset(AxisAlignedBB other, double offsetX) {
        return axisAlignedBB.calculateXOffset(other, offsetX);
    }

    public double calculateYOffset(AxisAlignedBB other, double offsetY) {
        return axisAlignedBB.calculateYOffset(other, offsetY);
    }

    public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
        return axisAlignedBB.calculateZOffset(other, offsetZ);
    }

    public boolean intersects(AxisAlignedBB other) {
        return axisAlignedBB.intersects(other);
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return axisAlignedBB.intersects(x1, y1, z1, x2, y2, z2);
    }

    @SideOnly(Side.CLIENT)
    public boolean intersects(Vec3d min, Vec3d max) {
        return axisAlignedBB.intersects(min, max);
    }

    public boolean contains(Vec3d vec) {
        return axisAlignedBB.contains(vec);
    }

    public double getAverageEdgeLength() {
        return axisAlignedBB.getAverageEdgeLength();
    }

    public AxisAlignedBB shrink(double value) {
        return axisAlignedBB.shrink(value);
    }

    @Nullable
    public RayTraceResult calculateIntercept(Vec3d vecA, Vec3d vecB) {
        return axisAlignedBB.calculateIntercept(vecA, vecB);
    }

    @VisibleForTesting
    public boolean intersectsWithYZ(Vec3d vec) {
        return axisAlignedBB.intersectsWithYZ(vec);
    }

    @VisibleForTesting
    public boolean intersectsWithXZ(Vec3d vec) {
        return axisAlignedBB.intersectsWithXZ(vec);
    }

    @VisibleForTesting
    public boolean intersectsWithXY(Vec3d vec) {
        return axisAlignedBB.intersectsWithXY(vec);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasNaN() {
        return axisAlignedBB.hasNaN();
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getCenter() {
        return axisAlignedBB.getCenter();
    }

    private final AxisAlignedBB axisAlignedBB;

    public AxisAlignedBBAdapter(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }
}
