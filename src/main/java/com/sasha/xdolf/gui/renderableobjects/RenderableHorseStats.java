package com.sasha.xdolf.gui.renderableobjects;


import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.RenderableObject;
import com.sasha.xdolf.gui.ScreenCornerPos;
import com.sasha.xdolf.gui.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.module.ModuleManager;
import net.minecraft.entity.passive.EntityHorse;

import java.io.IOException;

import static com.sasha.xdolf.XdolfMath.dround;
import static com.sasha.xdolf.XdolfMod.mc;

public class RenderableHorseStats extends RenderableObject {
    public RenderableHorseStats() {
        super("HorseStats", ScreenCornerPos.RIGHTTOP);
        try {
            this.setPos(XdolfMod.DATA_MANAGER.getHudPositionState(this));
        } catch (IOException e) {
            e.printStackTrace();
            this.setPos(this.getDefaultPos());
        }
    }

    @Override
    public void renderObjectLT(int yyy) {
        if (ModuleManager.getModuleByName("HorseStats").isEnabled()) {
            String s = "\247" + "3Horse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
            if (mc.player.isRidingHorse() && mc.player.getRidingEntity() instanceof EntityHorse) {
                EntityHorse e = ((EntityHorse) mc.player.getRidingEntity());
                Fonts.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), 4, yyy, 0xffffff);
                return;
            }
            Fonts.segoe_36.drawStringWithShadow("\247" + "3Horse stats: " + "\247" + "4None.", 4, yyy, 0xffffff);
        }
    }
    @Override
    public void renderObjectLB(int yyy) {
        if (ModuleManager.getModuleByName("HorseStats").isEnabled()) {
            String s = "\247" + "3Horse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
            if (mc.player.isRidingHorse() && mc.player.getRidingEntity() instanceof EntityHorse) {
                EntityHorse e = ((EntityHorse) mc.player.getRidingEntity());
                Fonts.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), 4, yyy, 0xffffff);
                return;
            }
            Fonts.segoe_36.drawStringWithShadow("\247" + "3Horse stats: " + "\247" + "4None.", 4, yyy, 0xffffff);
        }
    }
    @Override
    public void renderObjectRT(int yyy) {
        if (ModuleManager.getModuleByName("HorseStats").isEnabled()) {
            String s = "\247" + "3Horse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
            if (mc.player.isRidingHorse() && mc.player.getRidingEntity() instanceof EntityHorse) {
                EntityHorse e = ((EntityHorse) mc.player.getRidingEntity());
                Fonts.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3))) - 2), yyy, 0xffffff);
                return;
            }
            Fonts.segoe_36.drawStringWithShadow("\247" + "3Horse stats: " + "\247" + "4None.", (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth("Horse stats: None.")) - 2, yyy, 0xffffff);
        }
    }
    @Override
    public void renderObjectRB(int yyy) {
        if (ModuleManager.getModuleByName("HorseStats").isEnabled()) {
            String s = "\247" + "3Horse Stats: " + "\247" + "7Jump Height: " + "\247" + "f@JH" + "\247" + "7 Health: " + "\247" + "f@<3" + "\247" + "7 Max Speed: " + "\247" + "f@S";
            if (mc.player.isRidingHorse() && mc.player.getRidingEntity() instanceof EntityHorse) {
                EntityHorse e = ((EntityHorse) mc.player.getRidingEntity());
                Fonts.segoe_36.drawStringWithShadow(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3)), (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth(s.replace("@JH", "" + dround(e.getHorseJumpStrength(), 2)).replace("@<3", "" + e.getMaxHealth() / 2).replace("@S", "" + dround((e.getAIMoveSpeed() * 20) * 3, 3))) - 2), yyy, 0xffffff);
                return;
            }
            Fonts.segoe_36.drawStringWithShadow("\247" + "3Horse stats: " + "\247" + "4None.", (XdolfHUD.sWidth - Fonts.segoe_36.getStringWidth("Horse stats: None.")) - 2, yyy, 0xffffff);
        }
    }
}
