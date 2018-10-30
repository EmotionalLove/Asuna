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

package com.sasha.adorufu.mod.gui.hud;


import com.sasha.simplesettings.annotation.SerialiseSuper;
import com.sasha.simplesettings.annotation.Transiant;

import javax.annotation.Nullable;

@Deprecated
@SerialiseSuper
public class RenderableObject {

    @Transiant private String name;
    @Transiant private ScreenCornerPos pos = null;
    @Transiant private ScreenCornerPos defaultPos;
    private String stringPos;
    @Transiant private static int LT_x = 12;


    public RenderableObject(String name, ScreenCornerPos defaultPos){
        this.name = name;
        this.defaultPos = defaultPos;
        this.stringPos = getPosStr(defaultPos);
    }

    /**
     * Creates a new RenderableObject
     * @param name What is this RenderableObject called?
     * @param pos Which corner of the screen will it be on?
     * @param defaultPos What's the default position of this RO? (In case @param pos is null)
     */
    public RenderableObject(String name, @Nullable String pos, ScreenCornerPos defaultPos) {
        this.name = name;
        this.defaultPos = defaultPos;
        if (pos == null){
            this.pos = this.defaultPos;
            this.stringPos = getPosStr(this.pos);
            return;
        }
        this.pos = getPosEnum(pos);
        this.stringPos = getPosStr(this.pos);
    }

    // just in case i need it, better to use the above one instead though :///
    @Deprecated
    public RenderableObject(String name) {
        this.name = name;
    }

    /**
     * determines the code to use based on what the pos of the RenerableObject is set to
     */

    // x's are always the same, thankfully. no need to worry about that.
    public void renderTheObject(int pos) {
        if (this.pos == ScreenCornerPos.LEFTBOTTOM) {
            renderObjectLB(pos);
        }
        if (this.pos == ScreenCornerPos.LEFTTOP) {
            renderObjectLT(pos);
        }
        if (this.pos == ScreenCornerPos.RIGHTBOTTOM) {
            renderObjectRB(pos);
        }
        if (this.pos == ScreenCornerPos.RIGHTTOP) {
            renderObjectRT(pos);
        }
    }

    /**
     * >be me
     * >listening to llane's requests
     * >has to write 4 different rendering voids for each corner of the screen
     * >also requires me to rewrite my entire HUD code and to develop a configuration system
     * >fml
     *
     * tf is this - me 2018
     * this javadoc is gay af
     */
    public void renderObjectLT(int yyy) {

    }
    public void renderObjectLB(int yyy) {

    }
    public void renderObjectRT(int yyy) {

    }
    public void renderObjectRB(int yyy) {

    }

    public ScreenCornerPos getPos() {
        this.pos = getPosEnum(this.stringPos);
        return this.pos;
    }

    public ScreenCornerPos getDefaultPos() {
        return defaultPos;
    }

    public String getName() {
        return this.name;
    }

    public void setPos(String pos) {
        this.pos = getPosEnum(pos);
        this.stringPos = getPosStr(this.pos);
    }
    public void setPos(ScreenCornerPos pos) {
        this.pos = pos;
        this.stringPos = getPosStr(pos);
    }

    public static ScreenCornerPos getPosEnum(String pos) {
        if (pos.equalsIgnoreCase("LT")) {
            return ScreenCornerPos.LEFTTOP;
        }
        if (pos.equalsIgnoreCase("LB")) {
            return ScreenCornerPos.LEFTBOTTOM;
        }
        if (pos.equalsIgnoreCase("RT")) {
            return ScreenCornerPos.RIGHTTOP;
        }
        if (pos.equalsIgnoreCase("RB")) {
            return ScreenCornerPos.RIGHTBOTTOM;
        }
        return null;
    }
    public static String getPosStr(ScreenCornerPos pos) {
        if (pos == ScreenCornerPos.LEFTTOP) {
            return "LT";
        }
        if (pos == ScreenCornerPos.LEFTBOTTOM) {
            return "LB";
        }
        if (pos == ScreenCornerPos.RIGHTTOP) {
            return "RT";
        }
        if (pos == ScreenCornerPos.RIGHTBOTTOM) {
            return "RB";
        }
        return null;
    }
}
