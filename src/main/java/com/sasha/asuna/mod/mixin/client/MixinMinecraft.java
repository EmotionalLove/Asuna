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

import com.mojang.authlib.properties.PropertyMap;
import com.sasha.asuna.api.AsunaPlugin;
import com.sasha.asuna.api.AsunaPluginLoader;
import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientGetMusicTypeEvent;
import com.sasha.asuna.mod.events.client.ClientMouseClickEvent;
import com.sasha.asuna.mod.events.client.ClientScreenChangedEvent;
import com.sasha.asuna.mod.events.playerclient.PlayerBlockPlaceEvent;
import com.sasha.asuna.mod.feature.impl.CPUControlFeature;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

/**
 * Created by Sasha on 10/08/2018 at 1:40 PM
 **/
@Mixin(value = Minecraft.class, priority = 999)
public abstract class MixinMinecraft {
    //@Inject(method = "runTickKeyboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V", ordinal = 2), cancellable = true)
    @Shadow
    @Final
    public PropertyMap profileProperties;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public boolean actionKeyF3;
    @Shadow
    @Nullable
    public GuiScreen currentScreen;
    @Shadow
    public EntityRenderer entityRenderer;
    @Shadow
    public long debugCrashKeyPressTime;
    @Shadow
    public RayTraceResult objectMouseOver;
    @Shadow
    public boolean inGameHasFocus;
    @Shadow
    public WorldClient world;
    @Shadow
    public int leftClickCounter;

    @Shadow
    public abstract void processKeyBinds();

    @Shadow
    public abstract void updateDebugProfilerName(int keyCount);

    @Shadow
    public abstract void displayInGameMenu();

    @Shadow
    public abstract boolean processKeyF3(int auxKey);

    @Shadow
    public abstract void dispatchKeypresses();

    @Inject(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingArm(Lnet/minecraft/util/EnumHand;)V"))
    public void rightClickMouse(CallbackInfo info) {
        PlayerBlockPlaceEvent event = new PlayerBlockPlaceEvent(AsunaMod.minecraft.player.itemStackMainHand);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
    }

    @Inject(
            method = "runTickKeyboard",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/settings/KeyBinding.setKeyBindState(IZ)V",
                    ordinal = 2
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void postSetKeyState(CallbackInfo ci, int key, boolean flag) {
        Manager.Feature.getTogglableFeatures().forEachRemaining(feature -> {
            if (feature.getKeycode() == key) {
                feature.toggleState();
            }
        });
    }

    @Inject(method = "middleClickMouse", at = @At("HEAD"), cancellable = true)
    public void middleClickMouse(CallbackInfo iinfo) {
        ClientMouseClickEvent.Middle event = new ClientMouseClickEvent.Middle();
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) iinfo.cancel();
    }

    @Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
    protected void rightClickMouse$0(CallbackInfo iinfo) {
        ClientMouseClickEvent.Right event = new ClientMouseClickEvent.Right();
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) iinfo.cancel();
    }

    @Inject(method = "getAmbientMusicType", at = @At("RETURN"), cancellable = true)
    public void getAmbientMusicType(CallbackInfoReturnable<MusicTicker.MusicType> info) {
        ClientGetMusicTypeEvent event = new ClientGetMusicTypeEvent(info.getReturnValue());
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        info.setReturnValue(event.getMusicType());
    }

    @Inject(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;create(Lorg/lwjgl/opengl/PixelFormat;)V"))
    public void createDisplay(CallbackInfo info) {
        Display.setTitle("Minecraft 1.12.2 with " + AsunaMod.NAME + " " + AsunaMod.VERSION);
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreen(GuiScreen screenIn, CallbackInfo info) {
        ClientScreenChangedEvent event = new ClientScreenChangedEvent(screenIn);
        AsunaMod.EVENT_MANAGER.invokeEvent(event);
        if (event.isCancelled()) {
            info.cancel();
        }
        screenIn = event.getScreen();
    }

    @Inject(
            method = "getLimitFramerate",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preGetLimitFramerate(CallbackInfoReturnable<Integer> cir) {
        if (!Display.isActive() && Manager.Feature.isFeatureEnabled(CPUControlFeature.class)) {
            cir.setReturnValue(1);
        }
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"), cancellable = true)
    public void shutdownMinecraftApplet(CallbackInfo info) {
        AsunaPluginLoader.getLoadedPlugins().forEach(AsunaPlugin::onDisable);
    }
}
