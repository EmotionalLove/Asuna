package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientMouseClickEvent;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

public class ModuleMiddleClickBlock extends XdolfModule {
    public ModuleMiddleClickBlock() {
        super("MiddleClickBlock", XdolfCategory.MISC, false);
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
    @SimpleEventHandler
    public void onMiddleClick(ClientMouseClickEvent.Middle e) {
        if (XdolfMod.minecraft.world.getBlockState(XdolfMod.minecraft.objectMouseOver.getBlockPos()).getBlock().blockMaterial == Material.AIR) {
            return;
        }
        BlockPos en = XdolfMod.minecraft.objectMouseOver.getBlockPos();
        Block b = XdolfMod.minecraft.world.getBlockState(en).getBlock();
        if (!ModuleXray.XrayManager.isXrayBlock(b)) {
            ModuleXray.xrayBlocks.add(b);
            XdolfMod.logMsg(false, b.getLocalizedName() + " added.");
            refreshXray();
            return;
        }
        ModuleXray.xrayBlocks.remove(b);
        refreshXray();
        XdolfMod.logMsg(false, b.getLocalizedName() + " removed.");
    }
    private static void refreshXray() {
        if (ModuleManager.getModuleByName("X-Ray").isEnabled()) {
            XdolfMod.minecraft.renderGlobal.loadRenderers();
        }
    }
}
