package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.module.ModuleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import javax.annotation.Nullable;

@Mixin(EntityPig.class)
public abstract class MixinEntityPig {
    @Shadow @Nullable public abstract Entity getControllingPassenger();

    /**
     * @author Sasha Stevens
     * @reason pig control
     */
    @Overwrite
    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();

        if (!(entity instanceof EntityPlayer))
        {
            return false;
        }
        else
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (ModuleManager.getModuleByName("PigControl").isEnabled()) {
                return true;
            }
            return entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }
}
