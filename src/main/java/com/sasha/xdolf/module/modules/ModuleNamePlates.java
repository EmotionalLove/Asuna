package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

import static com.sasha.xdolf.misc.XdolfMath.dround;

public class ModuleNamePlates extends XdolfModule {
    public ModuleNamePlates() {
        super("NamePlates", XdolfCategory.RENDER, false);
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
