package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.misc.YMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sasha on 08/08/2018 at 9:17 AM
 **/
public abstract class ModuleUtils{

    private static Lock threadLock = new ReentrantLock();

    public static ArrayList<XdolfModule> moduleRegistry = new ArrayList<>();

    public static synchronized void saveModuleStates() throws IOException {
        XdolfMod.logMsg(true, "Updating module savestates...");
        threadLock.lock(); // Don't allow other threads to modify this file until this operation is done.
        XdolfMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File("Xdolf_ModuleStates.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            YMLParser parser = new YMLParser(file);
            moduleRegistry.forEach(mod -> {
                parser.set("modules." + mod.getModuleName(), mod.isEnabled());
            });
            parser.save();
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }
    public static synchronized boolean getSavedModuleState(String modName) throws IOException {
        XdolfMod.logMsg(true, "Getting module \"" + modName + "\"'s previously saved state...");
        threadLock.lock();
        XdolfMod.logWarn(true, "Thread locking engaged!");
        try {
            File file = new File("Xdolf_ModuleStates.yml");
            if (!file.exists()) {
                XdolfMod.logErr(true, "Module states file doesn't exist (maybe this is the client's first run?)");
                return false;
            }
            YMLParser parser = new YMLParser(file);
            return parser.getBoolean("modules." + modName, false);
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }


}
