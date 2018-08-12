package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by Sasha on 11/08/2018 at 8:27 PM
 **/
@ModuleInfo(description = "Automatically moves a totem into your offhand if it's empty")
public class ModuleAutoTotem extends XdolfModule {
    public ModuleAutoTotem() {
        super("AutoTotem", XdolfCategory.COMBAT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (!this.isEnabled())
            return;
        ItemStack offhand = XdolfMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        int i = 0;
        for (int x = 9; x <= 44; x++) {
            ItemStack stack = XdolfMod.minecraft.player.inventory.getStackInSlot(x);
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                i++;
            }
        }
        this.setSuffix(i +"");
        if (offhand.getItem() != Items.TOTEM_OF_UNDYING) {
            for (int x = 9; x <= 44; x++) {
                ItemStack stack = XdolfMod.minecraft.player.inventory.getStackInSlot(x);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING && XdolfMod.minecraft.currentScreen == null /* don't move totems if the inventory is open */) {
                    XdolfMod.minecraft.playerController.windowClick(0, x, 0, ClickType.PICKUP, XdolfMod.minecraft.player);
                    XdolfMod.minecraft.playerController.windowClick(0, 45, 0, ClickType.PICKUP, XdolfMod.minecraft.player);
                    break;
                }
            }
        }

    }
}
