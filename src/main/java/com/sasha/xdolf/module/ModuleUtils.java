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
public abstract class ModuleUtils {

    private static Lock threadLock = new ReentrantLock();

    public static ArrayList<XdolfModule> moduleRegistry = new ArrayList<>();

    public static void saveModuleStates() throws IOException {
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
        } finally {
            threadLock.unlock();
            XdolfMod.logWarn(true, "Thread locking disengaged!");
        }
    }


}
