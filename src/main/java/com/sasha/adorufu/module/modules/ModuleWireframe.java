package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;

@ModuleInfo(description = "See through the world kinda")
public class ModuleWireframe extends AdorufuModule {
    public ModuleWireframe() {
        super("Wireframe", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onTick() {

    }
}
