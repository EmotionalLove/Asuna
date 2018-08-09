package com.sasha.xdolf;

import com.sasha.xdolf.friend.Friend;
import com.sasha.xdolf.misc.YMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sasha.xdolf.module.ModuleManager.moduleRegistry;

/**
 * Created by Sasha on 08/08/2018 at 12:47 PM
 **/
// i love making simple tasks overly complex :3:3:3:3
// maybe im just a complex kind of girl
public class XdolfDataManager {
    private final Lock threadLock = new ReentrantLock();
    private final String dataFileName = "XdolfData.yml";

    /**
     * @since Xdolf 3.x, the HUD could be configured by the user into any corner of the screen.
     * the elements of the hud would automatically move up or down to stay out of the potion HUD and the chat UI.
     * It allowed for a clean look that Window based HUD's (like in Root by 086) couldn't deliver.
     *
     * The old config system was very sloppy, unfortunately. It needs to be fully re-written. This is temporary.
     * @return the configuration hashmap.
     */
    @Deprecated // TODO Redo this - this is just a way to enable compatibility with tbe legacy HUD config system from 3.x
    public synchronized HashMap<String, String> getHudPositionStates() {
        XdolfMod.logMsg(true, "Loading HUD posstates...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        XdolfMod.logWarn(true, "Thread locking engaged!");
        String[] oof = {
        "watermark",
        "coordinates",
        "hacklist",
        "Horsestats",
        "Tickrate",
        "Framerate",
        "Saturation",
        "InventoryStats"
        };
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                XdolfMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                // load default values
                HashMap<String, String> map = new HashMap<>();
                map.put("HUD_" + oof[0], "LT");
                map.put("HUD_" + oof[1], "LB");
                map.put("HUD_" + oof[2], "RB");
                map.put("HUD_" + oof[3], "LT");
                map.put("HUD_" + oof[4], "LB");
                map.put("HUD_" + oof[5], "LB");
                map.put("HUD_" + oof[6], "RT");
                map.put("HUD_" + oof[7], "RT");
                //this is _so_ ghetto.
                return map;
            }
            YMLParser parser = new YMLParser(file);
            HashMap<String, String> map = new HashMap<>();
            for (String s : oof) {
                String pos = parser.getString("xdolf.hud." + s);
                if (pos == null || pos.equalsIgnoreCase("")) parser.set("xdolf.hud."+s, "LT");
                map.put("HUD_"+s, pos);
            }
            parser.save();
            return map;
        } finally {
            threadLock.unlock();
        }
    }

    public synchronized void saveModuleStates() throws IOException {
        XdolfMod.logMsg(true, "Updating module savestates...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        XdolfMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            moduleRegistry.forEach(mod -> parser.set("xdolf.modules." + mod.getModuleName(), mod.isEnabled()));
            parser.save();
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized boolean getSavedModuleState(String modName) throws IOException {
        XdolfMod.logMsg(true, "Getting module \"" + modName + "\"'s previously saved state...");
        threadLock.lock();
        XdolfMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                XdolfMod.logErr(true, "Module states file doesn't exist (maybe this is the client's first run?)");
                return false;
            }
            YMLParser parser = new YMLParser(file);
            return parser.getBoolean("xdolf.modules." + modName, false);
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveFriends(ArrayList<Friend> updatedFriends) throws IOException {
        try {
            XdolfMod.logMsg(true, "Updating saved friends...");
            threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
            XdolfMod.logWarn(true, "Thread locking engaged!");
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            ArrayList<String> strFriends = new ArrayList<>();
            updatedFriends.forEach(f -> strFriends.add(f.getFriendName()));
            parser.set("xdolf.friends.friendlist", strFriends);//todo test
        } finally {
            threadLock.unlock();
        }
    }
    public synchronized ArrayList<Friend> loadFriends() throws IOException {
        XdolfMod.logMsg(true, "Loading saved friends...");
        threadLock.lock();
        XdolfMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                XdolfMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                return new ArrayList<>();
            }
            YMLParser parser = new YMLParser(file);
            List<String> list = parser.getStringList("xdolf.friends.friendlist");
            ArrayList<Friend> fliet = new ArrayList<>();
            list.forEach(fs -> fliet.add(new Friend(fs)));
            return fliet; // it was supposed to say flist but i made a typo but idc, now its a filet :yum:
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }
}
