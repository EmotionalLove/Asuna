package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.module.modules.ModuleXray;
import com.sasha.simplecmdsys.SimpleCommand;
import net.minecraft.block.Block;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.sasha.simplecmdsys.SimpleCommandInfo;
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
            for (int i = 0; i < ModuleXray.xrayBlocks.size(); i++) {
                if (i==0) {
                    builder.append(ModuleXray.xrayBlocks.get(i).getLocalizedName());
                    continue;
                }
                builder.append(", ").append(ModuleXray.xrayBlocks.get(i).getLocalizedName());
            }
            AdorufuMod.logMsg(false, "Listing registered blocks:");
            AdorufuMod.logMsg(builder.toString());
            return;
        }
        if (this.getArguments().length==2) {
            switch (this.getArguments()[0].toLowerCase()) {
                case "add":
                    Block b = Block.getBlockFromName(this.getArguments()[1]);
                    if (b == null) {
                        AdorufuMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (ModuleXray.xrayBlocks.contains(b)) {
                        AdorufuMod.logErr(false, "That block is already added to xray!");
                        break;
                    }
                    ModuleXray.xrayBlocks.add(b);
                    AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully added");
                    AdorufuMod.minecraft.renderGlobal.loadRenderers();
                    AdorufuMod.scheduler.schedule(() -> {
                        try { AdorufuMod.DATA_MANAGER.saveXrayBlocks(ModuleXray.xrayBlocks); } catch (IOException e) { e.printStackTrace(); }
                    }, 0, TimeUnit.NANOSECONDS);
                    break;
                case "del":
                    Block delb = Block.getBlockFromName(this.getArguments()[1]);
                    if (delb == null) {
                        AdorufuMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (!ModuleXray.xrayBlocks.contains(delb)) {
                        AdorufuMod.logErr(false, "That block is not added to xray!");
                        break;
                    }
                    ModuleXray.xrayBlocks.remove(delb);
                    AdorufuMod.logMsg(false, this.getArguments()[1] + " successfully removed");
                    AdorufuMod.minecraft.renderGlobal.loadRenderers();
                    AdorufuMod.scheduler.schedule(() -> {
                        try { AdorufuMod.DATA_MANAGER.saveXrayBlocks(ModuleXray.xrayBlocks); } catch (IOException e) { e.printStackTrace(); }
                    }, 0, TimeUnit.NANOSECONDS);
                    break;
            }
            return;
        }
        AdorufuMod.logErr(false, "Invalid arguments! Try \"-help command xray\"");
    }
}
