/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientMouseClickEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.asuna.mod.misc.Manager;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

@FeatureInfo(description = "Middle click a block to add it to xray")
public class MiddleClickBlockFeature extends AbstractAsunaTogglableFeature implements SimpleListener {
    public MiddleClickBlockFeature() {
        super("MiddleClickBlock", AsunaCategory.MISC);
    }

    private static void refreshXray() {
        if (Manager.Feature.isFeatureEnabled(XrayFeature.class)) {
            AsunaMod.minecraft.renderGlobal.loadRenderers();
        }
    }

    @SimpleEventHandler
    public void onMiddleClick(ClientMouseClickEvent.Middle e) {
        if (AsunaMod.minecraft.world.getBlockState(AsunaMod.minecraft.objectMouseOver.getBlockPos()).getBlock().material == Material.AIR) {
            return;
        }
        BlockPos en = AsunaMod.minecraft.objectMouseOver.getBlockPos();
        Block b = AsunaMod.minecraft.world.getBlockState(en).getBlock();
        if (!XrayFeature.isVisibleInXray(b)) {
            XrayFeature.xRayBlocks.add(Block.getIdFromBlock(b));
            AsunaMod.logMsg(false, b.getLocalizedName() + " added.");
            refreshXray();
            return;
        }
        XrayFeature.xRayBlocks.remove(Block.getIdFromBlock(b));
        refreshXray();
        AsunaMod.logMsg(false, b.getLocalizedName() + " removed.");
    }
}
