package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import static com.sasha.xdolf.XdolfMod.mc;

public class RenderableInventoryStats extends RenderableObject {
    public RenderableInventoryStats(String pos) {
        super("InventoryStatus", pos);
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(mc.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(mc.player), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(mc.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(mc.player), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(mc.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(mc.player);
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fInventory Statistics" + "\247" + "7: " + "Food " + getFoodItemsInInventory(mc.player) + " " + "\247" + "7Building Blocks " + getBuildingBlocksInInventory(mc.player);
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
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
