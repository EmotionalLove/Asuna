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

public class AdorufuGuiModuleButton implements IAdorufuGuiElement {

    private String title;
    private float highlightColourR = 255f;
    private float highlightColourG = 255f;
    private float highlightColourB = 255f;
    private float highlightColourA = 10f/255f;
    private int x;
    private int y;
    private int width;
    private int length;
    private Runnable buttonAction;

    public AdorufuGuiModuleButton(String title, int x, int y, int width, int length, Runnable buttonAction) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.buttonAction = buttonAction;
    }

    @Override
    public void drawElement(int x, int y) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString(this.title, (this.x + (this.width / 2)), this.y + 5, 0xffffff, true);
    }

    @Override
    public void onMouseEngage(int x, int y, int b) {

    }

    @Override
    public void onMouseRelease(int x, int y, int b) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }
}
