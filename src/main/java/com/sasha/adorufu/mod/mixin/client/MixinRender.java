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

import com.sasha.adorufu.mod.feature.impl.NamePlatesFeature;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Render.class, priority = 999)
public abstract class MixinRender {

    @Shadow public abstract FontRenderer getFontRendererFromRenderManager();

    @Inject(
            method = "renderLivingLabel",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/renderer/EntityRenderer.drawNameplate(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;FFFIFFZZ)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void modifyLabelText(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo ci, double d0, boolean flag, float f, float f1, boolean flag1, float f2, int i) {
        if ((entityIn instanceof EntityOtherPlayerMP) && Manager.Feature.isFeatureEnabled(NamePlatesFeature.class)) {
            str += " " + NamePlatesFeature.formatHealthTag(((EntityOtherPlayerMP) entityIn).getHealth());
        }
        EntityRenderer.drawNameplate(this.getFontRendererFromRenderManager(), str, (float)x, (float)y + f2, (float)z, i, f, f1, flag1, flag);

        ci.cancel();
    }
}
