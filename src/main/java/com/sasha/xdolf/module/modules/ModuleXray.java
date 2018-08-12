package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.misc.ModuleState;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
@ModuleInfo(description = "Makes chosen blocks invisible so that you can find ores or other blocks.")
public class ModuleXray extends XdolfModule {
    public static ArrayList<Block> xrayBlocks = new ArrayList<>();
    private boolean wasNightVisionsOff = false;

    public ModuleXray() {
        super("XRay", XdolfCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        if (!ModuleManager.getModuleByName("NightVision").isEnabled()) {
            ModuleManager.getModuleByName("NightVision").forceState(ModuleState.ENABLE, true, true);
            wasNightVisionsOff = true;
        }
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (wasNightVisionsOff) {
            ModuleManager.getModuleByName("NightVision").forceState(ModuleState.DISABLE, true, true);
            wasNightVisionsOff = false;
        }
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onTick() {

    }

    public static class XrayManager {

        public static boolean isXray;

        public static boolean isXrayBlock(Block b) {
            return xrayBlocks.contains(b);
        }
    }
}
