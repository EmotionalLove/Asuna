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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Render.class, priority = 999)
public abstract class MixinRender {

    @ModifyVariable(
            method = "renderLivingLabel",
            argsOnly = true,
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/renderer/EntityRenderer.drawNameplate(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;FFFIFFZZ)V"
            )
    )
    private String modifyRenderLivingLabelArgs(Entity entityIn, String str, double x, double y, double z, int maxDistance) {
        if ((entityIn instanceof EntityOtherPlayerMP) && Manager.Feature.isFeatureEnabled(NamePlatesFeature.class)) {
            return str + " " + NamePlatesFeature.formatHealthTag(((EntityOtherPlayerMP) entityIn).getHealth());
        }
        return str;
    }
}
