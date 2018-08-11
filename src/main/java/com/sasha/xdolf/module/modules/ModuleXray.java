package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 11:39 AM
 **/
public class ModuleXray extends XdolfModule {
    public static ArrayList<Block> xrayBlocks = new ArrayList<>();

    public ModuleXray() {
        super("XRay", XdolfCategory.RENDER, false);
    }

    @Override
    public void onEnable() {
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.renderGlobal.loadRenderers();
    }

    public static class XrayManager {

        public static boolean isXray;

        public static boolean isXrayBlock(Block b) {
            return xrayBlocks.contains(b);
        }
    }
}
