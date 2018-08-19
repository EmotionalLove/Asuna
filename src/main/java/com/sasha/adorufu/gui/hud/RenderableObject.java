package com.sasha.adorufu.gui.hud;


import javax.annotation.Nullable;

public class RenderableObject {

    private String name;
    private ScreenCornerPos pos = null;
    private ScreenCornerPos defaultPos;

    private static int LT_x = 12;


    public RenderableObject(String name, ScreenCornerPos defaultPos){
        this.name = name;
        this.defaultPos = defaultPos;
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
            return;
        }
        this.pos = getPosEnum(pos);
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
    }
    public void setPos(ScreenCornerPos pos) {
        this.pos = pos;
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
