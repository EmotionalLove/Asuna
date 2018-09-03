package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;
import net.minecraft.item.ItemStack;

/**
 * Created by Sasha on 11/08/2018 at 5:19 PM
 **/
public class PlayerBlockPlaceEvent extends SimpleEvent {

    private ItemStack block;

    public PlayerBlockPlaceEvent(ItemStack pos) {
        block = pos;
    }

    public ItemStack getBlock() {
        return block;
    }
}
