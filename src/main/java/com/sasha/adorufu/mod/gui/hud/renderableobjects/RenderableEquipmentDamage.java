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

package com.sasha.adorufu.mod.gui.hud.renderableobjects;


import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.impl.EquipmentDamageRenderableFeature;
import com.sasha.adorufu.mod.gui.hud.AdorufuHUD;
import com.sasha.adorufu.mod.gui.hud.RenderableObject;
import com.sasha.adorufu.mod.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.mod.misc.Manager;

public class RenderableEquipmentDamage extends RenderableObject {

    public RenderableEquipmentDamage() {
        super("EquipmentDamage", ScreenCornerPos.RIGHTTOP,
                Manager.Feature.findFeature(EquipmentDamageRenderableFeature.class));

    }

    public String toString() {
        StringBuilder builder = new StringBuilder("\247fEquipment Damage:");
        if (helmDamage(false) != 0) {
            builder.append(" \2477H: ");
            int max = helmDamage(true);
            int cur = helmDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                builder.append(color).append(max - cur);
            } else
            if ((max / 3) < max - cur) {
                color += "e";
                builder.append(color).append(max - cur);
            } else
            if ((max / 4) < max - cur) {
                color += "c";
                builder.append(color).append(max - cur);
            } else
            if ((max / 8) < max - cur) {
                color += "4";
                builder.append(color).append(max - cur);
            }
        }
        if (shirtDamage(false) != 0) {
            builder.append(" \2477C: ");
            int max = shirtDamage(true);
            int cur = shirtDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                builder.append(color).append(max - cur);
            } else
            if ((max / 3) < max - cur) {
                color += "e";
                builder.append(color).append(max - cur);
            } else
            if ((max / 4) < max - cur) {
                color += "c";
                builder.append(color).append(max - cur);
            } else
            if ((max / 8) < max - cur) {
                color += "4";
                builder.append(color).append(max - cur);
            }
        }
        if (pantsDamage(false) != 0) {
            builder.append(" \2477L: ");
            int max = pantsDamage(true);
            int cur = pantsDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                builder.append(color).append(max - cur);
            } else
            if ((max / 3) < max - cur) {
                color += "e";
                builder.append(color).append(max - cur);
            } else
            if ((max / 4) < max - cur) {
                color += "c";
                builder.append(color).append(max - cur);
            } else
            if ((max / 8) < max - cur) {
                color += "4";
                builder.append(color).append(max - cur);
            }
        }
        if (bootsDamage(false) != 0) {
            builder.append(" \2477B: ");
            int max = bootsDamage(true);
            int cur = bootsDamage(false);
            String color = "\247";
            if ((max / 2) < max - cur) {
                color += "a";
                builder.append(color).append(max - cur);
            } else
            if ((max / 3) < max - cur) {
                color += "e";
                builder.append(color).append(max - cur);
            } else
            if ((max / 4) < max - cur) {
                color += "c";
                builder.append(color).append(max - cur);
            } else
            if ((max / 8) < max - cur) {
                color += "4";
                builder.append(color).append(max - cur);
            }
        }
        if (heldDamage(false) != 0) {
            builder.append(" \2477Held: ");
            int max = heldDamage(true);
            int cur = heldDamage(false);
            String color = "\247";
            if ((max / 2) > cur - max) {
                color += "a";
                builder.append(color).append(max - cur);
            } else
            if ((max / 3) < max - cur) {
                color += "e";
                builder.append(color).append(max - cur);
            } else
            if ((max / 4) < max - cur) {
                color += "c";
                builder.append(color).append(max - cur);
            } else
            if ((max / 8) < max - cur) {
                color += "4";
                builder.append(color).append(max - cur);
            }
        }
        return builder.toString();
    }

    private static int bootsDamage(boolean bool) {
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

    private static int helmDamage(boolean bool) {
        if (bool) return AdorufuMod.minecraft.player.inventory.armorInventory.get(3).getMaxDamage();
        return AdorufuMod.minecraft.player.inventory.armorInventory.get(3).getItemDamage();
    }

    private static int heldDamage(boolean bool) {
        if (bool) return AdorufuMod.minecraft.player.getHeldItemMainhand().getMaxDamage();
        return AdorufuMod.minecraft.player.getHeldItemMainhand().getItemDamage();
    }

    @Override
    public void renderObjectLT(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(toString(), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectLB(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(toString(), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectRT(int yyy) {
        String s = toString();
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }

    @Override
    public void renderObjectRB(int yyy) {
        String s = toString();
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
}
