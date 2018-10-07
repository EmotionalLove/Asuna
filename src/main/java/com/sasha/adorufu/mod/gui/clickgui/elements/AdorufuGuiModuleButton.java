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
import com.sasha.adorufu.mod.gui.clickgui.ModuleToggler;
import com.sasha.adorufu.mod.misc.AdorufuMath;

public class AdorufuGuiModuleButton implements IAdorufuGuiElement {

    private String title;
    private float highlightColourR = 255f;
    private float highlightColourG = 255f;
    private float highlightColourB = 255f;
    private float highlightColourA = 75f;
    private int x;
    private int y;
    private int width;
    private int height;
    private ModuleToggler buttonAction;

    public AdorufuGuiModuleButton(String title, int x, int y, int width, int height, ModuleToggler buttonAction) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonAction = buttonAction;
    }

    @Override
    public void drawElement(int x, int y) {
        AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString(this.title, (this.x + (this.width / 2)), this.y + (((this.y + this.height) - this.y) / 4), 0xffffff, true);
        if ((x >= this.x && x <= (this.x + this.width))
                &&
                y >= this.y && y <= (this.y + this.height)) {
            drawHighlight();
            return;
        }
        if (buttonAction.isToggled()) {
            drawEnabled();
        }
    }

    private void drawHighlight() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + this.height,
                highlightColourR, highlightColourG, highlightColourB, highlightColourA);
    }
    private void drawEnabled() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + this.height,
                145f, 255f, 158f, 50f);
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
                this.buttonAction.run();
                return false;
            }
        }
        return false;
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
