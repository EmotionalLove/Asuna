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
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdorufuGuiWindow implements IAdorufuGuiElement {

    public static final int TITLEBAR_BOUND = 20;

    private String title;
    private float themeColourR = 255f;
    private float themeColourG = 255f;
    private float themeColourB = 255f;
    private float themeColourA = 5f;
    private List<IAdorufuGuiElement> moduleElements = new ArrayList<>();
    private int x;
    private int length;
    private int y;
    private int width;

    private boolean drag = false;
    private int offsetCursorX;
    private int offsetCursorY;

    public AdorufuGuiWindow(int x, int y, int length, int width, String title, List<IAdorufuGuiElement> moduleElements) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.title = title;
        this.moduleElements = moduleElements;
    }
    public AdorufuGuiWindow(int x, int y, int length, int width, int colour, String title, List<IAdorufuGuiElement> moduleElements) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.length = length;
        this.width = width;
        themeColourA = (float) (colour >> 24 & 0xFF) / 255F;
        themeColourR = (float) (colour >> 16 & 0xFF) / 255F;
        themeColourG = (float) (colour >> 8 & 0xFF) / 255F;
        themeColourB = (float) (colour & 0xFF) / 255F;
        this.moduleElements = moduleElements;
    }
    public AdorufuGuiWindow(int x, int y, int length, int width, float colourR, float colourG, float colourB, float colourA, String title, List<IAdorufuGuiElement> moduleElements) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.title = title;
        themeColourA = colourA;
        themeColourR = colourR;
        themeColourG = colourG;
        themeColourB = colourB;
        this.moduleElements = moduleElements;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void drawElement(int x, int y) {
        if (drag) {
            this.x = x - this.offsetCursorX;
            this.y = y - this.offsetCursorY;
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(8256);
        drawTitlebar();
        drawRestOfWindow();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        AtomicInteger c = new AtomicInteger();
        this.moduleElements.forEach(button -> {
            button.setX(this.x);
            button.setY(((this.y + TITLEBAR_BOUND) + (button.getHeight() * c.get())));
            c.getAndIncrement();
            button.drawElement(x, y);
        });
    }

    private void drawTitlebar() {
        AdorufuMath.drawRect(this.x, this.y,
                this.x + this.width,
                this.y + TITLEBAR_BOUND,
                themeColourR, themeColourG, themeColourB, themeColourA);
        AdorufuMod.FONT_MANAGER.segoe_36.drawCenteredString(this.title, (this.x + (this.width / 2)), this.y + 5, 0xffffff, true);
    }
    private void drawRestOfWindow() {
        AdorufuMath.drawRect(this.x, (this.y + TITLEBAR_BOUND), this.x + this.width, (this.y + TITLEBAR_BOUND) + this.length, Integer.MIN_VALUE);
    }

    @Override
    public boolean onMouseEngage(int x, int y, int b) {
        for (IAdorufuGuiElement moduleElement : this.moduleElements) {
            if (moduleElement.onMouseEngage(x,y,b)) {
                return true;
            }
        }
        if (b == 0) {
            if ((x >= this.x && x <= (this.x + this.width))
            &&
            y >= this.y && y <= (this.y + TITLEBAR_BOUND)){
                this.offsetCursorX = x - this.x;
                this.offsetCursorY = y - this.y;
                drag = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseRelease(int x, int y, int b) {
        for (IAdorufuGuiElement moduleElement : this.moduleElements) {
            if (moduleElement.onMouseRelease(x,y,b)) {
                return true;
            }
        }
        if (b == 0) {
            drag = false;
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

    @Override
    public int getHeight() {
        return length;
    }

}
