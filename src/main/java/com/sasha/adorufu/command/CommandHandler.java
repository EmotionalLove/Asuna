package com.sasha.adorufu.command;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.PlayerAdorufuCommandEvent;

import java.util.ArrayList;

import static com.sasha.adorufu.AdorufuMod.COMMAND_PROCESSOR;

/**
 * Created by Sasha on 08/08/2018 at 7:51 AM
 **/
public class CommandHandler implements SimpleListener {

    @SimpleEventHandler
    public void onAdorufuCommand(PlayerAdorufuCommandEvent e) {
        if (e.getMsg().startsWith(COMMAND_PROCESSOR.getCommandPrefix()))
        COMMAND_PROCESSOR.processCommand(e.getMsg());
    }
}
