package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;

@ModuleInfo(description = "See through the world kinda")
public class ModuleWireframe extends XdolfModule {
    public ModuleWireframe() {
        super("Wireframe", XdolfCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onTick() {

    }
}
