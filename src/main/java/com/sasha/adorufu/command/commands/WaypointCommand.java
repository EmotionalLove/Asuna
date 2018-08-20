package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.AdorufuCommand;
import com.sasha.adorufu.command.CommandInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@CommandInfo(description = "Add or remove waypoints", syntax = {"<'add'/'del'> <name>", "<'add'/'del'> <name> <x> <y> <z>"})
public class WaypointCommand extends AdorufuCommand {
    public WaypointCommand() {
        super("waypoint");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length != 2){
            AdorufuMod.logErr(false, "Arguments required! Try \"-help command waypoint\"");
            return;
        }
        switch (this.getArguments()[0].toLowerCase()) {
            case "add":
                if (AdorufuMod.FRIEND_MANAGER.isFriended(this.getArguments()[1])) {
                    AdorufuMod.logErr(false, "That person is already friended!");
                    return;
                }
                AdorufuMod.FRIEND_MANAGER.addFriend(this.getArguments()[1]);
                AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully added");
                AdorufuMod.scheduler.schedule(() -> {
                    try { AdorufuMod.DATA_MANAGER.saveFriends(AdorufuMod.FRIEND_MANAGER.getFriendList()); } catch (IOException e) { e.printStackTrace(); }
                }, 0, TimeUnit.NANOSECONDS);
                break;
            case "del":
                if (!AdorufuMod.FRIEND_MANAGER.isFriended(this.getArguments()[1])) {
                    AdorufuMod.logErr(false, "That person isn't friended!");
                    return;
                }
                AdorufuMod.FRIEND_MANAGER.removeFriend(this.getArguments()[1]);
                AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully removed");
                AdorufuMod.scheduler.schedule(() -> {
                    try { AdorufuMod.DATA_MANAGER.saveFriends(AdorufuMod.FRIEND_MANAGER.getFriendList()); } catch (IOException e) { e.printStackTrace(); }
                }, 0, TimeUnit.NANOSECONDS);
                break;
        }
    }
}
