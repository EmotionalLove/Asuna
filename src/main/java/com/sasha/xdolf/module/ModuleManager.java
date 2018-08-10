package com.sasha.xdolf.module;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientOverlayRenderEvent;
import com.sasha.xdolf.events.XdolfModuleTogglePostEvent;
import com.sasha.xdolf.gui.XdolfHUD;
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
    public void onModPostToggle(XdolfModuleTogglePostEvent e){
        try {
            XdolfMod.DATA_MANAGER.saveModuleStates();
        } catch (IOException e1) {
            XdolfMod.logErr(false, "Couldn't save module state. " + e1.getMessage());
            e1.printStackTrace();
        }
        if (e.getToggleState() == ModuleState.ENABLE){
            e.getToggledModule().onEnable();
            if (!e.getToggledModule().isRenderable()) {
                XdolfModule.displayList.add(e.getToggledModule());
                return;
            }
            XdolfHUD.resetHUD();
            return;
        }
        e.getToggledModule().onDisable();
        if (!e.getToggledModule().isRenderable()){
            XdolfModule.displayList.remove(e.getToggledModule());
            return;
        }
        XdolfHUD.resetHUD();
    }

    @SimpleEventHandler
    public void onOverlay(ClientOverlayRenderEvent e){
        XdolfHUD.renderScreen();
    }

    public static XdolfModule getModuleByName(String key) {
        for (XdolfModule m : moduleRegistry) {
            if (m.getModuleName().toLowerCase().equals(key.toLowerCase())) return m;
        }
        return null;
    }

    public static void tickModules(){
        moduleRegistry.forEach(XdolfModule::onTick);
    }

}
