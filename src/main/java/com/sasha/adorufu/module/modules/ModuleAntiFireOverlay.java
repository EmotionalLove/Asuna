package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.events.ClientRenderFireOverlayEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

public class ModuleAntiFireOverlay extends AdorufuModule implements SimpleListener {
    public ModuleAntiFireOverlay() {
        super("AntiFireOverlay", AdorufuCategory.RENDER, false);
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
    public void onFireRender(ClientRenderFireOverlayEvent e) {
        if (this.isEnabled()) e.setCancelled(true);
    }
}
