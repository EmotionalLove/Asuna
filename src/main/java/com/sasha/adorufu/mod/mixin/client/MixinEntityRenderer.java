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

import com.sasha.adorufu.mod.feature.impl.*;
import com.sasha.adorufu.mod.misc.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 09/08/2018 at 7:37 PM
 **/
@Mixin(value = EntityRenderer.class, priority = 999)
public abstract class MixinEntityRenderer {

    @Shadow @Final public Minecraft mc;
    @Shadow @Final public int[] lightmapColors;
    @Shadow @Final public DynamicTexture lightmapTexture;
    @Shadow public boolean lightmapUpdateNeeded;

    @Inject(
            method = "drawNameplate",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/renderer/GlStateManager.scale(FFF)V"
            )
    )
    private static void onPreScale(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking, CallbackInfo ci) {
        if (Manager.Feature.isFeatureEnabled(NamePlatesFeature.class)) {
            float scale = 0.030f;
            double distance = Math.sqrt(x * x + y * y + z * z);
            if (distance > 5) {
                scale *= distance / 10;
            }
            GlStateManager.scale(-scale, -scale, -scale);
        }
    }

    @Redirect(
            method = "drawNameplate",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/renderer/GlStateManager.scale(FFF)V"
            )
    )
    private static void doScale(float x, float y, float z) {
        if (!Manager.Feature.isFeatureEnabled(NamePlatesFeature.class)) {
            GlStateManager.scale(x, y, z);
        }
    }

    @ModifyVariable(
            method = "drawNameplate",
            at = @At("HEAD"),
            index = 9
    )
    private static boolean forceNotSneaking(boolean isSneaking) {
        return !Manager.Feature.isFeatureEnabled(NamePlatesFeature.class) && isSneaking;
    }


    @Redirect(
            method = "drawNameplate",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/gui/FontRenderer.drawString(Ljava/lang/String;III)I"
            )
    )
    private static int onRenderVisibleNameplate(FontRenderer fr, String text, int x, int y, int color) {
        return fr.drawString(text, x, y, Manager.Feature.isFeatureEnabled(NamePlatesFeature.class) ? 0xFFFFFF : color);
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GlStateManager;matrixMode(I)V", ordinal = 4))
    public void renderWorldPass$0(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        if (Manager.Feature.isFeatureEnabled(WireframeFeature.class)) {
            GL11.glPushAttrib(1048575);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(-1.0f, -1.0f);
            GL11.glLineWidth(1.0f);
            GL11.glPolygonMode(1032, 6913);
        }
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GlStateManager;shadeModel(I)V", ordinal = 1))
    public void renderWorldPass$1(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        if (Manager.Feature.isFeatureEnabled(FreecamFeature.class)) {
            GL11.glPopAttrib();
        }
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V"))
    public void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        Manager.Feature.renderFeatures();
        GL11.glColor4f(0f, 0f, 0f, 0f);
    }

    @Inject(method = "applyBobbing", at = @At("HEAD"), cancellable = true)
    public void applyBobbing(float partialTicks, CallbackInfo info) {
        if (TracersFeature.i > 0 || WaypointsFeature.i > 0) {
            info.cancel();
            //todo make tracers bob against camera so that we dont need to do this
        }
    }

    @Redirect(
            method = "orientCamera",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/multiplayer/WorldClient.rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"
            )
    )
    private RayTraceResult onRayTrace(WorldClient world, Vec3d start, Vec3d end) {
        return Manager.Feature.isFeatureEnabled(CameraClipFeature.class) ? null : world.rayTraceBlocks(start, end);
    }

    @Inject(method = "updateLightmap", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;lightmapColors:[I"), cancellable = true)
    public void updateLightmap(float partialTicks, CallbackInfo info) {
        if (!Manager.Feature.isFeatureEnabled(NightVisionFeature.class)) return;
        for (int i = 0; i < 256; ++i) this.lightmapColors[i] = -16777216 | -20 << 16 | -20 << 8 | -20;
        this.lightmapTexture.updateDynamicTexture();
        this.lightmapUpdateNeeded = false;
        this.mc.profiler.endSection();
        info.cancel();
    }
}
