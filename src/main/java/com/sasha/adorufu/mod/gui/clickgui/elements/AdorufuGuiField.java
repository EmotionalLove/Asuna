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

public class AdorufuGuiField implements IAdorufuGuiElement {

    private String title;
    private String text;
    private boolean editing;
    private int cursorTicker = 0;
    private float highlightColourR = 255f;
    private float highlightColourG = 255f;
    private float highlightColourB = 255f;
    private float highlightColourA = 75f;
    private int x;
    private int y;
    private int width;
    private int height;

    @Override
    public void drawElement(int x, int y) {
        cursorTicker++;
        if (cursorTicker > 1000) {
            cursorTicker = 0;
        }
        AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString(this.title, (this.x + (this.width / 2)), this.y + (((this.y + this.height) - this.y) / 4), 0xffffff, true);
        if ((x >= this.x && x <= (this.x + this.width))
                &&
                y >= this.y && y <= (this.y + this.height)) {
            drawHighlight();
            return;
        }
        if (isEditing()) {
            drawEditing();
        }
    }

    @Override
    public boolean onMouseEngage(int x, int y, int b) {
        if (b == 0) {
            if ((x >= this.x && x <= (this.x + this.width))
                    &&
                    y >= this.y && y <= (this.y + this.height)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseRelease(int x, int y, int b) {
        if (b == 0) {
            if ((x >= this.x && x <= (this.x + this.width))
                    &&
                    y >= this.y && y <= (this.y + this.height)) {
                this.editing = true;
                return false;
            }
        }
        return false;
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

    @Override
    public int getHeight() {
        return 0;
    }
    private void drawHighlight() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + this.height,
                highlightColourR, highlightColourG, highlightColourB, highlightColourA);
    }

    private void drawEditing() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + this.height,
                145f, 255f, 158f, 50f);
        if (cursorTicker > 500) {
            AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString("_", (this.x + (this.width / 2)) + AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(text) , this.y + (((this.y + this.height) - this.y) / 4), 0xfffff);
        }
    }

    public boolean isEditing() {
        return editing;
    }
}
