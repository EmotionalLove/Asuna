package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ModuleStatus;
import com.sasha.xdolf.events.XdolfModuleToggleEvent;

import java.io.IOException;

/**
 * Created by Sasha on 08/08/2018 at 8:30 AM
 * This class is based off of Xdolf 3.x's Module class, however this is redone to make use of the Event system.
 * Fun fact: Xdolf 3.x's Event system was absolutely horrible, it was easier to just _not use it_...
 **/
public abstract class XdolfModule {

    private String moduleName, moduleNameColoured;
    private XdolfCategory moduleCategory;
    private boolean isEnabled = false;
    private String suffix;
    private String colour;
    private int keyBind;
    private boolean isRenderable = false;

    public XdolfModule(String moduleName, XdolfCategory moduleCategory, int keyBind){
        this.moduleName = moduleName;
        this.moduleCategory = moduleCategory;
        String c;
        if (moduleCategory == XdolfCategory.COMBAT) {
            c = "4";
        }
        else if (moduleCategory == XdolfCategory.CHAT) {
            c = "3";
        }
        else if (moduleCategory == XdolfCategory.GUI) {
            c = "7";
        }
        else if (moduleCategory == XdolfCategory.MISC) {
            c = "b";
        }
        else if (moduleCategory == XdolfCategory.MOVEMENT) {
            c = "6";
        }
        else if (moduleCategory == XdolfCategory.RENDER) {
            c = "d";
        }
        else {
            c = "8";
        }
        this.moduleNameColoured = "\247" + c + moduleName;
        this.keyBind = keyBind;
        try {
            this.isEnabled = ModuleUtils.getSavedModuleState(moduleName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void toggle(){
        XdolfModuleToggleEvent event = new XdolfModuleToggleEvent(this, (isEnabled ? ModuleStatus.DISABLE : ModuleStatus.ENABLE));
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()){
            XdolfMod.logMsg(true, "Module \""+this.getModuleName()+"\" toggle was cancelled!");
            return;
        }
        this.isEnabled = !this.isEnabled;
    }

    public XdolfCategory getModuleCategory() {
        return moduleCategory;
    }

    public void onEnable(){}
    public void onDisable(){}
    public void onRender(){} // called a lot more than 20x per second
    public void onTick(){} // callee 20x per second
}
