package com.sasha.xdolf.module;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.XdolfModuleTogglePostEvent;
import com.sasha.xdolf.misc.ModuleState;
import com.sasha.xdolf.events.XdolfModuleTogglePreEvent;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sasha on 08/08/2018 at 11:52 AM
 **/
public class ModuleManager implements SimpleListener {

    public static ArrayList<XdolfModule> moduleRegistry = new ArrayList<>();

    @SimpleEventHandler
    public void onPreToggle(XdolfModuleTogglePreEvent e){
        if (e.getToggledModule().hasForcefulAnnotation(e.getToggledModule().getClass()) && e.getToggleState() == ModuleState.DISABLE){
            e.setCancelled(true);
            return;
        }
        if (e.getToggleState() == ModuleState.ENABLE){
            e.getToggledModule().onEnable();
            return;
        }
        e.getToggledModule().onDisable();
    }

    @SimpleEventHandler
    public void onModPostToggle(XdolfModuleTogglePostEvent e){
        try {
            XdolfMod.DATA_MANAGER.saveModuleStates(true);
        } catch (IOException e1) {
            XdolfMod.logErr(false, "Couldn't save module state. " + e1.getMessage());
            e1.printStackTrace();
        }
        if (e.getToggleState() == ModuleState.ENABLE){
            //e.getToggledModule().onEnable();
            if (!e.getToggledModule().isRenderable()) {
                XdolfModule.displayList.add(e.getToggledModule());
                return;
            }
            return;
        }
        //e.getToggledModule().onDisable();
        if (!e.getToggledModule().isRenderable()){
            XdolfModule.displayList.remove(e.getToggledModule());
            return;
        }
    }

    /**
     * This will automatically register listeners to the Event Manager if they implement SimpleListener
     */
    public static void register(XdolfModule mod) {
        moduleRegistry.add(mod);
        if (mod.getClass().getInterfaces().length != 0) {
            if (mod.getClass().getInterfaces()[0] == SimpleListener.class)  {
                XdolfMod.logMsg(true, mod.getModuleName() + " is listening for events.");
                XdolfMod.EVENT_MANAGER.registerListener((SimpleListener) mod);
            }
        }
    }

    public static XdolfModule getModuleByName(String key) {
        for (XdolfModule m : moduleRegistry) {
            if (m.getModuleName().toLowerCase().equals(key.toLowerCase())) return m;
        }
        return null;
    }

    public static void tickModules(){
        long l = System.currentTimeMillis();
        moduleRegistry.forEach(XdolfModule::onTick);
        XdolfMod.PERFORMANCE_ANAL.recordNewNormalTime((int)(System.currentTimeMillis()-l));
    }
    public static void renderModules(){
        long l = System.currentTimeMillis();
        moduleRegistry.forEach(XdolfModule::onRender);
        XdolfMod.PERFORMANCE_ANAL.recordNewRenderTime((int)(System.currentTimeMillis()-l));
    }

}
