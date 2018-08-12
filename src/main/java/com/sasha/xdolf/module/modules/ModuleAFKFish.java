package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;

/**
 * Created by Sasha on 11/08/2018 at 6:27 PM
 **/
@ModuleInfo(description = "Automated fishing, for when you're not at your computer.")
public class ModuleAFKFish extends XdolfModule {
    public ModuleAFKFish(){
        super("AFKFish", XdolfCategory.MISC, false);
    }
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            for (Entity e : XdolfMod.minecraft.world.getLoadedEntityList()) {
                if (e instanceof EntityFishHook){
                    if (e.motionY >= 0.1 && e.motionY < 0.7){
                        new Thread(() -> {
                            XdolfMod.minecraft.rightClickMouse();
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            XdolfMod.minecraft.rightClickMouse();
                        }).start();
                    }
                }
            }
        }
    }
}
