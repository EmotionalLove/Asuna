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

package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.io.IOException;

public class RenderableEquipmentDamage extends RenderableObject {

    public RenderableEquipmentDamage() {
        super("EquipmentDamage", ScreenCornerPos.RIGHTTOP);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(getTheWholeFuckingString(), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(getTheWholeFuckingString(), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = getTheWholeFuckingString();
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = getTheWholeFuckingString();
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
    public static String getTheWholeFuckingString() {
        StringBuilder boolder = new StringBuilder("\247fEquipment Damage:");
        HELMCLR:
        if (helmDamage(false) != 0) {
            boolder.append(" \2477H: ");
            int max = helmDamage(true);
            int cur = helmDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                boolder.append(color).append(max - cur);
                break HELMCLR;
            }
            if ((max / 3) < max - cur) {
                color += "e";
                boolder.append(color).append(max - cur);
                break HELMCLR;
            }
            if ((max / 4) < max - cur) {
                color += "c";
                boolder.append(color).append(max - cur);
                break HELMCLR;
            }
            if ((max / 8) < max - cur) {
                color += "4";
                boolder.append(color).append(max - cur);
            }
        }
        CHESTCLR:
        if (shirtDamage(false) != 0) {
            boolder.append(" \2477C: ");
            int max = shirtDamage(true);
            int cur = shirtDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                boolder.append(color).append(max - cur);
                break CHESTCLR;
            }
            if ((max / 3) < max - cur) {
                color += "e";
                boolder.append(color).append(max - cur);
                break CHESTCLR;
            }
            if ((max / 4) < max - cur) {
                color += "c";
                boolder.append(color).append(max - cur);
                break CHESTCLR;
            }
            if ((max / 8) < max - cur) {
                color += "4";
                boolder.append(color).append(max - cur);
            }
        }
        PANTCLR:
        if (pantsDamage(false) != 0) {
            boolder.append(" \2477L: ");
            int max = pantsDamage(true);
            int cur = pantsDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                boolder.append(color).append(max - cur);
                break PANTCLR;
            }
            if ((max / 3) < max - cur) {
                color += "e";
                boolder.append(color).append(max - cur);
                break PANTCLR;
            }
            if ((max / 4) < max - cur) {
                color += "c";
                boolder.append(color).append(max - cur);
                break PANTCLR;
            }
            if ((max / 8) < max - cur) {
                color += "4";
                boolder.append(color).append(max - cur);
            }
        }
        BOOTCLR:
        if (bootsDamage(false) != 0) {
            boolder.append(" \2477B: ");
            int max = bootsDamage(true);
            int cur = bootsDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                boolder.append(color).append(max - cur);
                break BOOTCLR;
            }
            if ((max / 3) < max - cur) {
                color += "e";
                boolder.append(color).append(max - cur);
                break BOOTCLR;
            }
            if ((max / 4) < max - cur) {
                color += "c";
                boolder.append(color).append(max - cur);
                break BOOTCLR;
            }
            if ((max / 8) < max - cur) {
                color += "4";
                boolder.append(color).append(max - cur);
            }
        }
        HELDCLR:
        if (heldDamage(false) != 0) {
            boolder.append(" \2477Held: ");
            int max = heldDamage(true);
            int cur = heldDamage(false);
            String color = "\247";
            if ((max / 2) > cur - max) {
                color += "a";
                boolder.append(color).append(max - cur);
                break HELDCLR;
            }
            if ((max / 3) < max - cur) {
                color += "e";
                boolder.append(color).append(max - cur);
                break HELDCLR;
            }
            if ((max / 4) < max - cur) {
                color += "c";
                boolder.append(color).append(max - cur);
                break HELDCLR;
            }
            if ((max / 8) < max - cur) {
                color += "4";
                boolder.append(color).append(max - cur);
            }
            else {
                color += "d";
                boolder.append(color).append(max - cur);
            }
        }
        return boolder.toString();
    }
    private static int bootsDamage(boolean bool){
        if (bool) return AdorufuMod.minecraft.player.inventory.armorInventory.get(0).getMaxDamage();
        return AdorufuMod.minecraft.player.inventory.armorInventory.get(0).getItemDamage();
    }
    private static int pantsDamage(boolean bool) {
        if (bool) return AdorufuMod.minecraft.player.inventory.armorInventory.get(1).getMaxDamage();
        return AdorufuMod.minecraft.player.inventory.armorInventory.get(1).getItemDamage();
    }
    private static int shirtDamage(boolean bool) {
        if (bool) return AdorufuMod.minecraft.player.inventory.armorInventory.get(2).getMaxDamage();
        return AdorufuMod.minecraft.player.inventory.armorInventory.get(2).getItemDamage();
    }
    private static int helmDamage(boolean bool){
        if (bool) return AdorufuMod.minecraft.player.inventory.armorInventory.get(3).getMaxDamage();
        return AdorufuMod.minecraft.player.inventory.armorInventory.get(3).getItemDamage();
    }
    private static int heldDamage(boolean bool){
        if (bool) return AdorufuMod.minecraft.player.getHeldItemMainhand().getMaxDamage();
        return AdorufuMod.minecraft.player.getHeldItemMainhand().getItemDamage();
    }
}
