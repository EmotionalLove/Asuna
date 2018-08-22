package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.waypoint.Waypoint;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
@SimpleCommandInfo(description = "Add or remove waypoints", syntax = {"<'add'/'del'> <name>", "<'add'/'del'> <name> <x> <y> <z>"})
public class WaypointCommand extends SimpleCommand {
    public WaypointCommand() {
        super("waypoint");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length < 2){
            AdorufuMod.logErr(false, "Arguments required! Try \"-help command waypoint\"");
            return;
        }
        boolean manualMode = false;
        if (this.getArguments().length == 5) {
            manualMode = true;
        }
        switch (this.getArguments()[0].toLowerCase()) {
            case "add":
                if (AdorufuMod.WAYPOINT_MANAGER.sameName(this.getArguments()[1])) {
                    AdorufuMod.logErr(false, "There is already a waypoint with that name!");
                    return;
                }
                Waypoint daWaypoint = new Waypoint(manualMode ? Integer.parseInt(this.getArguments()[2]) : AdorufuMod.minecraft.player.getPosition().x,
                        manualMode ? Integer.parseInt(this.getArguments()[3]) : AdorufuMod.minecraft.player.getPosition().y,
                        manualMode ? Integer.parseInt(this.getArguments()[4]) : AdorufuMod.minecraft.player.getPosition().z,
                        true,
                        (AdorufuMod.minecraft.getCurrentServerData() == null) ? null : AdorufuMod.minecraft.getCurrentServerData().serverIP,
                        this.getArguments()[1]);
                AdorufuMod.WAYPOINT_MANAGER.addWaypoint(daWaypoint, true);
                AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully added");
                break;
            case "del":
                if (!AdorufuMod.WAYPOINT_MANAGER.sameName(this.getArguments()[1])) {
                    AdorufuMod.logErr(false, "There isn't a waypoint with that name!");
                    return;
                }
                AdorufuMod.WAYPOINT_MANAGER.delWaypoint(this.getArguments()[1]);
                AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully removed");
                break;
        }
    }
}
