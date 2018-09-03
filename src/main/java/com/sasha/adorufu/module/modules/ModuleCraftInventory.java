package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.events.ServerPlayerInventoryCloseEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.inventory.ContainerPlayer;

/**
 * Created by Sasha at 12:44 PM on 9/2/2018
 */
public class ModuleCraftInventory extends AdorufuModule implements SimpleListener {
    public ModuleCraftInventory() {
        super("CraftInventory", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }


    @SimpleEventHandler
    public void onPckCloseInv(ServerPlayerInventoryCloseEvent e) {
        if (!this.isEnabled()) return;
        if (e.getContainer() instanceof ContainerPlayer) {
            e.setCancelled(true);
        }
    }
}
