package com.sasha.xdolf.gui;

public class RenderableObject {

    private String name;
    private ScreenCornerPos pos;

    private static int LT_x = 12;

    public RenderableObject(String name, String pos) {
        this.name = name;
        this.pos = getPosEnum(pos);
    }

    // just in case i need it, better to use the above one instead though :///
    public RenderableObject(String name) {
        this.name = name;
    }

    /**
     * determines the code to use based on what the pos of the RenerableObject is set to
     */

    //TODO: FIGURE THIS OUT SO THAT RENDERS DONT COLLIDE WITH EACHOTHER edit : done (i think)
    // TODO: needs more testing
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
     * >be a trap, mentally
     * >has to write 4 different rendering voids for each corner of the screen
     * >also requires me to rewrite my entire HUD code and to develop a configuration system
     * >fml
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

    public String getName() {
        return this.name;
    }

    public void setPos(String pos) {
        this.pos = getPosEnum(pos);
    }

    private static ScreenCornerPos getPosEnum(String pos) {
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
}
