package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.PlayerBlockBreakEvent;
import com.sasha.adorufu.misc.AdorufuRender;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModuleGhostBlockWarning extends AdorufuModule {

    private static LinkedHashMap<Coordinate, Boolean> CasterlyIsMyBoyfriendMap = new LinkedHashMap<>();

    public ModuleGhostBlockWarning() {
        super("GhostBlockWarning", AdorufuCategory.RENDER, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

    }
    @Override
    public void onRender(){
        if (!this.isEnabled()){
            return;
        }
        for (HashMap.Entry<Coordinate, Boolean> wow : CasterlyIsMyBoyfriendMap.entrySet()){
            if(!wow.getValue()){
                AdorufuRender.storageESP(wow.getKey().getX(), wow.getKey().getY(), wow.getKey().getZ(), 1.0f, 0.0f, 0.0f);
            }
        }
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e) {
        CasterlyIsMyBoyfriendMap.put(new Coordinate(e.getBlockPos().x, e.getBlockPos().y, e.getBlockPos().z, AdorufuMod.minecraft.player.dimension), false);
    }
    private static boolean between(int i, int coord, double min, double max) {
        if (coord < 0) {
            coord--;
        }
        return i >= (coord - min) && i <= (coord + max);
    }
}
class Coordinate {
    private int x,y,z,dim;

    public Coordinate(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    public int getDim() {
        return dim;
    }
}
