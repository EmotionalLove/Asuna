package com.sasha.xdolf.friend;

/**
 * Created by Sasha on 08/08/2018 at 12:34 PM
 **/
public class Friend {
    private String friendName;

    public Friend(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return this.friendName;
    }

    @Deprecated
    public void setFriendName(String friendName) {
        this.friendName = friendName;
        // why would you wanna do this anyways?
    }
}
