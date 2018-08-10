package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.eventsys.SimpleEvent;
import com.sasha.xdolf.misc.ModuleState;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 9:18 AM
 **/
public class XdolfModuleTogglePostEvent extends SimpleEvent {
    private XdolfModule toggledModule;
    private ModuleState toggleState;

    public XdolfModuleTogglePostEvent(XdolfModule toggledModule, ModuleState toggleState){
        this.toggledModule= toggledModule;
        this.toggleState= toggleState;
    }

    public ModuleState getToggleState() {
        return toggleState;
    }

    public XdolfModule getToggledModule() {
        return toggledModule;
    }
}

