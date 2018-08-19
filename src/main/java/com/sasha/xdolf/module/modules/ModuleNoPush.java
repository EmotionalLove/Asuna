package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.events.ClientEntityCollideEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

@ModuleInfo(description = "Don't collide with other entities")
public class ModuleNoPush extends XdolfModule implements SimpleListener {
    public ModuleNoPush() {
        super("NoPush", XdolfCategory.MOVEMENT, false);
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
