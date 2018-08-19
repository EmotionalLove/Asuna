package com.sasha.adorufu.events;

import com.sasha.eventsys.SimpleCancellableEvent;

public class ClientMouseClickEvent {
    public static class Middle extends SimpleCancellableEvent {
        public Middle(){}
    }
    public static class Right extends SimpleCancellableEvent {
        public Right(){}
    }
}
