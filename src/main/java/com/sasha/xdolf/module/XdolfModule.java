package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ModuleStatus;
import com.sasha.xdolf.events.XdolfModuleToggleEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

    public static ArrayList<XdolfModule> displayList = new ArrayList<>(); // used for the hud

    public XdolfModule(String moduleName, XdolfCategory moduleCategory, boolean isRenderable){
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
        this.isRenderable= isRenderable;
        XdolfMod.scheduler.schedule(() -> {//todo test
            try {
                this.isEnabled = XdolfMod.DATA_MANAGER.getSavedModuleState(moduleName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.SECONDS);
        if(this.isRenderable){
            this.init();
        }
    }

    ///getters

    /**
     * gets module name
     * @return String
     */
    public String getModuleName() {
        return moduleName;
    }

    public String getModuleNameColoured() {
        return moduleNameColoured;
    }

    public String getSuffix() {
        return suffix;
    }

    /**
     * Gets whether its toggled or not
     * @return bool
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Gets the module's category
     * @return XdolfCategory enum
     */
    public XdolfCategory getModuleCategory() {
        return moduleCategory;
    }

    /**
     * whether the module is used to toggle a HUD element
     * @return bool
     */
    public boolean isRenderable() {
        return isRenderable;
    }

    ///voids

    /**
     * toggles the module and runs all the needed disable/enable fhnctions
     * invokes an XdolfModuleToggleEvent
     */
    public void toggle(){
        XdolfModuleToggleEvent event = new XdolfModuleToggleEvent(this, (isEnabled ? ModuleStatus.DISABLE : ModuleStatus.ENABLE));
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()){
            XdolfMod.logWarn(true, "Module \""+this.getModuleName()+"\" toggle was cancelled!");
            return;
        }
        this.isEnabled = !this.isEnabled;
    }

    /**
     * forces the module to become active or inactive
     * @param state enable or disable
     * @param executeOnStateMethod whether you want to execute onDisable() or onEnable()
     */
    public void forceState(ModuleStatus state, boolean executeOnStateMethod){
        if (state == ModuleStatus.ENABLE){
            this.isEnabled = true;
            if (executeOnStateMethod){
                this.onEnable();
            }
            if (!this.isRenderable()){
                XdolfModule.displayList.add(this);
            }
        }else{
            this.isEnabled = false;
            if (executeOnStateMethod){
                this.onDisable();
            }
            if (!this.isRenderable()){
                XdolfModule.displayList.remove(this);
            }
        }
    }



    public void onEnable(){}
    public void onDisable(){}
    public void onRender(){} // called a lot more than 20x per second
    public void onTick(){} // callee 20x per second
    public void init() {} //used for renderables(?) todo this needs review.
}
