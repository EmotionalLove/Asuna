/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.mixin.client;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientPlayerInventoryCloseEvent;
import com.sasha.asuna.mod.misc.GlobalGuiButton;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha at 12:49 PM on 9/2/2018
 */
@Mixin(value = GuiContainer.class, priority = 999)
public abstract class MixinGuiContainer extends MixinGuiScreen {

    @Shadow public Container inventorySlots;

    @Shadow protected abstract void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type);

    private GlobalGuiButton storeButton;
    private GlobalGuiButton stealButton;

    @Inject(method = "initGui", at = @At("TAIL"), cancellable = true)
    public void initGui(CallbackInfo info) {
        if (!((GuiContainer)(Object)this instanceof GuiChest)) {
            info.cancel();
            return;
        }
        GuiChest chest = (GuiChest) (Object)this;
        storeButton = new GlobalGuiButton("Store",69, 50, 75,40,20, "Store", btn -> {
            new Thread(() -> {
                for(int i = 0; i < (chest.inventoryRows * 9) + 44; i++) {
                    Slot s = chest.inventorySlots.getSlot(i);
                    if (s.getStack().getItem() != Items.AIR) {
                        try {
                            Thread.sleep(250L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handleMouseClick(s, i, 0, ClickType.QUICK_MOVE);
                    }
                }
            }).start();
        });
        stealButton = new GlobalGuiButton("Steal", 70, 50, 50, 40, 20, "Steal", btn -> {
            new Thread(() -> {
                for(int i = 0; i < chest.inventoryRows * 9; i++) {
                    Slot s = chest.inventorySlots.getSlot(i);
                    if (s.getStack().getItem() != Items.AIR) {
                        try {
                            Thread.sleep(250L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handleMouseClick(s, i, 0, ClickType.QUICK_MOVE);
                    }
                }
            }).start();

        });
        AsunaMod.globalGuiButtonManager.globalButtons.add(storeButton);
        AsunaMod.globalGuiButtonManager.globalButtons.add(stealButton);
    }

    @Inject(method = "onGuiClosed", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;onContainerClosed(Lnet/minecraft/entity/player/EntityPlayer;)V"), cancellable = true)
    public void onContainerClosed(CallbackInfo info) {
        ClientPlayerInventoryCloseEvent event = new ClientPlayerInventoryCloseEvent(this.inventorySlots);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }




    @Inject(method = "onGuiClosed", at = @At("HEAD"))
    public void onGuiClosed(CallbackInfo info) {
        AsunaMod.globalGuiButtonManager.globalButtons.clear();
    }
}
