package com.sasha.adorufu;

import com.sasha.adorufu.exception.AdorufuNoSuchElementInDataFileException;
import com.sasha.adorufu.friend.Friend;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.misc.PlayerIdentity;
import com.sasha.adorufu.misc.YMLParser;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.waypoint.Waypoint;
import net.minecraft.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sasha.adorufu.AdorufuMod.logMsg;
import static com.sasha.adorufu.AdorufuMod.logWarn;
import static com.sasha.adorufu.module.ModuleManager.moduleRegistry;

/**
 * Created by Sasha on 08/08/2018 at 12:47 PM
 **/
// i love making simple tasks overly complex :3:3:3:3
// maybe im just a complex kind of girl
public class AdorufuDataManager {
    private final Lock threadLock = new ReentrantLock();
    private final Lock waypointLock = new ReentrantLock();
    private final Lock identityLock = new ReentrantLock();
    private final String dataFileName = "AdorufuData.yml";

    public LinkedHashMap<String, PlayerIdentity> identityCacheMap = new LinkedHashMap<>();

    /**
     * Used for saving a float,double,string,int,long... anything like that, really.
     * @param path the path in the config to save it to. (recommended is Adorufu.something)
     * @param varName the name of the variable in the cfg file
     * @param obj the thing to save.
     */
    public synchronized void saveSomeGenericValue(String path, String varName, Object obj) throws IOException {
        threadLock.lock();
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            parser.set(path + "." + varName, obj);
            parser.save();
        } finally {
            threadLock.unlock();
        }
    }

    /**
     * You'll need to cast the final value to what you saved it as.
     */
    public synchronized Object loadSomeGenericValue(String path, String varName, Object defaultVal) throws IOException {
        threadLock.lock();
        try {
            File file = new File(dataFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            if (!parser.exists(path + "." + varName)) {
                if (defaultVal != null) return defaultVal;
                throw new AdorufuNoSuchElementInDataFileException("\"" + path + "\" does not exist in " + dataFileName);
            }
            if (defaultVal != null) return parser.get(path + "." + varName, defaultVal);
            return parser.get(path + "." + varName);
        } finally {
            threadLock.unlock();
        }
    }

    public synchronized void savePlayerIdentity(PlayerIdentity id, boolean delete) throws IOException {
        logMsg(true, "Saving identity " + id.getStringUuid() + "...");
        identityLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File dir = new File("playeridentitycache");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File f = new File("playeridentitycache/" + id.getStringUuid() + ".mcid");
            if (f.exists() || delete) {
                f.delete();
                if (delete) return;
            }
            FileOutputStream fstream = new FileOutputStream(f);
            ObjectOutputStream stream = new ObjectOutputStream(fstream);
            stream.writeObject(id);
            stream.close();
            fstream.close();
        } finally {
            identityLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public PlayerIdentity getPlayerIdentity(String UUID) {
        if (identityCacheMap.containsKey(UUID)) {
            return identityCacheMap.get(UUID);
        }
        return new PlayerIdentity(UUID);
    }

    public synchronized void saveWaypoint(Waypoint waypoint, boolean delete) throws IOException {
        logMsg(true, "Saving waypoint " + waypoint.getName() + "...");
        waypointLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File dir = new File("waypoints");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File f = new File("waypoints/" + waypoint.getName() + ".wypt");
            if (f.exists() || delete) {
                f.delete();
                if (delete) return;
            }
            FileOutputStream fstream = new FileOutputStream(f);
            ObjectOutputStream stream = new ObjectOutputStream(fstream);
            stream.writeObject(waypoint);
            stream.close();
            fstream.close();
        } finally {
            waypointLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void loadPlayerIdentities() throws IOException {
        logMsg(true, "Loading id's...");
        identityLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File f = new File("playeridentitycache");
            if (!f.exists()) {
                logMsg(true, "No id's to load, skipping.");
                return; // nothing to load :p
            }
            if (!f.isDirectory()) {
                f.delete();
                return; // nothing to load :p
            }
            List<File> files = Arrays.asList(f.listFiles());
            files.stream().filter(file -> file.getName().endsWith(".mcid")).forEach(wyptFile -> {
                try {
                    FileInputStream inputStream = new FileInputStream(wyptFile);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Object wayptObj = objectInputStream.readObject();
                    if (wayptObj instanceof PlayerIdentity) {
                        identityCacheMap.put(((PlayerIdentity) wayptObj).getStringUuid(),(PlayerIdentity)wayptObj);
                        objectInputStream.close();
                        inputStream.close();
                        return;
                    }
                    objectInputStream.close();
                    inputStream.close();
                    logWarn(true, wyptFile.getName() + " was not a id, skipping.");
                }catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace(); //dont rly care
                    return;
                }
            });
        } finally {
            identityLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized ArrayList<Waypoint> loadWaypoints() throws IOException {
        logMsg(true, "Loading waypoints...");
        waypointLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File f = new File("waypoints");
            if (!f.exists()) {
                logMsg(true, "No waypoints to load, skipping.");
                return new ArrayList<>(); // nothing to load :p
            }
            if (!f.isDirectory()) {
                f.delete();
                return new ArrayList<>(); // nothing to load :p
            }
            ArrayList<Waypoint> theWaypoints = new ArrayList<>();
            List<File> files = Arrays.asList(f.listFiles());
            files.stream().filter(file -> file.getName().endsWith(".wypt")).forEach(wyptFile -> {
                try {
                    FileInputStream inputStream = new FileInputStream(wyptFile);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Object wayptObj = objectInputStream.readObject();
                    if (wayptObj instanceof Waypoint) {
                        theWaypoints.add((Waypoint)wayptObj);
                        objectInputStream.close();
                        inputStream.close();
                        return;
                    }
                    objectInputStream.close();
                    inputStream.close();
                    logWarn(true, wyptFile.getName() + " was not a waypoint, skipping.");
                }catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace(); //dont rly care
                    return;
                }
            });
            return theWaypoints;
        } finally {
            waypointLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized void saveIgnorelist(List<String> ignores) throws IOException {
        logMsg(true, "Saving ignorelist...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveGreeterMsgs(ArrayList<List<String>> greets) throws IOException {
        logMsg(true, "Saving greeter messages...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            parser.set("Adorufu.greeter.join", greets.get(0));
            parser.set("Adorufu.greeter.leave", greets.get(1));
            parser.save();
        } finally {
            threadLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized List<String> loadIgnorelist() throws IOException {
        logMsg(true, "Getting ignorelist...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized ArrayList<List<String>> loadGreets() throws IOException {
        logMsg(true, "Getting greets...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()) {
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
            }
            YMLParser parser = new YMLParser(f);
            List<String> joins =  parser.getStringList("Adorufu.greeter.join");
            List<String> leaves =  parser.getStringList("Adorufu.greeter.leave");
            ArrayList<List<String>> theReturn = new ArrayList<>();
            theReturn.add(joins);
            theReturn.add(leaves);
            return theReturn;
        } finally {
            threadLock.unlock();
            logWarn(true, "Thread locking disengaged!");
        }
    }
    
    public synchronized boolean getDRPEnabled() throws IOException {
        logMsg(true, "Checking to see if DRP should be enabled.");
        threadLock.lock();
        logWarn(true, "Thread locked.");
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
            logWarn(true, "Thread unlocked.");
        }
    }

    public synchronized void saveXrayBlocks(ArrayList<Block> blocks) throws IOException {
        logMsg(true, "Saving Xray blocks...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized ArrayList<Block> getXrayBlocks() throws IOException {
        logMsg(true, "Saving Xray blocks...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized int[] getSavedGuiPos(AdorufuWindow window){
        logMsg(true, "Loading \"" + window.getTitle()+"\"'s saved GUI position...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveGuiPos(AdorufuWindow window) throws IOException{
        logMsg(true, "Saving \"" + window.getTitle()+"\"'s GUI position...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized ScreenCornerPos getHudPositionState(RenderableObject robj) throws IOException {
        logMsg(true, "Loading \"" + robj.getName() +"\"'s saved HUD position...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
        try {
            File f = new File(dataFileName);
            if (!f.exists()){
                AdorufuMod.logErr(true, "Data file doesn't exist (maybe this is the client's first run?)");
                f.createNewFile();
                logMsg(true, "Creating new data file with default HUD poaitions.");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized int getSavedKeybind(AdorufuModule module){
        logMsg(true, "Getting module keybinde...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveModuleBind(AdorufuModule module) throws IOException {
        logMsg(true, "Saving module keybinde...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }

    public synchronized void saveModuleStates(boolean fileExists) throws IOException {
        logMsg(true, "Updating module savestates...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized boolean getSavedModuleState(String modName) throws IOException {
        logMsg(true, "Getting module \"" + modName + "\"'s previously saved state...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }
    public synchronized void saveFriends(ArrayList<Friend> updatedFriends) throws IOException {
        try {
            logMsg(true, "Updating saved friends...");
            threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
            logWarn(true, "Thread locking engaged!");
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
        logMsg(true, "Loading saved friends...");
        threadLock.lock();
        logWarn(true, "Thread locking engaged!");
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
            logWarn(true, "Thread locking disengaged!");
        }
    }

}
