package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.eventsys.SimpleEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.xdolf.XdolfMod;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 11/08/2018 at 5:19 PM
 **/
public class PlayerBlockPlaceEvent extends SimpleEvent {

    private Block block;

    public PlayerBlockPlaceEvent(BlockPos pos) {
        block = XdolfMod.minecraft.world.getBlockState(pos).getBlock();
    }

    public Block getBlock() {
        return block;
    }
}
