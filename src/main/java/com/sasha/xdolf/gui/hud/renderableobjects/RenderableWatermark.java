package com.sasha.xdolf.gui.hud.renderableobjects;


import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.hud.RenderableObject;
import com.sasha.xdolf.gui.hud.ScreenCornerPos;
import com.sasha.xdolf.gui.hud.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;

import java.io.IOException;

public class RenderableWatermark extends RenderableObject {
    public RenderableWatermark() {
        super("Watermark", ScreenCornerPos.LEFTTOP);
        try {
            this.setPos(XdolfMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
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
