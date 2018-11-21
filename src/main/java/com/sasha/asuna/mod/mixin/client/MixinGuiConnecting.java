/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

import com.sasha.asuna.mod.feature.impl.AutoReconnectFeature;
import com.sasha.asuna.mod.gui.GuiDisconnectedAuto;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiConnecting.class, priority = 999)
public class MixinGuiConnecting {

    @Shadow
    public boolean cancel;

    @Shadow
    @Final
    public GuiScreen previousGuiScreen;

    @Redirect(
            method = "connect",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/Minecraft.displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"
            )
    )
    private void onDisplayScreen(Minecraft mc, GuiScreen screen) {
        if (screen instanceof GuiDisconnected && Manager.Feature.isFeatureEnabled(AutoReconnectFeature.class)) {
            GuiDisconnected gui = (GuiDisconnected) screen;
            screen = new GuiDisconnectedAuto(this.previousGuiScreen, gui.reason, gui.message, AutoReconnectFeature.delay, AutoReconnectFeature.serverData);
        }
        mc.displayGuiScreen(screen);
    }
}
