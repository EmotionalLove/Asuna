package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

import static com.sasha.xdolf.module.modules.ModuleKillaura.rotateTowardsEntity;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@ModuleInfo(description = "Automatically hit nearby crystals")
public class ModuleCrystalAura extends XdolfModule {
    public ModuleCrystalAura() {
        super("CrystalAura", XdolfCategory.COMBAT, false);
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
            for (Entity e : XdolfMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    //float attacktime = XdolfMod.minecraft.player.getCooledAttackStrength(1);
                    if (XdolfMod.minecraft.player.getDistance(e) <= 3.8f/* && XdolfMod.minecraft.player.canEntityBeSeen(e)*/) {
                        if(Mouse.isButtonDown(1) && XdolfMod.minecraft.player.inventory.getCurrentItem().getItem() != Items.END_CRYSTAL){
                            return; //dont fuggin do anythint
                        }
                        if (e.isEntityAlive()) {
                            float yaw =  XdolfMod.minecraft.player.rotationYaw;
                            float pitch =  XdolfMod.minecraft.player.rotationPitch;
                            float yawh =  XdolfMod.minecraft.player.rotationYawHead;
                            boolean wasSprinting = XdolfMod.minecraft.player.isSprinting();
                            rotateTowardsEntity(e);
                            XdolfMod.minecraft.player.setSprinting(false);
                            XdolfMod.minecraft.playerController.attackEntity(XdolfMod.minecraft.player, e);
                            XdolfMod.minecraft.player.swingArm(EnumHand.MAIN_HAND);
                            XdolfMod.minecraft.player.rotationYaw = yaw;
                            XdolfMod.minecraft.player.rotationPitch = pitch;
                            XdolfMod.minecraft.player.rotationYawHead = yawh;
                            if (wasSprinting) {
                                XdolfMod.minecraft.player.setSprinting(true);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
