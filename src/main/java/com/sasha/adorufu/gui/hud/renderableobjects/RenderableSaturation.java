package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.misc.AdorufuMath;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.fonts.Fonts;

import java.io.IOException;

import static com.sasha.adorufu.AdorufuMod.minecraft;

public class RenderableSaturation extends RenderableObject {
    public RenderableSaturation() {
        super("Saturation", ScreenCornerPos.RIGHTTOP);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fSaturation" + "\247" + "7: " + formatSaturation(AdorufuMath.fround(minecraft.player.getFoodStats().getSaturationLevel(), 2)), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fSaturation" + "\247" + "7: " + formatSaturation(AdorufuMath.fround(minecraft.player.getFoodStats().getSaturationLevel(), 2)), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fSaturation" + "\247" + "7: " + formatSaturation(AdorufuMath.fround(minecraft.player.getFoodStats().getSaturationLevel(), 2));
        Fonts.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fSaturation" + "\247" + "7: " + formatSaturation(AdorufuMath.fround(minecraft.player.getFoodStats().getSaturationLevel(), 2));
        Fonts.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    private static String formatSaturation(float sat) {
        if (sat > 5.0) {
            return "\247" + "a" + sat;
        }
        else if (sat > 3.0) {
            return "\247" + "e" + sat;
        }
        else if (sat > 1.0) {
            return "\247" + "c" + sat;
        }
        else if (sat >= 0.0) {
            return "\247" + "4" + sat;
        }
        return "\247" + "b" + sat;
    }
}
