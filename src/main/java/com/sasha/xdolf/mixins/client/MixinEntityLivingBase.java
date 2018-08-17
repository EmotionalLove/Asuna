package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.events.PlayerJumpEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {


    @Shadow @Nullable public abstract PotionEffect getActivePotionEffect(Potion potionIn);

    /**
     * @author Sasha Stevens
     * @reason bc ZOZZLE ofc
     */
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    protected void jump(CallbackInfo info){
    }
}
