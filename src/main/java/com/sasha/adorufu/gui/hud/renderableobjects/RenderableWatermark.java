package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.fonts.Fonts;

import java.io.IOException;

public class RenderableWatermark extends RenderableObject {
    public RenderableWatermark() {
        super("Watermark", ScreenCornerPos.LEFTTOP);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Adorufu " + "\247" + "7" + AdorufuMod.VERSION, 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Adorufu " + "\247" + "7" + AdorufuMod.VERSION, 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "4Adorufu " + "\247" + "7" + AdorufuMod.VERSION;
        Fonts.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "4Adorufu " + "\247" + "7" + AdorufuMod.VERSION, 4, yyy, 0xffffff);
    }
}
