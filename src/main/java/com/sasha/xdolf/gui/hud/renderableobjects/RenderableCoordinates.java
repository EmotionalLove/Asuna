package com.sasha.xdolf.gui.hud.renderableobjects;

import com.sasha.xdolf.misc.XdolfMath;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.hud.RenderableObject;
import com.sasha.xdolf.gui.hud.ScreenCornerPos;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.module.ModuleManager;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

import static com.sasha.xdolf.misc.XdolfMath.dround;
import static com.sasha.xdolf.XdolfMod.minecraft;

public class RenderableCoordinates extends RenderableObject {
    public RenderableCoordinates() {
        super("Coordinates", ScreenCornerPos.LEFTBOTTOM);
        try {
            this.setPos(XdolfMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        if (ModuleManager.getModuleByName("Coordinates").isEnabled()) {
            double xx = dround(minecraft.player.posX, 3);
            double y = dround(minecraft.player.posY, 3);
            double z = dround(minecraft.player.posZ, 3);
            if (minecraft.player.dimension == 0 || minecraft.player.dimension == 1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")" + attachDirection(), 4, yyy, 0xffffff);
            }
            if (minecraft.player.dimension == -1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")" + attachDirection(), 4, yyy, 0xffffff);
            }
        }
    }
    @Override
    public void renderObjectLB(int yyy) {
        if (ModuleManager.getModuleByName("Coordinates").isEnabled()) {
            double xx = dround(minecraft.player.posX, 3);
            double y = dround(minecraft.player.posY, 3);
            double z = dround(minecraft.player.posZ, 3);
            if (minecraft.player.dimension == 0 || minecraft.player.dimension == 1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")" + attachDirection(), 4, yyy, 0xffffff);
            }
            if (minecraft.player.dimension == -1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")" + attachDirection(), 4, yyy, 0xffffff);
            }
        }
    }
    @Override
    public void renderObjectRT(int yyy) {
        if (ModuleManager.getModuleByName("Coordinates").isEnabled()) {
            double xx = dround(minecraft.player.posX, 3);
            double y = dround(minecraft.player.posY, 3);
            double z = dround(minecraft.player.posZ, 3);
            ScaledResolution sr = new ScaledResolution(minecraft);
            int width = sr.getScaledWidth();
            if (minecraft.player.dimension == 0 || minecraft.player.dimension == 1) {
                String ss = "\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")" + attachDirection();
                Fonts.segoe_36.drawStringWithShadow(ss, width - Fonts.segoe_36.getStringWidth(ss) - 2, yyy, 0xffffff);
            }
            if (minecraft.player.dimension == -1) {
                String s = "\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")" + attachDirection();
                Fonts.segoe_36.drawStringWithShadow(s, width - Fonts.segoe_36.getStringWidth(s) - 2, yyy, 0xffffff);
            }
        }
    }
    @Override
    public void renderObjectRB(int yyy) {
        if (ModuleManager.getModuleByName("Coordinates").isEnabled()) {
            double xx = dround(minecraft.player.posX, 3);
            double y = dround(minecraft.player.posY, 3);
            double z = dround(minecraft.player.posZ, 3);
            ScaledResolution sr = new ScaledResolution(minecraft);
            int width = sr.getScaledWidth();
            if (minecraft.player.dimension == 0 || minecraft.player.dimension == 1) {
                String ss = "\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")" + attachDirection();
                Fonts.segoe_36.drawStringWithShadow(ss, width - Fonts.segoe_36.getStringWidth(ss) - 2, yyy, 0xffffff);
            }
            if (minecraft.player.dimension == -1) {
                String s = "\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")" + attachDirection();
                Fonts.segoe_36.drawStringWithShadow(s, width - Fonts.segoe_36.getStringWidth(s) - 2, yyy, 0xffffff);
            }
        }
    }
    private static String attachDirection() {
        return " " + "\247" + "f[" + XdolfMath.getStringDirection(XdolfMath.getCardinalDirection(minecraft.player.rotationYaw)) + "]";

    }
}
