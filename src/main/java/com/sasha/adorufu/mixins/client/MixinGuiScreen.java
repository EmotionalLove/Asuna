package com.sasha.adorufu.mixins.client;

import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Created by Sasha at 12:59 PM on 9/2/2018
 */
@Mixin(value = GuiScreen.class, priority = 999)
public class MixinGuiScreen {


}
