package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.XdolfModHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.misc.TPS;

public class RenderableTickrate extends RenderableObject {
    public RenderableTickrate(String pos) {
        super("Tickrate", pos);
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate()), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate()), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate());
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfModHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fTPS" + "\247" + "7: " + formatTickrate(TPS.getTickRate());
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfModHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    private static String formatTickrate(float tps) {
        if (tps > 15) {
            return "\247" + "a" + tps;
        }
        else if (tps > 10) {
            return "\247" + "e" + tps;
        }
        else if (tps > 5) {
            return "\247" + "c" + tps;
        }
        else if (tps >= 0) {
            return "\247" + "4" + tps;
        }
        return "\247" + "b" + tps;
    }
}
