/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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
import com.sasha.asuna.mod.misc.GlobalGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sasha at 12:59 PM on 9/2/2018
 */
@Mixin(value = GuiScreen.class, priority = 999)
public abstract class MixinGuiScreen {

    @Shadow public int height;
    @Shadow public int width;
    @Shadow public Minecraft mc;
    @Shadow protected List<GuiButton> buttonList;

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    protected void actionPerformed(GuiButton button, CallbackInfo info) throws IOException {
        if (button instanceof GlobalGuiButton) AsunaMod.globalGuiButtonManager.performAction((GlobalGuiButton) button);
    }
    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
        for (GlobalGuiButton globalButton : AsunaMod.globalGuiButtonManager.globalButtons) {
            globalButton.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo info) throws IOException {
        if (mouseButton == 0) {
            for (GlobalGuiButton globalButton : AsunaMod.globalGuiButtonManager.globalButtons) {
                if (globalButton.mousePressed(this.mc, mouseX, mouseY)) {
                    globalButton.playPressSound(this.mc.getSoundHandler());
                    AsunaMod.globalGuiButtonManager.performAction(globalButton);
                }
            }
        }
    }


}
