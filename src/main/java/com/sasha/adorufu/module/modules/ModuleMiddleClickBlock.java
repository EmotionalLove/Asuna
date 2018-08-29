package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientMouseClickEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(description = "Middle click a block to add it to xray")
public class ModuleMiddleClickBlock extends AdorufuModule implements SimpleListener {
    public ModuleMiddleClickBlock() {
        super("MiddleClickBlock", AdorufuCategory.MISC, false);
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
        if (AdorufuMod.minecraft.world.getBlockState(AdorufuMod.minecraft.objectMouseOver.getBlockPos()).getBlock().blockMaterial == Material.AIR) {
            return;
        }
        BlockPos en = AdorufuMod.minecraft.objectMouseOver.getBlockPos();
        Block b = AdorufuMod.minecraft.world.getBlockState(en).getBlock();
        if (!ModuleXray.XrayManager.isXrayBlock(b)) {
            ModuleXray.xrayBlocks.add(b);
            AdorufuMod.logMsg(false, b.getLocalizedName() + " added.");
            refreshXray();
            return;
        }
        ModuleXray.xrayBlocks.remove(b);
        refreshXray();
        AdorufuMod.logMsg(false, b.getLocalizedName() + " removed.");
    }
    private static void refreshXray() {
        if (ModuleManager.getModule("X-Ray").isEnabled()) {
            AdorufuMod.minecraft.renderGlobal.loadRenderers();
        }
    }
}
