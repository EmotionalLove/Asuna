package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.gui.fonts.Fonts;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuModule;

import java.io.IOException;

import static com.sasha.adorufu.gui.hud.AdorufuHUD.sWidth;

public class RenderableHacklist extends RenderableObject {
    public RenderableHacklist() {
        super("Hacklist", ScreenCornerPos.RIGHTBOTTOM);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            int count = 0;
            for (AdorufuModule module : AdorufuModule.displayList) {
                if (module.isEnabled() && module.getSuffix().equals("")) {
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
            // TODO
        }
    }
    @Override
    public void renderObjectRT(int yyy) {
        if (ModuleManager.getModuleByName("Hacklist").isEnabled()) {
            int count = 0;
            for (AdorufuModule module : AdorufuModule.displayList) {
                if (module.isEnabled() && module.getSuffix().equals("")) {
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
            for (AdorufuModule module : AdorufuModule.displayList) {
                if (module.isEnabled() && module.getSuffix().equals("")) {
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
