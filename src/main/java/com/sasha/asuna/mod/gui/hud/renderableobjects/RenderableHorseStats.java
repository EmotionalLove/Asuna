/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.gui.hud.renderableobjects;


import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.impl.HorsestatsRenderableFeature;
import com.sasha.asuna.mod.gui.hud.AsunaHUD;
import com.sasha.asuna.mod.gui.hud.RenderableObject;
import com.sasha.asuna.mod.gui.hud.ScreenCornerPos;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.entity.passive.EntityHorse;

import static com.sasha.asuna.mod.AsunaMod.minecraft;
import static com.sasha.asuna.mod.misc.AsunaMath.dround;

public class RenderableHorseStats extends RenderableObject {
    public RenderableHorseStats() {
        super("HorseStats", ScreenCornerPos.RIGHTTOP,
                Manager.Feature.findFeature(HorsestatsRenderableFeature.class));

    }

    @Override
    public void renderObjectLT(int yyy) {
        String s = "\247" + "fHorse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
        if (minecraft.player.isRidingHorse() && minecraft.player.getRidingEntity() instanceof EntityHorse) {
            EntityHorse e = ((EntityHorse) minecraft.player.getRidingEntity());
            AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), 4, yyy, 0xffffff);
            return;
        }
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fHorse stats: " + "\247" + "4None.", 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectLB(int yyy) {
        String s = "\247" + "fHorse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
        if (minecraft.player.isRidingHorse() && minecraft.player.getRidingEntity() instanceof EntityHorse) {
            EntityHorse e = ((EntityHorse) minecraft.player.getRidingEntity());
            AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), 4, yyy, 0xffffff);
            return;
        }
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fHorse stats: " + "\247" + "4None.", 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fHorse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
        if (minecraft.player.isRidingHorse() && minecraft.player.getRidingEntity() instanceof EntityHorse) {
            EntityHorse e = ((EntityHorse) minecraft.player.getRidingEntity());
            AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3))) - 2), yyy, 0xffffff);
            return;
        }
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fHorse stats: " + "\247" + "4None.", (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth("Horse stats: None.")) - 2, yyy, 0xffffff);
    }

    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fHorse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
        if (minecraft.player.isRidingHorse() && minecraft.player.getRidingEntity() instanceof EntityHorse) {
            EntityHorse e = ((EntityHorse) minecraft.player.getRidingEntity());
            AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3))) - 2), yyy, 0xffffff);
            return;
        }
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fHorse stats: " + "\247" + "4None.", (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth("Horse stats: None.")) - 2, yyy, 0xffffff);
    }
}
