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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiIngameMenu.class, priority = 999)
public abstract class MixinGuiIngameMenu extends MixinGuiScreen {

    @Inject(method = "initGui", at = @At("RETURN"), cancellable = true)
    public void initGui(CallbackInfo info) {
        if (!this.mc.isIntegratedServerRunning()) info.cancel();
        this.buttonList.add(new GuiButton(666, this.width / 2 - 100, this.height / 4 + 144 + -16, "Reconnect"));
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    protected void actionPerformed(GuiButton button, CallbackInfo info) {
        if (button.id == 666) {
            ServerData data = this.mc.getCurrentServerData();
            this.mc.world.sendQuittingDisconnectingPacket();
            FMLClientHandler.instance().connectToServer(new GuiMainMenu(), data);

        }
    }
}
