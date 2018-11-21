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

package com.sasha.asuna.mod.gui.hud.renderableobjects;


import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.asuna.AsunaBatteryLevelChangedEvent;
import com.sasha.asuna.mod.feature.impl.BatteryLifeRenderableFeature;
import com.sasha.asuna.mod.gui.hud.AsunaHUD;
import com.sasha.asuna.mod.gui.hud.RenderableObject;
import com.sasha.asuna.mod.gui.hud.ScreenCornerPos;
import com.sasha.asuna.mod.misc.Manager;

public class RenderableBatteryLife extends RenderableObject {

    public boolean isSupported = false;
    private int lastPercent = -1;

    public RenderableBatteryLife() {
        super("BatteryLife", ScreenCornerPos.LEFTBOTTOM,
                Manager.Feature.findFeature(BatteryLifeRenderableFeature.class));
        if (AsunaMod.BATTERY_MANAGER != null) {
            isSupported = true;
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fBattery" + "\247" + "7: " + formatBattery(), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectLB(int yyy) {
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("\247" + "fBattery" + "\247" + "7: " + formatBattery(), 4, yyy, 0xffffff);
    }

    @Override
    public void renderObjectRT(int yyy) {
        String s = "\247" + "fBattery" + "\247" + "7: " + formatBattery();
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }

    @Override
    public void renderObjectRB(int yyy) {
        String s = "\247" + "fBattery" + "\247" + "7: " + formatBattery();
        AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow(s, (AsunaHUD.sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(s) - 2), yyy, 0xffffff);
    }

    private String formatBattery() {
        int old = lastPercent;
        if (!isSupported || AsunaMod.BATTERY_MANAGER_INTERFACE == null) {
            return "\2478Battery status is unavailable on this machine...";
        }
        AsunaMod.BATTERY_MANAGER_INTERFACE.GetSystemPowerStatus(AsunaMod.BATTERY_MANAGER);
        if (lastPercent != AsunaMod.BATTERY_MANAGER.BatteryLifePercent) {
            lastPercent = AsunaMod.BATTERY_MANAGER.BatteryLifePercent;
            AsunaBatteryLevelChangedEvent event = new AsunaBatteryLevelChangedEvent(old, lastPercent, AsunaMod.BATTERY_MANAGER.ACLineStatus == (byte) 1);
            AsunaMod.EVENT_MANAGER.invokeEvent(event);
        }
        int battery = AsunaMod.BATTERY_MANAGER.BatteryLifePercent;
        if (battery == (byte) 255) {
            return "\2478???%";
        }
        boolean charging = AsunaMod.BATTERY_MANAGER.ACLineStatus == (byte) 1;
        if (charging) {
            return "\247" + "b" + battery + "%";
        } else if (battery > 80) {
            return "\247" + "a" + battery + "%";
        } else if (battery > 50) {
            return "\247" + "e" + battery + "%";
        } else if (battery > 20) {
            return "\247" + "c" + battery + "%";
        } else if (battery >= 0) {
            return "\247" + "4" + battery + "%";
        }
        return "\247" + "b" + battery + "%";
    }
}
