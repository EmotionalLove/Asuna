package com.sasha.adorufu.mixins.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Created by Sasha at 1:06 PM on 9/2/2018
 */
@Mixin(value = EntityPlayer.class, priority = 999)
public class MixinEntityPlayer {
    @Shadow public Container openContainer;
}
