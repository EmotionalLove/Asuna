package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

import static com.sasha.adorufu.misc.AdorufuMath.dround;

@ModuleInfo(description = "Display an enchanced nametag above a player's head")
public class ModuleNamePlates extends AdorufuModule {
    public ModuleNamePlates() {
        super("NamePlates", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
    public static String formatHealthTag(double health) {
        if (health > 15.0) {
            return "\247" + "a" + (dround(health / 2, 3)) + " " + "\247" + "c<3";
        }
        else if (health > 10.0) {
            return "\247" + "e" + (dround(health / 2, 3)) + " " + "\247" + "c<3";
        }
        else if (health > 5.0) {
            return "\247" + "c" + (dround(health / 2, 3)) + " " + "\247" + "c<3";
        }
        else if (health > 0.0) {
            return "\247" + "4" + (dround(health / 2, 3)) + " " + "\247" + "c</3";
        }
        else {
            return "\247" + "4</3";
        }
    }
}
