package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.events.ClientEntityCollideEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "Don't collide with other entities")
public class ModuleNoPush extends AdorufuModule implements SimpleListener {
    public ModuleNoPush() {
        super("NoPush", AdorufuCategory.MOVEMENT, false);
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
    public void onEntityCollide(ClientEntityCollideEvent e) {
        if (this.isEnabled()) {
            e.setCancelled(true);
        }
    }
}
