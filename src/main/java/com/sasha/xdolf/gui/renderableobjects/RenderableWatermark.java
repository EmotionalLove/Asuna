package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;

public class RenderableWatermark extends RenderableObject {
    public RenderableWatermark(String pos) {
        super("Watermark", pos);
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Xdolf " + "\247" + "7" + XdolfMod.VERSION, 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Xdolf " + "\247" + "7" + XdolfMod.VERSION, 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "4Xdolf " + "\247" + "7" + XdolfMod.VERSION;
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Xdolf " + "\247" + "7" + XdolfMod.VERSION, 4, yyy, 0xffffff);
    }
}
