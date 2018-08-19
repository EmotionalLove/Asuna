package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;
import com.sasha.adorufu.AdorufuMod;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 11/08/2018 at 5:19 PM
 **/
public class PlayerBlockPlaceEvent extends SimpleEvent {

    private Block block;

    public PlayerBlockPlaceEvent(BlockPos pos) {
        block = AdorufuMod.minecraft.world.getBlockState(pos).getBlock();
    }

    public Block getBlock() {
        return block;
    }
}
