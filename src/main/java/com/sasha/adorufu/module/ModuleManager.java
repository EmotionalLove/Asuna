package com.sasha.adorufu.module;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.AdorufuModuleTogglePostEvent;
import com.sasha.adorufu.misc.ModuleState;
import com.sasha.adorufu.events.AdorufuModuleTogglePreEvent;

import java.io.IOException;
import java.util.ArrayList;

import static com.sasha.adorufu.AdorufuMod.DATA_MANAGER;

/**
 * Created by Sasha on 08/08/2018 at 11:52 AM
 **/
public class ModuleManager implements SimpleListener {

    public static ArrayList<AdorufuModule> moduleRegistry = new ArrayList<>();

    @SimpleEventHandler
    public void onPreToggle(AdorufuModuleTogglePreEvent e){
        if (e.getToggledModule().hasForcefulAnnotation(e.getToggledModule().getClass()) && e.getToggleState() == ModuleState.DISABLE){
            e.setCancelled(true);
            return;
        }
        if (e.getToggleState() == ModuleState.ENABLE && !e.getToggledModule().isPostExec(e.getToggledModule().getClass())){
            e.getToggledModule().onEnable();
            return;
        }
        if (e.getToggledModule().isPostExec(e.getToggledModule().getClass())) return;
        e.getToggledModule().onDisable();
    }

    @SimpleEventHandler
    public void onModPostToggle(AdorufuModuleTogglePostEvent e){
        try {
            DATA_MANAGER.saveModuleStates(true);
        } catch (IOException e1) {
            AdorufuMod.logErr(false, "Couldn't save module state. " + e1.getMessage());
            e1.printStackTrace();
        }
        if (e.getToggleState() == ModuleState.ENABLE){
            if (e.getToggledModule().isPostExec(e.getToggledModule().getClass())) e.getToggledModule().onEnable();

            if (!e.getToggledModule().isRenderable()) {
                AdorufuModule.displayList.add(e.getToggledModule());
                return;
            }
            return;
        }
        if (e.getToggledModule().isPostExec(e.getToggledModule().getClass())) e.getToggledModule().onDisable();
        if (!e.getToggledModule().isRenderable()){
            AdorufuModule.displayList.remove(e.getToggledModule());
            return;
        }
    }

    /**
     * This will automatically register listeners to the Event Manager if they implement SimpleListener
     */
    public static void register(AdorufuModule mod) {
        moduleRegistry.add(mod);
        if (mod.getClass().getInterfaces().length != 0) {
            if (mod.getClass().getInterfaces()[0] == SimpleListener.class)  {
                AdorufuMod.logMsg(true, mod.getModuleName() + " is listening for events.");
                AdorufuMod.EVENT_MANAGER.registerListener((SimpleListener) mod);
            }
        }
    }

    public static void loadBinds() {
        ModuleManager.moduleRegistry.forEach(mod -> {
            try {
                if (DATA_MANAGER.getSavedModuleState(mod.getModuleName())) {
                    mod.forceState(ModuleState.ENABLE, false, true);
                }
                if (mod.hasForcefulAnnotation(mod.getClass())) {
                    mod.forceState(ModuleState.ENABLE, false, true);
                }
                mod.setKeyBind(DATA_MANAGER.getSavedKeybind(mod));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static AdorufuModule getModuleByName(String key) {
        for (AdorufuModule m : moduleRegistry) {
            if (m.getModuleName().toLowerCase().equals(key.toLowerCase())) return m;
        }
        return null;
    }

    public static void tickModules(){
        long l = System.currentTimeMillis();
        moduleRegistry.forEach(AdorufuModule::onTick);
        AdorufuMod.PERFORMANCE_ANAL.recordNewNormalTime((int)(System.currentTimeMillis()-l));
    }
    public static void renderModules(){
        long l = System.currentTimeMillis();
        moduleRegistry.forEach(AdorufuModule::onRender);
        AdorufuMod.PERFORMANCE_ANAL.recordNewRenderTime((int)(System.currentTimeMillis()-l));
    }

}
