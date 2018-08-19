package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ModuleInfo(description = "Press the spacebar to go faster")
public class ModuleElytraBoost extends AdorufuModule {

    static float limit = 2.5f;

    public ModuleElytraBoost() {
        super("ElytraBoost", AdorufuCategory.MOVEMENT, false);
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
            //this updates + displays flight speed
            if (AdorufuMod.minecraft.player.isElytraFlying()) {
                double speed = Math.abs(AdorufuMod.minecraft.player.motionX) + Math.abs(AdorufuMod.minecraft.player.motionZ);
                this.setSuffix(round(speed,2) + "");
            } else {
                this.setSuffix("N/A");
            }

            //actual code with limit (flightLimit)
            if (AdorufuMod.minecraft.player.isElytraFlying() && AdorufuMod.minecraft.gameSettings.keyBindJump.isKeyDown()) {
                float f1 = AdorufuMod.minecraft.player.rotationYaw * 0.017453292F;
                if ((Math.abs(AdorufuMod.minecraft.player.motionX) + Math.abs(AdorufuMod.minecraft.player.motionZ)) < limit) {
                    AdorufuMod.minecraft.player.motionX -= (double)(MathHelper.sin(f1) * 0.15f);
                    AdorufuMod.minecraft.player.motionZ += (double)(MathHelper.cos(f1) * 0.15f);
                }
            }
        }
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
