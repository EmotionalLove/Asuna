package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

/**
 * Created by Sasha at 7:33 PM on 8/28/2018
 */
public class ModuleInventoryMove extends AdorufuModule {
    public ModuleInventoryMove() {
        super("InventoryMove", AdorufuCategory.MISC, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        if (AdorufuMod.minecraft.currentScreen == null) {
            return;
        }
        AdorufuMod.minecraft.setIngameNotInFocus();
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) return;
        AdorufuMod.minecraft.setIngameFocus();
    }
}
