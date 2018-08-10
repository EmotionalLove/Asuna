package com.sasha.xdolf.gui;


import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.events.ClientOverlayRenderEvent;
import com.sasha.xdolf.events.XdolfModuleTogglePostEvent;
import com.sasha.xdolf.misc.XdolfMath;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;


public class XdolfHUD extends GuiScreen implements SimpleListener {

    public static int sHeight;
    public static int sWidth;

    public static ArrayList<RenderableObject> registeredHudElements = new ArrayList<>();

    // starts at x = 12
    private static ArrayList<RenderableObject> leftTop = new ArrayList<>();

    // starts at x = (sHeight - 15)
    // possible blockades: chatbox
    private static ArrayList<RenderableObject> leftBottom = new ArrayList<>();

    // starts at x = 2
    // possible blockades: potion effects
    private static ArrayList<RenderableObject> rightTop = new ArrayList<>();

    // starts at x = (sHeight - 15)
    // possible blockades: hack arraylist
    private static ArrayList<RenderableObject> rightBottom = new ArrayList<>();

    @SimpleEventHandler
    public void onOverlayRender(ClientOverlayRenderEvent e){
        renderScreen();
    }
    @SimpleEventHandler
    public void onPostToggle(XdolfModuleTogglePostEvent e){
        if (e.getToggledModule().isRenderable()) {
            resetHUD();
        }
    }

    /* todo optimise this */
    public static void setupHUD(){
        XdolfMod.logWarn(true, "The HUD is (re)setting up!");
        for (RenderableObject element : registeredHudElements) {
            for (XdolfModule moduleElement : ModuleManager.moduleRegistry) {
                //XdolfMod.logMsg(true, "Checking "+moduleElement.getModuleName().toLowerCase() +" & "
                //+ element.getName());
                if (element.getName().toLowerCase().equals(moduleElement.getModuleName().toLowerCase())){
                    //XdolfMod.logWarn(true, "Found matching module and renderable: " + element.getName().toLowerCase());
                    if (moduleElement.isEnabled() && moduleElement.isRenderable()){
                        //XdolfMod.logMsg(true,"Sorting renderable...");
                        ScreenCornerPos thePos=element.getPos();
                        if (element.getPos() == null){
                            thePos=element.getDefaultPos();
                        }
                        switch(thePos){
                            case LEFTTOP:
                                leftTop.add(element);
                                XdolfMod.logMsg(true,element.getName() + " LT");
                                break;
                            case LEFTBOTTOM:
                                leftBottom.add(element);
                                XdolfMod.logMsg(true,element.getName() + " LB");
                                break;
                            case RIGHTBOTTOM:
                                rightBottom.add(element);
                                XdolfMod.logMsg(true,element.getName() + " RB");
                                break;
                            case RIGHTTOP:
                                rightTop.add(element);
                                XdolfMod.logMsg(true,element.getName() + " RT");
                                break;
                        }
                        break;
                    }
                }
            }
        }
    }


    public static void renderScreen() {
        sHeight = new ScaledResolution(XdolfMod.minecraft).getScaledHeight();
        sWidth = new ScaledResolution(XdolfMod.minecraft).getScaledWidth();
        if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && XdolfMod.minecraft.world.isRemote && XdolfMod.minecraft.currentScreen == null) {
            return;
        }

        int lt_count = 0;
        for (RenderableObject o : leftTop) {
            o.renderTheObject((2) + (10 * lt_count));
            lt_count++;
        }
        int lb_count = 0;
        for (RenderableObject o : leftBottom) {
            if (XdolfMod.minecraft.currentScreen instanceof GuiChat) {
                o.renderTheObject(((sHeight - 15) - (10 * lb_count)) - 14);
                lb_count++;
                continue;
            }
            o.renderTheObject((sHeight - 15) - (10 * lb_count));
            lb_count++;
        }
        int rt_count = 0;
        for (RenderableObject o : rightTop) {
            o.renderTheObject(((2) + (10 * rt_count)) + XdolfMath.calculateFutureSyndromeFix());
            rt_count++;
        }
        int rb_count = 0;
        for (RenderableObject o : rightBottom) {
            if (XdolfMod.minecraft.currentScreen instanceof GuiChat) {
                o.renderTheObject(((sHeight - 15) - (10 * rb_count)) - 14);
                rb_count++;
                continue;
            }
            o.renderTheObject((sHeight - 15) - (10 * rb_count));
            rb_count++;
        }


        if (!XdolfModule.displayList.isEmpty()) {
            XdolfModule.displayList.sort((m, m1) -> Fonts.segoe_36.getStringWidth(m1.getModuleName() + m1.getSuffix()) - Fonts.segoe_36.getStringWidth(m.getModuleName() + m.getSuffix()));
        }
    }

    /*
    private static void renderServerResponding() {
        if (!XdolfMod.minecraft.world.isRemote) {
            return;
        }
        Fonts.segoe_36.drawCenteredString("\247" + "4The server is not responding! (" + ServerResponding.responding/20 + " sec)", sWidth / 2, (sHeight / 2) - 15, 0xffffff, true);
    }*/

    public static void resetHUD() {
        leftBottom.clear();
        leftTop.clear();
        rightBottom.clear();
        rightTop.clear();
        setupHUD();
    }
}
