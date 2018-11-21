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

package com.sasha.asuna.mod.gui.clickgui.helper;

@Deprecated
public class DragHelper {

    public boolean dragging;
    protected int x;
    protected int offsetX;
    protected int y;
    protected int offsetY;

    public DragHelper(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void beginDragging(int cursorX, int cursorY) {
        this.dragging = true;
        this.offsetX = cursorX - this.x;
        this.offsetY = cursorY - this.y;
    }

    public void stopDragging() {
        this.dragging = false;
    }

    public int[] drawCallback() {
        int[] coords = new int[2];
        coords[0] = this.x - this.offsetX;
        coords[1] = this.y - this.offsetY;
        return coords;
    }

}
