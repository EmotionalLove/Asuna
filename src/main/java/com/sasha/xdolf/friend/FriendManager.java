package com.sasha.xdolf.friend;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.misc.YMLParser;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sasha on 08/08/2018 at 12:40 PM
 **/
public class FriendManager {

    private ArrayList<Friend> friendList = new ArrayList<>();

    public FriendManager() {

    }

    public void addFriend(String friendName) {
        friendList.add(new Friend(friendName));
        XdolfMod.scheduler.schedule(() -> {
            try {
                XdolfMod.DATA_MANAGER.saveFriends(friendList);
            } catch (IOException ee){
                ee.printStackTrace();
                XdolfMod.logErr(false, "Couldn't save the friend's list! (" + ee.getMessage() + ")");
            }
        }, 0, TimeUnit.NANOSECONDS);
    }
    public boolean removeFriend(String friendName) {
        Friend f1 = null;
        for (Friend f : friendList) {
            if (f.getFriendName().equals(friendName)) {
                f1 = f;
                break;
            }
        }
        if (f1 != null) {
            friendList.remove(f1);
            try {
                XdolfMod.DATA_MANAGER.saveFriends(friendList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
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



    /* these are outdated xdolf 3.x functions
    public static void saveFriends() throws IOException {
        File file = new File("XDOLF_friendslist.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Friend friend : friendList) {
            writer.write(friend.getFriendName());
            writer.write("\r\n");
        }
        writer.close();
    }
    public static void loadFriends() throws IOException {
        File file = new File("XDOLF_friendslist.txt");
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