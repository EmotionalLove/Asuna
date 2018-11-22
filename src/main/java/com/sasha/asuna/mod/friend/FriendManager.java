/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.friend;

import com.sasha.asuna.mod.AsunaMod;

import java.util.ArrayList;

/**
 * Created by Sasha on 08/08/2018 at 12:40 PM
 **/
public class FriendManager {

    private ArrayList<Friend> friendList = new ArrayList<>();

    public FriendManager() {
        //Manager.Data.registerSettingObject(this);
    }

    public void addFriend(String friendName) {
        friendList.add(new Friend(friendName));
        AsunaMod.SETTING_HANDLER.save(this);
    }

    public void removeFriend(String friendName) {
        Friend f1 = null;
        for (Friend f : friendList) {
            if (f.getFriendName().equals(friendName)) {
                f1 = f;
                break;
            }
        }
        if (f1 != null) {
            friendList.remove(f1);
            AsunaMod.SETTING_HANDLER.save(this);
        }
    }

    public boolean isFriended(String friendName) {
        for (Friend f : friendList) {
            if (f.getFriendName().equalsIgnoreCase(friendName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public ArrayList<String> getFriendListString() {
        ArrayList<String> friendStrs = new ArrayList<>();
        for (Friend friend : friendList) {
            friendStrs.add(friend.getFriendName());
        }
        return friendStrs;
    }



    /* these are outdated Asuna 3.x functions
    public static void saveFriends() throws IOException {
        File file = new File("Asuna_friendslist.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Friend friend : friendList) {
            writer.write(friend.getFriendName());
            writer.write("\r\n");
        }
        writer.close();
    }
    public static void loadFriends() throws IOException {
        File file = new File("Asuna_friendslist.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            addFriend(line, true);
        }
        reader.close();
    }*/
}