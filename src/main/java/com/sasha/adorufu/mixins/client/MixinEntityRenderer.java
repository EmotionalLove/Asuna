package com.sasha.adorufu.mixins.client;

import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.modules.ModuleTracers;
import com.sasha.adorufu.module.modules.ModuleWaypoints;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 09/08/2018 at 7:37 PM
 **/
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {


    /**
     * @author Sasha Stevens
     * @reason Nameplates
     */
    @Overwrite
    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        float scale = 0.025f;
        if (ModuleManager.moduleRegistry.get(2).isEnabled()) {
            isSneaking = false;
            double distance = Math.sqrt(x * x + y * y + z * z);
            if (distance > 10) {
                scale *= distance / 10;
            }
        }
        GlStateManager.scale(-scale, -scale, -scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        if (!isSneaking)
        {
            GlStateManager.disableDepth();
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        if (!isSneaking)
        {
            fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, ModuleManager.moduleRegistry.get(2).isEnabled() ? 0xFFFFFF : 553648127);
            GlStateManager.enableDepth();
        }

        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, isSneaking ? 553648127 : -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GlStateManager;matrixMode(I)V", ordinal = 4))
    public void renderWorldPass$0(int pass, float partialTicks, long finishTimeNano, CallbackInfo info){
        if (ModuleManager.moduleRegistry.get(1).isEnabled()) {
            GL11.glPushAttrib(1048575);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(-1.0f, -1.0f);
            GL11.glLineWidth(1.0f);
            GL11.glPolygonMode(1032, 6913);
        }
    }
    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GlStateManager;shadeModel(I)V", ordinal = 1))
    public void renderWorldPass$1(int pass, float partialTicks, long finishTimeNano, CallbackInfo info){
        if (ModuleManager.moduleRegistry.get(1).isEnabled()) {
            GL11.glPopAttrib();
        }
    }
    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand(FI)V"))
    public void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo info){
        ModuleManager.renderModules();
        GL11.glColor4f(0f,0f,0f,0f);
    }
    @Inject(method = "applyBobbing", at = @At("HEAD"), cancellable = true)
    public void applyBobbing(float partialTicks, CallbackInfo info){
        if (ModuleTracers.i > 0 || ModuleWaypoints.i > 0){
            info.cancel();
            //todo make tracers bob against camera so that we dont need to do this
        }
    }
}
