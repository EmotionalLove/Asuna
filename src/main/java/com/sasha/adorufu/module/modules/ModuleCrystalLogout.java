package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@ModuleInfo(description = "Automatically disconnect when a crystal is near you")
public class ModuleCrystalLogout extends AdorufuModule {
    public ModuleCrystalLogout() {
        super("CrystalLogout", AdorufuCategory.COMBAT, false);
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
            for (Entity e : AdorufuMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal) {
                    //float attacktime = mc.player.getCooledAttackStrength(1);
                    if (AdorufuMod.minecraft.player.getDistance(e) <= 3.8f && !Mouse.isButtonDown(1)/* && mc.player.canEntityBeSeen(e)*/) {
                        if (e.isEntityAlive()) {
                            AdorufuMod.minecraft.player.connection.getNetworkManager().closeChannel(new TextComponentString("\2476Disconnected by Ender Crystal"));
                            this.toggle();
                            break;
                        }
                    }
                }
            }
        }
    }
}
