package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.gui.XdolfModHUD;
import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.fonts.Fonts;
import net.minecraft.client.Minecraft;

public class RenderableFramerate extends RenderableObject {
    public RenderableFramerate(String pos) {
        super("Framerate", pos);
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
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfModHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fFPS" + "\247" + "7: " + formatFPS(Minecraft.getDebugFPS());
        Fonts.segoe_36.drawStringWithShadow(s, (XdolfModHUD.sWidth - Fonts.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
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
