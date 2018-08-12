package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.XdolfModuleTogglePostEvent;
import com.sasha.xdolf.events.XdolfModuleTogglePreEvent;
import com.sasha.xdolf.gui.hud.XdolfHUD;
import com.sasha.xdolf.misc.ModuleState;

import java.util.ArrayList;

/**
 * Created by Sasha on 08/08/2018 at 8:30 AM
 * This class is based off of Xdolf 3.x's Module class, however this is redone to make use of the Event system.
 * Fun fact: Xdolf 3.x's Event system was absolutely horrible, it was easier to just _not use it_...
 **/
public abstract class XdolfModule {

    private String moduleName, moduleNameColoured;
    private XdolfCategory moduleCategory;
    private boolean isEnabled = false;
    private String suffix = "";
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
        this.isEnabled=false;
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

    public String getDescription(Class<?> clazz){
        ModuleInfo d = clazz.getAnnotation(ModuleInfo.class);
        if (d == null){
            return "No description provided.";
        }
        return d.description();
    }

    public String getModuleNameColoured() {
        return moduleNameColoured;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String s){
        this.suffix= " \2478[\2477" + s + "\2478]";
    }
    public void setSuffix(String[] s){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if(i==0){
                b.append(s[i]);
                continue;
            }
            b.append(", ").append(s[i]);
        }
        this.suffix= " \2478[\2477" + b.toString() + "\2478]";
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public void removeSuffix(){
        this.suffix = "";
    }

    /**
     * Gets whether its toggled or not
     * @return bool
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean hasForcefulAnnotation(Class<?> clazz){
        return clazz.getAnnotation(ForcefulEnable.class)!= null;
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
     * invokes an XdolfModuleTogglePreEvent
     */
    public void toggle(){
        XdolfModuleTogglePreEvent event = new XdolfModuleTogglePreEvent(this, (isEnabled ? ModuleState.DISABLE : ModuleState.ENABLE));
        XdolfMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()){
            XdolfMod.logWarn(true, "Module \""+this.getModuleName()+"\" toggle was cancelled!");
            return;
        }
        this.isEnabled = !this.isEnabled;
        XdolfModuleTogglePostEvent post = new XdolfModuleTogglePostEvent(this, (isEnabled ? ModuleState.ENABLE : ModuleState.DISABLE));
        XdolfMod.EVENT_MANAGER.invokeEvent(post);
    }

    /**
     * forces the module to become active or inactive
     * @param state enable or disable
     * @param executeOnStateMethod whether you want to execute onDisable() or onEnable()
     */
    public void forceState(ModuleState state, boolean executeOnStateMethod, boolean resetHud){
        if (state == ModuleState.ENABLE){
            this.isEnabled = true;
            if (executeOnStateMethod){
                this.onEnable();
            }
            if (!this.isRenderable()){
                XdolfModule.displayList.add(this);
                return;
            }
            if(!resetHud){
                return;
            }
            XdolfHUD.resetHUD();
        }else{
            this.isEnabled = false;
            if (executeOnStateMethod){
                this.onDisable();
            }
            if (!this.isRenderable()){
                XdolfModule.displayList.remove(this);
                return;
            }
            if(!resetHud){
                return;
            }
            XdolfHUD.resetHUD();
        }
    }



    public abstract void onEnable();
    public  abstract void onDisable();
    public void onRender(){} // called a lot more than 20x per second
    public abstract void onTick(); // callee 20x per second
    public void init() {} //used for renderables(?) todo this needs review.
}
