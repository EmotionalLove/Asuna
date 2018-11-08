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

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.impl.BookForgerFeature;
import com.sasha.adorufu.mod.misc.AdorufuBookNBTBuilder;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha at 3:51 PM on 9/16/2018
 */
@Mixin(value = GuiScreenBook.class, priority = 999)
public class MixinGuiScreenBook extends MixinGuiScreen {

    @Shadow
    @Final
    private ItemStack book;

    @Shadow
    @Final
    private EntityPlayer editingPlayer;

    @Shadow
    private String bookTitle;

    @Inject(
            method = "sendBookToServer",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/network/PacketBuffer"
            )
    )
    private void preCreateBuffer(boolean publish, CallbackInfo ci) {
        AdorufuBookNBTBuilder builder = new AdorufuBookNBTBuilder(this.book);
        boolean flag = Manager.Feature.isFeatureEnabled(BookForgerFeature.class);
        builder.setAuthor(flag ? BookForgerFeature.author : this.editingPlayer.getName());
        if (flag) AdorufuMod.logMsg(false, BookForgerFeature.author);
        builder.setTitle(this.bookTitle);
        builder.setSign(publish);
        builder.push();
        ci.cancel();
    }
}
