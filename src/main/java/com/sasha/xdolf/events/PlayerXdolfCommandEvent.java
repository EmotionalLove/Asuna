package com.sasha.xdolf.events;

import com.sasha.eventsys.SimpleCancellableEvent;

/**
 * Created by Sasha on 06/08/2018 at 4:23 PM
 **/
public class PlayerXdolfCommandEvent extends SimpleCancellableEvent {

    private String msg;

    public PlayerXdolfCommandEvent(String msg){
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }
}
