package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.ScreenCornerPos;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;

import java.io.IOException;

import static com.sasha.xdolf.XdolfMath.dround;
import static com.sasha.xdolf.XdolfMod.mc;
import static com.sasha.xdolf.gui.XdolfHUD.sWidth;

public class RenderableHacklist extends RenderableObject {
    public RenderableHacklist() {
        super("Hacklist", ScreenCornerPos.RIGHTBOTTOM);
        try {
            this.setPos(XdolfMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            int count = 0;
            for (XdolfModule module : XdolfModule.displayList) {
                if (module.isEnabled() && module.getSuffix() != null && !module.getSuffix().contains("[")) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured(), 4, (yyy) + (10 * count), 0xffffff);
                    count++;
                }
                else if (module.isEnabled()) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured() + module.getSuffix(), 4, (yyy) - (10 * count), 0xffffff);
                    count++;
                }
            }
        }
    }
    @Override
    public void renderObjectLB(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            double xx = dround(mc.player.posX, 3);
            double y = dround(mc.player.posY, 3);
            double z = dround(mc.player.posZ, 3);
            if (mc.player.dimension == 0 || mc.player.dimension == 1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx/8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z/8, 3) + ")", 4, yyy, 0xffffff);
            }
            if (mc.player.dimension == -1) {
                Fonts.segoe_36.drawStringWithShadow("\247" + "fX " + "\247" + "7" + xx + " (" + dround(xx*8, 3) + ") " + "\247" + "fY " + "\247" + "7" + y + " " + "\247" + "fZ " + "\247" + "7" + z + " (" + dround(z*8, 3) + ")", 4, yyy, 0xffffff);
            }
        }
    }
    @Override
    public void renderObjectRT(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            int count = 0;
            for (XdolfModule module : XdolfModule.displayList) {
                if (module.isEnabled() && module.getSuffix() != null && !module.getSuffix().contains("[")) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName()) - 2, (yyy) + (10 * count), 0xffffff);
                    count++;
                }
                else if (module.isEnabled()) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured() + module.getSuffix(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName() + module.getSuffix()) - 2, (yyy) + (10 * count), 0xffffff);
                    count++;
                }
            }
        }
    }
    @Override
    public void renderObjectRB(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            int count = 0;
            for (XdolfModule module : XdolfModule.displayList) {
                if (module.isEnabled() && module.getSuffix() != null && !module.getSuffix().contains("[")) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName()) - 2, (yyy) - (10 * count), 0xffffff);
                    count++;
                }
                else if (module.isEnabled()) {
                    Fonts.segoe_36.drawStringWithShadow("" + module.getModuleNameColoured() + module.getSuffix(), sWidth - Fonts.segoe_36.getStringWidth(module.getModuleName() + module.getSuffix()) - 2, (yyy) - (10 * count), 0xffffff);
                    count++;
                }
            }
        }
    }
}
