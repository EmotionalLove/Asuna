package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.simplecmdsys.SimpleCommand;

import static com.sasha.adorufu.AdorufuMod.logMsg;
import com.sasha.simplecmdsys.SimpleCommandInfo;
/**
 * Created by Sasha on 09/08/2018 at 3:28 PM
 **/
@SimpleCommandInfo(description = "View your friends", syntax = {"", "[player]"})
public class FriendlistCommand extends SimpleCommand {
    public FriendlistCommand() {
        super("friendlist");
    }

    public void onCommand(){
        if (this.getArguments() == null) {
            StringBuilder builder = new StringBuilder();
            logMsg(false, "Listing \247l" + AdorufuMod.FRIEND_MANAGER.getFriendList().size() + " \247r\2477friended players:");
            for (int i = 0; i < AdorufuMod.FRIEND_MANAGER.getFriendList().size(); i++) {
                if (i == 0) {
                    builder.append(AdorufuMod.FRIEND_MANAGER.getFriendList().get(i).getFriendName());
                    continue;
                }
                builder.append(", ").append(AdorufuMod.FRIEND_MANAGER.getFriendList().get(i).getFriendName());
            }
            logMsg(builder.toString());
            return;
        }
        String player = this.getArguments()[0];
        logMsg(false,
                player + " is" + (AdorufuMod.FRIEND_MANAGER.isFriended(player) ? "" : " not") + " friended.");
    }
}
