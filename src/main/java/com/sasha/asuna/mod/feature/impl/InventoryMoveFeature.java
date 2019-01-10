package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.input.Keyboard;

public class InventoryMoveFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public InventoryMoveFeature() {
        super("InventoryMove", AsunaCategory.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (AsunaMod.minecraft.currentScreen instanceof GuiInventory) {
            AsunaMod.minecraft.player.movementInput.moveStrafe = 0.0F;
            AsunaMod.minecraft.player.movementInput.moveForward = 0.0F;
            if (Keyboard.isKeyDown(AsunaMod.minecraft.gameSettings.keyBindForward.getKeyCode())) {
                ++AsunaMod.minecraft.player.movementInput.moveForward;
            }
            if (Keyboard.isKeyDown(AsunaMod.minecraft.gameSettings.keyBindBack.getKeyCode())) {
                --AsunaMod.minecraft.player.movementInput.moveForward;
            }
            if (Keyboard.isKeyDown(AsunaMod.minecraft.gameSettings.keyBindLeft.getKeyCode())) {
                ++AsunaMod.minecraft.player.movementInput.moveStrafe;
            }
            if (Keyboard.isKeyDown(AsunaMod.minecraft.gameSettings.keyBindRight.getKeyCode())) {
                --AsunaMod.minecraft.player.movementInput.moveStrafe;
            }
        }
    }
}
