package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.eventsys.SimpleEvent;
import com.sasha.xdolf.XdolfMod;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 11/08/2018 at 5:19 PM
 **/
public class PlayerBlockBreakEvent extends SimpleCancellableEvent {

    private Block block;

    public PlayerBlockBreakEvent(BlockPos pos) {
        block = XdolfMod.minecraft.world.getBlockState(pos).getBlock();
    }

    public Block getBlock() {
        return block;
    }
}
