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

package com.sasha.adorufu.mod.gui.clickgui.elements;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.AdorufuMath;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdorufuGuiWindow implements IAdorufuGuiElement {

    private String title;
    private int themeColour = 0xffffff;
    private List<AdorufuGuiModuleButton> moduleElements = new ArrayList<>();
    private int x;
    private int length;
    private int y;
    private int width;

    public AdorufuGuiWindow(int x, int y, int length, int width, String title, List<AdorufuGuiModuleButton> moduleElements) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.moduleElements = moduleElements;
    }
    public AdorufuGuiWindow(int x, int y, int length, int width, int colour, String title, List<AdorufuGuiModuleButton> moduleElements) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.themeColour = colour;
        this.moduleElements = moduleElements;
    }

    private String getTitle() {
        return this.title;
    }

    @Override
    public void drawElement() {
        drawTitlebar();
        drawRestOfWindow();
    }

    private void drawTitlebar() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + 40,
                this.themeColour);
        AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString(this.title, (this.x + this.width) / 2, this.y, 0xffffff, false);
    }
    private void drawRestOfWindow() {
        AdorufuMath.drawRect(this.x, (this.y - 40), this.y + this.length, (this.y - 40) + this.width, Integer.MIN_VALUE);
        AtomicInteger c = new AtomicInteger();
        this.moduleElements.forEach(button -> {
            button.setX(this.x);
            button.setY(this.y + (15 * c.get()));
            c.getAndIncrement();
        });
    }

    @Override
    public void onMouseEngage(int x, int y, int b) {

    }

    @Override
    public void onMouseRelease(int x, int y, int b) {

    }


    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

}
