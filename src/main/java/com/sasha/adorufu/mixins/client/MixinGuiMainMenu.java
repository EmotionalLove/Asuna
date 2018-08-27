package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin;
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
