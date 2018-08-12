package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;

import static com.sasha.xdolf.module.modules.ModuleKillaura.rotateTowardsEntity;

/**
 * Created by Sasha on 12/08/2018 at 8:53 AM
 **/
@ModuleInfo(description = "Automatically disconnect when a crystal is near you")
public class ModuleCrystalLog extends XdolfModule {
    public ModuleCrystalLog() {
        super("CrystalLog", XdolfCategory.COMBAT, false);
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
                    //float attacktime = mc.player.getCooledAttackStrength(1);
                    if (XdolfMod.minecraft.player.getDistance(e) <= 3.8f && !Mouse.isButtonDown(1)/* && mc.player.canEntityBeSeen(e)*/) {
                        if (e.isEntityAlive()) {
                            XdolfMod.minecraft.player.connection.getNetworkManager().closeChannel(new TextComponentString("\2476Disconnected by Ender Crystal"));
                            this.toggle();
                            break;
                        }
                    }
                }
            }
        }
    }
}
