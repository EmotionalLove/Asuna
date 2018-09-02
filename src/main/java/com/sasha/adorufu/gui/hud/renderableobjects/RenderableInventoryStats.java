package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.fonts.FontManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.io.IOException;

import static com.sasha.adorufu.AdorufuMod.minecraft;

public class RenderableInventoryStats extends RenderableObject {
    public RenderableInventoryStats() {
        super("InventoryStats", ScreenCornerPos.RIGHTTOP);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(minecraft.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(minecraft.player), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(minecraft.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(minecraft.player), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(minecraft.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(minecraft.player);
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(minecraft.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(minecraft.player);
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    public static String getFoodItemsInInventory(EntityPlayerSP player) {
        int i = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem().getCreativeTab() == CreativeTabs.FOOD) {
                i += stack.getCount();
            }
        }
        if (i <= 5) {
            return "\247" + "4" + i;
        }
        if (i <= 10) {
            return "\247" + "c" + i;
        }
        if (i <= 32) {
            return "\247" + "e" + i;
        }
        else {
            return "\247" + "a" + i;
        }
    }
    public static String getBuildingBlocksInInventory(EntityPlayerSP player) {
        int i = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem().getCreativeTab() == CreativeTabs.BUILDING_BLOCKS) {
                i += stack.getCount();
            }
        }
        if (i <= 5) {
            return "\247" + "4" + i;
        }
        if (i <= 10) {
            return "\247" + "c" + i;
        }
        if (i <= 32) {
            return "\247" + "e" + i;
        }
        else {
            return "\247" + "a" + i;
        }
    }
}
