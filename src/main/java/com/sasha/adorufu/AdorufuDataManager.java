package com.sasha.adorufu;

import com.sasha.adorufu.friend.Friend;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.misc.YMLParser;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.waypoint.Waypoint;
import net.minecraft.block.Block;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sasha.adorufu.module.ModuleManager.moduleRegistry;

/**
 * Created by Sasha on 08/08/2018 at 12:47 PM
 **/
// i love making simple tasks overly complex :3:3:3:3
// maybe im just a complex kind of girl
public class AdorufuDataManager {
    private final Lock threadLock = new ReentrantLock();
    private final Lock waypointLock = new ReentrantLock();
    private final String dataFileName = "AdorufuData.yml";

    public synchronized void saveWaypoint(Waypoint waypoint) throws IOException {
        AdorufuMod.logMsg(true, "Saving waypoint " + waypoint.getName() + "...");
        waypointLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File("waypoints/" + waypoint.getName() + ".wypt");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream fstream = new FileOutputStream(f);
            ObjectOutputStream stream = new ObjectOutputStream(fstream);
            stream.writeObject(waypoint);
            stream.close();
            fstream.close();
        } finally {
            waypointLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized void saveIgnorelist(ArrayList<String> ignores) throws IOException {
        AdorufuMod.logMsg(true, "Saving ignorelist...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            parser.set("Adorufu.ignorelist", ignores);
            parser.save();
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized List<String> loadIgnorelist() throws IOException {
        AdorufuMod.logMsg(true, "Getting ignorelist...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            return parser.getStringList("Adorufu.ignorelist");
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    
    public synchronized boolean getDRPEnabled() throws IOException {
        AdorufuMod.logMsg(true, "Checking to see if DRP should be enabled.");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locked.");
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                return true; // assuming true
            }
            YMLParser parser = new YMLParser(f);
            if (!parser.exists("Adorufu.discordpresence.enabled")){
                parser.set("Adorufu.discordpresence.enabled", true);
                return true;
            }
            return parser.getBoolean("Adorufu.discordpresence.enabled");
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread unlocked.");
        }
    }

    public synchronized void saveXrayBlocks(ArrayList<Block> blocks) throws IOException {
        AdorufuMod.logMsg(true, "Saving Xray blocks...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        ArrayList<Integer> intBlocks = new ArrayList<>();
        blocks.forEach(b -> intBlocks.add(Block.getIdFromBlock(b))); // In 3.x I used the Localised name, but the localised name changes when you change the game's language
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            parser.set("Adorufu.xray.blocks", intBlocks);
            parser.save();
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized ArrayList<Block> getXrayBlocks() throws IOException {
        AdorufuMod.logMsg(true, "Saving Xray blocks...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        List<Integer> intBlocks = new ArrayList<>();
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?) Returning empty ArrayList...");
                return new ArrayList<>();
            }
            YMLParser parser = new YMLParser(f);
            if (!parser.exists("Adorufu.xray.blocks")){
                return new ArrayList<>();
            }
            ArrayList<Block> realBlocks=new ArrayList<>();
            intBlocks = parser.getIntegerList("Adorufu.xray.blocks");
            intBlocks.forEach(id -> realBlocks.add(Block.getBlockById(id)));
            return realBlocks;
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized int[] getSavedGuiPos(AdorufuWindow window){
        AdorufuMod.logMsg(true, "Loading \"" + window.getTitle()+"\"'s saved GUI position...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()){
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                return new int[]{0,0};
            }
            YMLParser parser = new YMLParser(f);
            if (!parser.exists("Adorufu.gui.clickgui."+window.getTitle()+".x") && (!parser.exists("Adorufu.gui.clickgui."+window.getTitle()+".y"))){
                parser.set("Adorufu.gui.clickgui."+window.getTitle()+".x", window.dragX);
                parser.set("Adorufu.gui.clickgui."+window.getTitle()+".y", window.dragY);
                parser.save();
                return new int[]{0,0};
            }
            int x = parser.getInt("Adorufu.gui.clickgui."+window.getTitle()+".x",0);
            int y = parser.getInt("Adorufu.gui.clickgui."+window.getTitle()+".y",0);
            return new int[]{x,y};
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveGuiPos(AdorufuWindow window) throws IOException{
        AdorufuMod.logMsg(true, "Saving \"" + window.getTitle()+"\"'s GUI position...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()){
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            parser.set("Adorufu.gui.clickgui."+window.getTitle()+".x",window.dragX);
            parser.set("Adorufu.gui.clickgui."+window.getTitle()+".y",window.dragY);
            parser.save();
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized ScreenCornerPos getHudPositionState(RenderableObject robj) throws IOException {
        AdorufuMod.logMsg(true, "Loading \"" + robj.getName() +"\"'s saved HUD position...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()){
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
                AdorufuMod.logMsg(true, "Creating new data file with default HUD poaitions.");
                YMLParser parser = new YMLParser(f);
                parser.set("Adorufu.gui.hud." + robj.getName().toLowerCase(), RenderableObject.getPosStr(robj.getDefaultPos()));
                parser.save();
                return robj.getDefaultPos();
            }
            YMLParser parser = new YMLParser(f);
            if (!parser.exists("Adorufu.gui.hud." + robj.getName().toLowerCase())){
                parser.set("Adorufu.gui.hud." + robj.getName().toLowerCase(), RenderableObject.getPosStr(robj.getDefaultPos()));
                parser.save();
                return robj.getDefaultPos();
            }
            return RenderableObject.getPosEnum(parser.getString("Adorufu.gui.hud." + robj.getName().toLowerCase()));
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized int getSavedKeybind(AdorufuModule module){
        AdorufuMod.logMsg(true, "Getting module keybinde...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                return 0;
            }
            YMLParser parser = new YMLParser(file);
            if (!parser.exists("Adorufu.modules."+module.getModuleName().toLowerCase()+".bind")){
                parser.set("Adorufu.modules."+module.getModuleName()+".bind", 0);
                parser.save();
                return 0;
            }
            return parser.getInt("Adorufu.modules."+module.getModuleName().toLowerCase()+".bind", 0);
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveModuleBind(AdorufuModule module) throws IOException {
        AdorufuMod.logMsg(true, "Saving module keybinde...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            parser.set("Adorufu.modules."+module.getModuleName().toLowerCase()+".bind", module.getKeyBind());
            parser.save();
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized void saveModuleStates(boolean fileExists) throws IOException {
        AdorufuMod.logMsg(true, "Updating module savestates...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            moduleRegistry.forEach(mod -> parser.set("Adorufu.modules." + mod.getModuleName().toLowerCase()+".state", fileExists && mod.isEnabled()));
            parser.save();
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized boolean getSavedModuleState(String modName) throws IOException {
        AdorufuMod.logMsg(true, "Getting module \"" + modName + "\"'s previously saved state...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                AdorufuMod.logErr(true, "Module states file doesn't exist (maybe this is the client's first run?)");
                saveModuleStates(false);
                return false;
            }
            YMLParser parser = new YMLParser(file);
            return parser.getBoolean("Adorufu.modules." + modName.toLowerCase() + ".state", false);
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveFriends(ArrayList<Friend> updatedFriends) throws IOException {
        try {
            AdorufuMod.logMsg(true, "Updating saved friends...");
            threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
            AdorufuMod.logWarn(true, "Thread locking engaged!");
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            ArrayList<String> strFriends = new ArrayList<>();
            updatedFriends.forEach(f -> strFriends.add(f.getFriendName()));
            if (strFriends.isEmpty()) {
                strFriends.add("");
            }
            parser.set("Adorufu.friends.friendlist", strFriends);//todo test
            parser.save();
        } finally {
            threadLock.unlock();
        }
    }
    public synchronized ArrayList<Friend> loadFriends() throws IOException {
        AdorufuMod.logMsg(true, "Loading saved friends...");
        threadLock.lock();
        AdorufuMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                return new ArrayList<>();
            }
            YMLParser parser = new YMLParser(file);
            List<String> list = parser.getStringList("Adorufu.friends.friendlist");
            ArrayList<Friend> fliet = new ArrayList<>();
            list.forEach(fs -> fliet.add(new Friend(fs)));
            return fliet; // it was supposed to say flist but i made a typo but idc, now its a filet :yum:
        } finally {
            threadLock.unlock();
            AdorufuMod.logWarn(true, "Thread locking disengaged!");
        }
    }

}
