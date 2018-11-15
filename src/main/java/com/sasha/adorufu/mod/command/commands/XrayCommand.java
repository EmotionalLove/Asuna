/*
 * Copyright (c) Sasha Stevens 2018.
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

package com.sasha.adorufu.mod.command.commands;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.impl.XrayFeature;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;
import net.minecraft.block.Block;

/**
 * Created by Sasha on 11/08/2018 at 1:18 PM
 **/
@SimpleCommandInfo(description = "Add, remote, or list blocks added to xray", syntax = {"<'add'/'del'> <block>", "<'list'>"})
public class XrayCommand extends SimpleCommand {
    public XrayCommand() {
        super("xray");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null) {
            AdorufuMod.logErr(false, "Arguments required! Try \"-help command xray\"");
            return;
        }
        if (this.getArguments().length == 1 && this.getArguments()[0].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < XrayFeature.xRayBlocks.size(); i++) {
                if (i == 0) {
                    builder.append(Block.getBlockById(XrayFeature.xRayBlocks.get(i)).getLocalizedName());
                    continue;
                }
                builder.append(", ").append(Block.getBlockById(XrayFeature.xRayBlocks.get(i)).getLocalizedName());
            }
            AdorufuMod.logMsg(false, "Listing registered blocks:");
            AdorufuMod.logMsg(builder.toString());
            return;
        }
        if (this.getArguments().length == 2) {
            switch (this.getArguments()[0].toLowerCase()) {
                case "add":
                    Block b = Block.getBlockFromName(this.getArguments()[1]);
                    if (b == null) {
                        AdorufuMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (XrayFeature.xRayBlocks.contains(Block.getIdFromBlock(b))) {
                        AdorufuMod.logErr(false, "That block is already added to xray!");
                        break;
                    }
                    XrayFeature.xRayBlocks.add(Block.getIdFromBlock(b));
                    AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully added");
                    if (Manager.Feature.isFeatureEnabled(XrayFeature.class)) AdorufuMod.minecraft.renderGlobal.loadRenderers();
                    break;
                case "del":
                    Block delb = Block.getBlockFromName(this.getArguments()[1]);
                    if (delb == null) {
                        AdorufuMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (!XrayFeature.xRayBlocks.contains(Block.getIdFromBlock(delb))) {
                        AdorufuMod.logErr(false, "That block is not added to xray!");
                        break;
                    }
                    XrayFeature.xRayBlocks.remove(Block.getIdFromBlock(delb));
                    AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully removed");
                    if (Manager.Feature.isFeatureEnabled(XrayFeature.class))AdorufuMod.minecraft.renderGlobal.loadRenderers();
                    break;
            }
            return;
        }
        AdorufuMod.logErr(false, "Invalid arguments! Try \"-help command xray\"");
    }
}
