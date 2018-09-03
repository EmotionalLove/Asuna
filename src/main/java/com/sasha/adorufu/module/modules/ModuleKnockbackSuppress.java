package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.events.PlayerKnockbackEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;

/**
 * Created by Sasha at 7:59 PM on 9/2/2018
 */
@ModuleInfo(description = "Reduce or completely ignore knockback")
public class ModuleKnockbackSuppress extends AdorufuModule implements SimpleListener {
    public ModuleKnockbackSuppress() {
        super("KnockbackSuppress", AdorufuCategory.COMBAT, false, true, true);
        this.addOption("Ignore", true);
        this.addOption("Reduce", false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        this.setSuffix(this.getModuleOptionsMap());
    }
    @SimpleEventHandler
    public void onPlayerKnockBack(PlayerKnockbackEvent e) {
        if (!this.isEnabled()) return;
        if (this.getOption("Ignore")) {
            e.setCancelled(true);
            return;
        }
        e.setMotionX(e.getMotionX()/3);
        e.setMotionY(e.getMotionY()/3);
        e.setMotionZ(e.getMotionZ()/3);
    }
}
