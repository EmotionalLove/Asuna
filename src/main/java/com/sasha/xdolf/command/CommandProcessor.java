package com.sasha.xdolf.command;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.events.PlayerXdolfCommandEvent;

import java.util.ArrayList;

/**
 * Created by Sasha on 08/08/2018 at 7:51 AM
 **/
public class CommandProcessor implements SimpleListener {

    public static ArrayList<XdolfCommand> commandRegistry = new ArrayList<>();

    @SimpleEventHandler
    public void onXdolfCommand(PlayerXdolfCommandEvent e) {
        commandRegistry.forEach(command -> {
            if (e.getMsg().startsWith(command.getCommandName(true))) {
                command.setArguments(e.getMsg());
                command.onCommand();
            }
        });

    }
}
