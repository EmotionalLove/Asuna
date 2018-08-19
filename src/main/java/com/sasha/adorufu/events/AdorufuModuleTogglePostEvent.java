package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleEvent;
import com.sasha.adorufu.misc.ModuleState;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha on 08/08/2018 at 9:18 AM
 **/
public class AdorufuModuleTogglePostEvent extends SimpleEvent {
    private AdorufuModule toggledModule;
    private ModuleState toggleState;

    public AdorufuModuleTogglePostEvent(AdorufuModule toggledModule, ModuleState toggleState){
        this.toggledModule= toggledModule;
        this.toggleState= toggleState;
    }

    public ModuleState getToggleState() {
        return toggleState;
    }

    public AdorufuModule getToggledModule() {
        return toggledModule;
    }
}

