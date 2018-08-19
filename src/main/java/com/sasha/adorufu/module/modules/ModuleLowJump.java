package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.events.PlayerJumpEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "Jump slightly lower than vanilla height so that you don't hit your head")
public class ModuleLowJump extends AdorufuModule implements SimpleListener {
    public ModuleLowJump() {
        super("LowJump", AdorufuCategory.MOVEMENT, false);
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
    public void onJump(PlayerJumpEvent e) {
        if (!this.isEnabled()) return;
        e.setJumpHeight(0.40f);
    }
}
