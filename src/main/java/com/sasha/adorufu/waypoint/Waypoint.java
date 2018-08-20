package com.sasha.adorufu.waypoint;

import com.sasha.adorufu.AdorufuMod;

import java.io.Serializable;

public class Waypoint implements Serializable {

    /**
     * Must use objects cuz serialisation
     */
    private Integer x;
    private Integer y;
    private Integer z;
    private Integer dim;
    private Boolean doesRender;
    private String ip;
    private String name;

    public Waypoint(int x, int y, int z, boolean doesRender, String ip, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = AdorufuMod.minecraft.player.dimension;
        this.doesRender = doesRender;
        this.ip = ip;
        this.name = name;
    }
    public Waypoint(int x, int y, int z, int dim, boolean doesRender, String ip, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
        this.doesRender = doesRender;
        this.ip = ip;
        this.name = name;
    }

    public int[] getCoords() {
        int[] kek = {this.x, this.y, this.z};
        return kek;
    }
    public String getName() {
        return this.name;
    }

    public int getDim() {
        return dim;
    }

    public boolean isDoesRender() {
        return doesRender;
    }
    public void toggle(){
        doesRender=!doesRender;
    }
}
