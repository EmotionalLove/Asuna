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

import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiModuleButton;
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiWindow;
import com.sasha.adorufu.mod.gui.clickgui.elements.IAdorufuGuiElement;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class AdorufuClickGUI extends GuiScreen {
    private List<IAdorufuGuiElement> elementList = new ArrayList<>();

    public AdorufuClickGUI() {
        ArrayList<IAdorufuGuiElement> elements = new ArrayList<>();
        elements.add(new AdorufuGuiModuleButton("Button", 0, 0, 100, 20, () -> System.exit(0)));
        elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 0f, 35f, 91f, 255f,  "Test", elements));
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        elementList.forEach(e -> e.drawElement(x, y));
    }
    @Override
    public void mouseClicked(int x, int y, int b) {
        elementList.forEach(e -> e.onMouseEngage(x, y, b));
    }
    @Override
    public void mouseReleased(int x, int y, int b) {
        elementList.forEach(e -> e.onMouseRelease(x,y,b));
    }

    /**
     * Calculates what the length of you list should be
     * @param i the amount of buttons that the window will have
     * @return the length value
     */
    public static int calcListLength(int i) {
        return 0; //todo
    }

}
