package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.XdolfMath;
import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import static com.sasha.xdolf.XdolfMod.mc;

public class RenderableSaturation extends RenderableObject {
    public RenderableSaturation(String pos) {
        super("Saturation", pos);
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fSaturation" + "\247" + "7: " + formatSaturation(XdolfMath.fround(mc.player.getFoodStats().getSaturationLevel(), 2)), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fSaturation" + "\247" + "7: " + formatSaturation(XdolfMath.fround(mc.player.getFoodStats().getSaturationLevel(), 2)), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fSaturation" + "\247" + "7: " + formatSaturation(XdolfMath.fround(mc.player.getFoodStats().getSaturationLevel(), 2));
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fSaturation" + "\247" + "7: " + formatSaturation(XdolfMath.fround(mc.player.getFoodStats().getSaturationLevel(), 2));
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
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
