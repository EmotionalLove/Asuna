package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.misc.PlayerIdentity;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;

import java.util.HashMap;
import java.util.UUID;

@ModuleInfo(description = "Show who tamed a tameable entity")
public class ModuleWolfIdentity extends AdorufuModule {


    public ModuleWolfIdentity() {
        super("TamedIdentity", AdorufuCategory.RENDER, false);
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
            for (Entity entity : AdorufuMod.minecraft.world.getLoadedEntityList()) {
                if (entity instanceof EntityTameable) {
                    EntityTameable tameableEntity = (EntityTameable) entity;
                    if (tameableEntity.isTamed()) {
                        tameableEntity.setAlwaysRenderNameTag(true);
                        PlayerIdentity identity = AdorufuMod.DATA_MANAGER.getPlayerIdentity(tameableEntity.getOwnerId().toString());
                        tameableEntity.setCustomNameTag(identity.getDisplayName());
                    }
                }
                if (entity instanceof AbstractHorse) {
                    AbstractHorse tameableEntity = (AbstractHorse) entity;
                    if (tameableEntity.isTame()) {
                        tameableEntity.setAlwaysRenderNameTag(true);
                        PlayerIdentity identity = AdorufuMod.DATA_MANAGER.getPlayerIdentity(tameableEntity.getOwnerUniqueId().toString());
                        tameableEntity.setCustomNameTag(identity.getDisplayName());
                    }
                }
            }
        }
    }
}
