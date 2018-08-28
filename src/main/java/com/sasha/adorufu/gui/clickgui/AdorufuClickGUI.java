package com.sasha.adorufu.gui.clickgui;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.windows.*;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Some of the code in this class is based off of the code in Xdolf 3's GUI code, which was based off of Xdolf 2's code (which was licensed under MIT at the time)
 * <i>During Xdolf 3's phase, I was the only active developer on the project.</i>
 * <p>
 * Credit goes to the original Xdolf team for the pieces of code that was used in Adorufu's GUI system. Much of it
 * has been reworked, and, to be honest, it's due for a rewrite
 * >todo
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS",
 * WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public class AdorufuClickGUI extends GuiScreen {

    public static ArrayList<AdorufuWindow> windowList = new ArrayList<>();
    public static ArrayList<AdorufuWindow> unFocusedWindows = new ArrayList<>();


    public static WindowRender render = new WindowRender();
    public static WindowMisc misc = new WindowMisc();
    public static WindowCombat combat = new WindowCombat();
    public static WindowMovement movement = new WindowMovement();
    public static WindowHUD hud = new WindowHUD();
    public static WindowChat chat = new WindowChat();


    @Override
    public void initGui() {
        for (AdorufuWindow w : windowList) {
            if (w.getType() == WindowType.OPTION) {
                w.drag(3000, 3000);
            }
            if (w.getType() == WindowType.MODULE) {
                boolean isNew = false;
                try {
                    int[] lol = AdorufuMod.DATA_MANAGER.getSavedGuiPos(w);
                    w.dragX = lol[0];
                    w.dragY = lol[1];
                } catch (Exception e) {
                    AdorufuMod.logErr(true, "Couldn't load ClickGUI positions");
                }
            }
        }
    }


    public void onGuiClosed() {
        for (AdorufuWindow w : windowList) {
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
        }
    }

    public void mouseClicked(int x, int y, int b) throws IOException {
        try {
            for (AdorufuWindow w : windowList) {
                w.mouseClicked(x, y, b);
            }
            super.mouseClicked(x, y, b);
        } catch (Exception e) {
            //
        }
    }

    public void mouseReleased(int x, int y, int state) {
        try {
            for (AdorufuWindow w : windowList) {
                w.mouseReleased(x, y, state);
            }
            super.mouseReleased(x, y, state);
        } catch (Exception e) {
            //
        }
    }

    public void drawScreen(int x, int y, float ticks) {
        drawRect(0, 0, width, height, Integer.MIN_VALUE);
        for (AdorufuWindow w : windowList) {
            if (w.isShown()) {
                w.drawWindow(x, y);
            }
        }
        super.drawScreen(x, y, ticks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void focusWindow(AdorufuWindow window) {
        if (windowList.contains(window)) {
            windowList.remove(window);
            windowList.add(windowList.size(), window);
        }
    }

    public static ArrayList<AdorufuWindow> getWindowList() {
        return windowList;
    }
}

