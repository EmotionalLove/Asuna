package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.xdolf.module.XdolfModule;

/**
 * Created by Sasha on 08/08/2018 at 9:18 AM
 **/
public class XdolfModuleToggleEvent extends SimpleCancellableEvent {
    private XdolfModule toggledModule;
    private ModuleStatus toggleState;

    public XdolfModuleToggleEvent(XdolfModule toggledModule, ModuleStatus toggleState){
        this.toggledModule= toggledModule;
        this.toggleState= toggleState;
    }
}

