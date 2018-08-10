package com.sasha.xdolf.gui;


import com.sasha.xdolf.XdolfMath;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.gui.renderableobjects.*;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sasha.xdolf.XdolfMath.dround;


public class XdolfHUD extends GuiScreen {

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


    /* todo optimise this */
    public static void setupHUD(){
        for (RenderableObject element : registeredHudElements) {
            for (XdolfModule moduleElement : ModuleManager.moduleRegistry) {
                if (element.getName().toLowerCase().endsWith(moduleElement.getModuleName().toLowerCase())){
                    if (moduleElement.isEnabled() && moduleElement.isRenderable()){
                        switch(element.getPos()){
                            case LEFTTOP:
                                leftTop.add(element);
                                break;
                            case LEFTBOTTOM:
                                leftBottom.add(element);
                                break;
                            case RIGHTBOTTOM:
                                rightBottom.add(element);
                                break;
                            case RIGHTTOP:
                                rightTop.add(element);
                                break;
                        }
                        return;
                    }
                    return;
                }
            }
        }
    }

    /*
    public static void setupHUD() {
        for (HashMap.Entry<String, String> e : XdolfMod.DATA_MANAGER.getHudPositionStates().entrySet()) {
            XdolfMod.logMsg(true, e.getKey() + " " + e.getValue());
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("watermark")) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableWatermark("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableWatermark("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableWatermark("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableWatermark("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("coordinates") && ModuleManager.getModuleByName("coordinates").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableCoordinates("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableCoordinates("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableCoordinates("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableCoordinates("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("hacklist") && ModuleManager.getModuleByName("Hacklist").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableHacklist("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableHacklist("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableHacklist("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableHacklist("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("Horsestats") && ModuleManager.getModuleByName("HorseStats").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableHorseStats("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableHorseStats("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableHorseStats("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableHorseStats("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("TPS") && ModuleManager.getModuleByName("TPS").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableTickrate("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableTickrate("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableTickrate("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableTickrate("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("FPS") && ModuleManager.getModuleByName("FPS").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableFramerate("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableFramerate("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableFramerate("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableFramerate("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("Saturation") && ModuleManager.getModuleByName("Saturation").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableSaturation("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableSaturation("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableSaturation("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableSaturation("RT"));
                    continue;
                }
            }
            if (e.getKey().replace("HUD_", "").equalsIgnoreCase("InventoryStats") && ModuleManager.getModuleByName("InventoryStats").isEnabled()) {
                if (e.getValue().equalsIgnoreCase("LT")) {
                    leftTop.add(new RenderableInventoryStats("LT"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("LB")) {
                    leftBottom.add(new RenderableInventoryStats("LB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RB")) {
                    rightBottom.add(new RenderableInventoryStats("RB"));
                    continue;
                }
                if (e.getValue().equalsIgnoreCase("RT")) {
                    rightTop.add(new RenderableInventoryStats("RT"));
                    continue;
                }
            }
            //XdolfMod.pushNotifyNoPrefix("\247" + "e[" + "\247" + "6Warning" + "\247" + "e] Out of date XDOLF_UserConfig.txt file, please delete and restart client", "\247" + "7");
        }
    }*/

    public static void renderScreen() {
        sHeight = new ScaledResolution(XdolfMod.mc).getScaledHeight();
        sWidth = new ScaledResolution(XdolfMod.mc).getScaledWidth();
        if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && XdolfMod.mc.world.isRemote && XdolfMod.mc.currentScreen == null) {
            return;
        }

        int lt_count = 0;
        for (RenderableObject o : leftTop) {
            o.renderTheObject((2) + (10 * lt_count));
            lt_count++;
        }
        int lb_count = 0;
        for (RenderableObject o : leftBottom) {
            if (XdolfMod.mc.currentScreen instanceof GuiChat) {
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
            if (XdolfMod.mc.currentScreen instanceof GuiChat) {
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
        if (!XdolfMod.mc.world.isRemote) {
            return;
        }
        Fonts.segoe_36.drawCenteredString("\247" + "4The server is not responding! (" + ServerResponding.responding/20 + " sec)", sWidth / 2, (sHeight / 2) - 15, 0xffffff, true);
    }*/

    private static void renderArrayList() {
        int count = 0;
        for (XdolfModule module : XdolfModule.displayList) {
            if (module.isEnabled() && module.getSuffix() != null && !module.getSuffix().contains("[")) {
                Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName()) - 2, (sHeight - 15) - (10 * count), 0xffffff);
                count++;
            }
            else if (module.isEnabled()) {
                Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured() + module.getSuffix(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName() + module.getSuffix()) - 2, (sHeight - 15) - (10 * count), 0xffffff);
                count++;
            }
        }
    }
    private static void renderCoords() {
        if (ModuleManager.getModuleByName("Coordinates").isEnabled()) {
            double x = dround(XdolfMod.mc.player.posX, 3);
            double y = dround(XdolfMod.mc.player.posY, 3);
            double z = dround(XdolfMod.mc.player.posZ, 3);
            if (XdolfMod.mc.player.dimension == 0 || XdolfMod.mc.player.dimension == 1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + x + " (" + dround(x/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")", 4, 12, 0xffffff);
            }
            if (XdolfMod.mc.player.dimension == -1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + x + " (" + dround(x*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")", 4, 12, 0xffffff);
            }
        }
    }


    public static void resetHUD() {
        leftBottom.clear();
        leftTop.clear();
        rightBottom.clear();
        rightTop.clear();
        setupHUD();
    }
}
