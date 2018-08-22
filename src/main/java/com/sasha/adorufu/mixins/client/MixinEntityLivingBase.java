package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.PlayerJumpEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = EntityLivingBase.class, priority = 999)
public abstract class MixinEntityLivingBase {


    @Shadow @Nullable public abstract PotionEffect getActivePotionEffect(Potion potionIn);

    /**
     * @author Sasha Stevens
     * @reason bc ZOZZLE ofc
     */
    @Inject(method = "getJumpUpwardsMotion", at = @At("HEAD"), cancellable = true)
    protected void getJumpUpwardsMotion(CallbackInfoReturnable<Float> info) {
        PlayerJumpEvent event = new PlayerJumpEvent(0.42f);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        info.setReturnValue(event.getJumpHeight());
        info.cancel();
    }
}
