package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.events.ClientPacketSendEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

/**
 * Created by Sasha at 11:00 AM on 8/28/2018
 */
public class ModuleBlink extends AdorufuModule implements SimpleListener {
    public ModuleBlink() {
        super("Blink", AdorufuCategory.COMBAT, false);
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
    public void onPacketTx(ClientPacketSendEvent e) {
        if (this.isEnabled()) e.setCancelled(true);
    }

}
