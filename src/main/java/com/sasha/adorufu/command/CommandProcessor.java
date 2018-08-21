package com.sasha.adorufu.command;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.PlayerAdorufuCommandEvent;

import java.util.ArrayList;

/**
 * Created by Sasha on 08/08/2018 at 7:51 AM
 **/
public class CommandProcessor implements SimpleListener {

    public static ArrayList<AdorufuCommand> commandRegistry = new ArrayList<>();

    @SimpleEventHandler
    public void onAdorufuCommand(PlayerAdorufuCommandEvent e) {
        commandRegistry.forEach(command -> {
            if (e.getMsg().split(" ")[0].equalsIgnoreCase(command.getCommandName(true))) {
                command.setArguments(e.getMsg());
                try {
                    command.onCommand();
                }catch(Exception ee) {
                    AdorufuMod.logErr(false, "Oops! An internal exception occurred while executing the requested command. \2477" + ee.getMessage());
                }
            }
        });

    }
}
