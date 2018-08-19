package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ModuleInfo(description = "Press the spacebar to go faster")
public class ModuleElytraBoost extends XdolfModule {

    static float limit = 2.5f;

    public ModuleElytraBoost() {
        super("ElytraBoost", XdolfCategory.MOVEMENT, false);
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
            if (XdolfMod.minecraft.player.isElytraFlying()) {
                double speed = Math.abs(XdolfMod.minecraft.player.motionX) + Math.abs(XdolfMod.minecraft.player.motionZ);
                this.setSuffix(round(speed,2) + "");
            } else {
                this.setSuffix("N/A");
            }

            //actual code with limit (flightLimit)
            if (XdolfMod.minecraft.player.isElytraFlying() && XdolfMod.minecraft.gameSettings.keyBindJump.isKeyDown()) {
                float f1 = XdolfMod.minecraft.player.rotationYaw * 0.017453292F;
                if ((Math.abs(XdolfMod.minecraft.player.motionX) + Math.abs(XdolfMod.minecraft.player.motionZ)) < limit) {
                    XdolfMod.minecraft.player.motionX -= (double)(MathHelper.sin(f1) * 0.15f);
                    XdolfMod.minecraft.player.motionZ += (double)(MathHelper.cos(f1) * 0.15f);
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
