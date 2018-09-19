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

package com.sasha.adorufu.mod.gui.clickgui;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.mod.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.mod.gui.clickgui.windows.*;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdorufuClickGUI extends GuiScreen {


    public static ArrayList<AdorufuWindow> registeredWindows = new ArrayList<>();
    public static ArrayList<AdorufuWindow> unFocusedWindows = new ArrayList<>();


    public static WindowRender render = new WindowRender();
    public static WindowMisc misc = new WindowMisc();
    public static WindowCombat combat = new WindowCombat();
    public static WindowMovement movement = new WindowMovement();
    public static WindowHUD hud = new WindowHUD();
    public static WindowChat chat = new WindowChat();


    @Override
    public void initGui() {
        registeredWindows.forEach(w -> {
            switch (w.getType()) {
                case OPTION:
                    w.drag(3000, 3000);
                    break;
                case MODULE:
                    try {
                        int[] lol = AdorufuMod.DATA_MANAGER.getSavedGuiPos(w);
                        w.dragX = lol[0];
                        w.dragY = lol[1];
                    } catch (Exception e) {
                        AdorufuMod.logErr(true, "Couldn't load ClickGUI positions");
                    }
                    break;
            }
        });
    }

    @Override
    public void onGuiClosed() {
        registeredWindows.forEach(w -> {
            AdorufuMod.scheduler.schedule(() -> {
                try {
                    AdorufuMod.DATA_MANAGER.saveGuiPos(w);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, TimeUnit.NANOSECONDS);
            if (w.getType() == WindowType.OPTION) {
                w.setShown(false);
                w.drag(3000, 3000);
            }
        });
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        try {
            registeredWindows.forEach(w -> w.mouseClicked(x, y, b));
            super.mouseClicked(x, y, b);
        } catch (Exception ex) {/* ignore this */}
    }

    @Override
    public void mouseReleased(int x, int y, int state) {
        try {
            registeredWindows.forEach(w -> w.mouseReleased(x, y, state));
            super.mouseReleased(x, y, state);
        } catch (Exception e) {
            //
        }
    }

    public void drawScreen(int x, int y, float ticks) {
        drawRect(0, 0, width, height, Integer.MIN_VALUE);
        registeredWindows.forEach(w -> w.drawWindow(x, y));
        super.drawScreen(x, y, ticks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void focusWindow(AdorufuWindow window) {
        if (registeredWindows.contains(window)) {
            registeredWindows.remove(window);
            registeredWindows.add(window);
        }
    }

    public static ArrayList<AdorufuWindow> getRegisteredWindows() {
        return registeredWindows;
    }
}

