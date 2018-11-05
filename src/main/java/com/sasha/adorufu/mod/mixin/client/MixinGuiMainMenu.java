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

package com.sasha.adorufu.mod.mixin.client;

import com.sasha.adorufu.mod.gui.remotedatafilegui.GuiCloudLogin;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha at 12:35 PM on 8/26/2018
 */


@Mixin(value = GuiMainMenu.class, priority = 999)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "initGui", at = @At(value = "RETURN"), cancellable = true)
    public void initGui(CallbackInfo info) {
        this.buttonList.add(new GuiButton(600, 5, 5, fontRenderer.getStringWidth("Adorufu Cloud") + 10, 20, "Adorufu Cloud"));
    }

    @Inject(method = "actionPerformed", at = @At(value = "HEAD"), cancellable = true)
    public void actionPerformed(GuiButton button, CallbackInfo info) {
        if (button.id == 600) {
            mc.displayGuiScreen(new GuiCloudLogin(new GuiMainMenu()));
        }
    }
}
