package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;
import com.sasha.eventsys.SimpleEvent;

/**
 * Created by Sasha on 06/08/2018 at 4:23 PM
 **/
public class MixinInitEvent extends SimpleCancellableEvent {

    private String mixinConfig;
    private String envObf;

    public MixinInitEvent(String mixinConfig, String envObf){
        this.mixinConfig = mixinConfig;
        this.envObf = envObf;
    }

    public String getEnvObf() {
        return envObf;
    }

    public void setEnvObf(String envObf) {
        this.envObf = envObf;
    }

    public String getMixinConfig() {
        return mixinConfig;
    }

    public void setMixinConfig(String mixinConfig) {
        this.mixinConfig = mixinConfig;
    }
}
