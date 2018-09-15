/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.gui.hud.renderableobjects;


import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.desktop.AdorufuWindowsBatteryManager;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.hud.RenderableObject;
import com.sasha.adorufu.gui.hud.ScreenCornerPos;

import java.io.IOException;

public class RenderableBatteryLife extends RenderableObject {

    public boolean isSupported = false;

    public RenderableBatteryLife() {
        super("BatteryLife", ScreenCornerPos.LEFTBOTTOM);
        try {
            this.setPos(AdorufuMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
        if (AdorufuMod.BATTERY_MANAGER != null) {
            isSupported = true;
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fBattery" + "\247" + "7: " + formatBattery(), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectLB(int yyy) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fBattery" + "\247" + "7: " + formatBattery(), 4, yyy, 0xffffff);
    }
    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fBattery" + "\247" + "7: " + formatBattery();
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fBattery" + "\247" + "7: " + formatBattery();
        AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AdorufuHUD.sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }
    private String formatBattery() {
        AdorufuWindowsBatteryManager.INSTANCE.GetSystemPowerStatus(AdorufuMod.BATTERY_MANAGER);
        if (!isSupported) {
            return "\2478Battery status is unavailable on this machine...";
        }
        int battery = AdorufuMod.BATTERY_MANAGER.BatteryLifePercent;
        if (battery == (byte)255) {
            return "\2478???%";
        }
        boolean charging = AdorufuMod.BATTERY_MANAGER.ACLineStatus == (byte)1;
        if (charging) {
            return "\247" + "b" + battery + "%";
        }
        else if (battery > 80) {
            return "\247" + "a" + battery + "%";
        }
        else if (battery > 50) {
            return "\247" + "e" + battery + "%";
        }
        else if (battery > 20) {
            return "\247" + "c" + battery + "%";
        }
        else if (battery >= 0) {
            return "\247" + "4" + battery + "%";
        }
        return "\247" + "b" + battery + "%";
    }
}
