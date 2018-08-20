package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.AdorufuCommand;
import com.sasha.adorufu.command.CommandInfo;

import static com.sasha.adorufu.AdorufuMod.logMsg;
import static com.sasha.adorufu.module.modules.ModuleClientIgnore.ignorelist;

/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@CommandInfo(description = "View your friends", syntax = {"", "[player]"})
public class FriendlistCommand extends AdorufuCommand {
    public FriendlistCommand() {
        super("friendlist");
    }

    public void onCommand(){
        if (this.getArguments() == null) {
            StringBuilder builder = new StringBuilder();
            logMsg(false, "Listing \247l" + ignorelist.size() + " \247r\2477friended players:");
            for (int i = 0; i < AdorufuMod.FRIEND_MANAGER.getFriendList().size(); i++) {
                if (i == 0) {
                    builder.append(AdorufuMod.FRIEND_MANAGER.getFriendList().get(i));
                    continue;
                }
                builder.append(", ").append(AdorufuMod.FRIEND_MANAGER.getFriendList().get(i));
            }
            logMsg(builder.toString());
            return;
        }
        String player = this.getArguments()[0];
        logMsg(false,
                player + " is" + (AdorufuMod.FRIEND_MANAGER.getFriendList().contains(player) ? "" : " not") + " friended.");
    }
}
