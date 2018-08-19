package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.misc.ModuleState;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.block.Block;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
@ModuleInfo(description = "Makes chosen blocks invisible so that you can find ores or other blocks.")
public class ModuleXray extends AdorufuModule {
    public static ArrayList<Block> xrayBlocks = new ArrayList<>();
    private boolean wasNightVisionsOff = false;

    public ModuleXray() {
        super("XRay", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        if (!ModuleManager.getModuleByName("NightVision").isEnabled()) {
            ModuleManager.getModuleByName("NightVision").forceState(ModuleState.ENABLE, true, true);
            wasNightVisionsOff = true;
        }
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (wasNightVisionsOff) {
            ModuleManager.getModuleByName("NightVision").forceState(ModuleState.DISABLE, true, true);
            wasNightVisionsOff = false;
        }
        AdorufuMod.minecraft.renderGlobal.loadRenderers();
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
