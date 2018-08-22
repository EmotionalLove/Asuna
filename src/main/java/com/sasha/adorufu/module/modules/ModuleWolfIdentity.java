package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;

@ModuleInfo(description = "Show who tamed a wolf")
public class ModuleWolfIdentity extends AdorufuModule {

    public ModuleWolfIdentity() {
        super("WolfIdentity", AdorufuCategory.RENDER, false);
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
                        tameableEntity.setCustomNameTag(tameableEntity.getOwnerId().toString());
                        EntityRenderer.drawNameplate(AdorufuMod.minecraft.fontRenderer, tameableEntity.getOwnerId().toString(),
                                (float)tameableEntity.posX,
                                (float)tameableEntity.posY + 1.5f,
                                (float)tameableEntity.posZ,
                                0,
                                AdorufuMod.minecraft.player.rotationYaw,
                                AdorufuMod.minecraft.player.rotationPitch,
                                false,
                                false
                                );
                    }
                }
            }
        }
    }
}
