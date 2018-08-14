package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 12/08/2018 at 2:58 PM
 **/
public class CollisionBoxEvent extends SimpleEvent {
    private Block block;
    private BlockPos pos;
    private AxisAlignedBB aabb;

    public CollisionBoxEvent(Block block, BlockPos pos, AxisAlignedBB aabb) {
        this.block = block;
        this.pos = pos;
        this.aabb = aabb;
    }
    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public AxisAlignedBB getAabb() {
        return aabb;
    }

    public void setAabb(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }
}
