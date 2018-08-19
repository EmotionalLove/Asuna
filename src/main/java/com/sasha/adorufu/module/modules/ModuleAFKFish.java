package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;

/**
 * Created by Sasha on 11/08/2018 at 6:27 PM
 **/
@ModuleInfo(description = "Automated fishing, for when you're not at your computer.")
public class ModuleAFKFish extends AdorufuModule {
    public ModuleAFKFish(){
        super("AFKFish", AdorufuCategory.MISC, false);
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
            for (Entity e : AdorufuMod.minecraft.world.getLoadedEntityList()) {
                if (e instanceof EntityFishHook){
                    if (e.motionY >= 0.1 && e.motionY < 0.7){
                        new Thread(() -> {
                            AdorufuMod.minecraft.rightClickMouse();
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            AdorufuMod.minecraft.rightClickMouse();
                        }).start();
                    }
                }
            }
        }
    }
}
