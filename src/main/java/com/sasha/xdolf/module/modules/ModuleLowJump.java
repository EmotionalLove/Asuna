package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.events.PlayerJumpEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

@ModuleInfo(description = "Jump slightly lower than vanilla height so that you don't hit your head")
public class ModuleLowJump extends XdolfModule implements SimpleListener {
    public ModuleLowJump() {
        super("LowJump", XdolfCategory.MOVEMENT, false);
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
