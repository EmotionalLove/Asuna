package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPlayerInventoryCloseEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha at 12:49 PM on 9/2/2018
 */
@Mixin(value = GuiContainer.class, priority = 999)
public class MixinGuiContainer extends MixinGuiScreen {

    @Shadow public Container inventorySlots;

    @Inject(method = "onGuiClosed", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;onContainerClosed(Lnet/minecraft/entity/player/EntityPlayer;)V"), cancellable = true)
    public void onContainerClosed(CallbackInfo info) {
        ClientPlayerInventoryCloseEvent event = new ClientPlayerInventoryCloseEvent(this.inventorySlots);
        AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
