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

package com.sasha.asuna.mod.gui.hud.renderableobjects;


import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.impl.TickrateRenderableFeature;
import com.sasha.asuna.mod.gui.hud.AsunaHUD;
import com.sasha.asuna.mod.gui.hud.RenderableObject;
import com.sasha.asuna.mod.gui.hud.ScreenCornerPos;
import com.sasha.asuna.mod.misc.Manager;
import com.sasha.asuna.mod.misc.TPS;

public class RenderableTickrate extends RenderableObject {
    public RenderableTickrate() {
        super("TPS", ScreenCornerPos.RIGHTTOP,
                Manager.Feature.findFeature(TickrateRenderableFeature.class));

    }

    private static String formatTickrate(float tps) {
        if (!TPS.isServerResponding()) {
            return "\247" + "7" + tps + " \247l\247c(not responding!)";
        }
        if (tps > 15) {
            return "\247" + "a" + tps;
        } else if (tps > 10) {
            return "\247" + "e" + tps;
        } else if (tps > 5) {
            return "\247" + "c" + tps;
        } else if (tps >= 0) {
            return "\247" + "4" + tps;
        }
        return "\247" + "b" + tps;
    }

    @Override
    public void renderObjectLT(int yyy) {
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate()), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectLB(int yyy) {
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate()), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate());
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }

    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate());
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
}
