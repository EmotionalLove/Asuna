package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.module.modules.ModuleXray;
import net.minecraft.block.Block;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sasha on 11/08/2018 at 1:18 PM
 **/
@CommandInfo(description = "Add, remote, or list blocks added to xray", syntax = {"<'add'/'del'> <block>", "<'list'>"})
public class XrayCommand extends XdolfCommand {
    public XrayCommand() {
        super("xray");
    }
    @Override
    public void onCommand() {
        if (this.getArguments() == null) {
            XdolfMod.logErr(false, "Arguments required! Try \"-help command xray\"");
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
            XdolfMod.logMsg(false, "Listing registered blocks:");
            XdolfMod.logMsg(builder.toString());
            return;
        }
        if (this.getArguments().length==2) {
            switch (this.getArguments()[0].toLowerCase()) {
                case "add":
                    Block b = Block.getBlockFromName(this.getArguments()[1]);
                    if (b == null) {
                        XdolfMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (ModuleXray.xrayBlocks.contains(b)) {
                        XdolfMod.logErr(false, "That block is already added to xray!");
                        break;
                    }
                    ModuleXray.xrayBlocks.add(b);
                    XdolfMod.logMsg(false, this.getArguments()[1] + " successfully added");
                    XdolfMod.minecraft.renderGlobal.loadRenderers();
                    XdolfMod.scheduler.schedule(() -> {
                        try { XdolfMod.DATA_MANAGER.saveXrayBlocks(ModuleXray.xrayBlocks); } catch (IOException e) { e.printStackTrace(); }
                    }, 0, TimeUnit.NANOSECONDS);
                    break;
                case "del":
                    Block delb = Block.getBlockFromName(this.getArguments()[1]);
                    if (delb == null) {
                        XdolfMod.logErr(false, this.getArguments()[1] + " isn't a valid block! (If the name of your block has spaces in it, try surrounding the entire name in quotation marks)");
                        break;
                    }
                    if (!ModuleXray.xrayBlocks.contains(delb)) {
                        XdolfMod.logErr(false, "That block is not added to xray!");
                        break;
                    }
                    ModuleXray.xrayBlocks.remove(delb);
                    XdolfMod.logMsg(false, this.getArguments()[1] + " successfully removed");
                    XdolfMod.minecraft.renderGlobal.loadRenderers();
                    XdolfMod.scheduler.schedule(() -> {
                        try { XdolfMod.DATA_MANAGER.saveXrayBlocks(ModuleXray.xrayBlocks); } catch (IOException e) { e.printStackTrace(); }
                    }, 0, TimeUnit.NANOSECONDS);
                    break;
            }
            return;
        }
        XdolfMod.logErr(false, "Invalid arguments! Try \"-help command xray\"");
    }
}
