package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.fonts.Fonts;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class RenderableFramerate extends RenderableObject {
    public RenderableFramerate() {
        super("FPS", ScreenCornerPos.LEFTBOTTOM);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fFPS" + "\247" + "7: " + formatFPS(Minecraft.getDebugFPS()), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        Fonts.segoe_36.drawStringWithShadow("\247" + "fFPS" + "\247" + "7: " + formatFPS(Minecraft.getDebugFPS()), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fFPS" + "\247" + "7: " + formatFPS(Minecraft.getDebugFPS());
        Fonts.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fFPS" + "\247" + "7: " + formatFPS(Minecraft.getDebugFPS());
        Fonts.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    private static String formatFPS(int fps) {
        if (fps > 80) {
            return "\247" + "a" + fps;
        }
        else if (fps > 60) {
            return "\247" + "e" + fps;
        }
        else if (fps > 30) {
            return "\247" + "c" + fps;
        }
        else if (fps >= 0) {
            return "\247" + "4" + fps;
        }
        return "\247" + "b" + fps;
    }
}
