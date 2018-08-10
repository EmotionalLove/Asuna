package com.sasha.xdolf.mixins.client;

import com.mojang.authlib.properties.PropertyMap;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.io.IOException;

import static net.minecraft.client.Minecraft.getSystemTime;

/**
 * Created by Sasha on 10/08/2018 at 1:40 PM
 **/
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //@Inject(method = "runTickKeyboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V", ordinal = 2), cancellable = true)
    @Shadow
    @Final
    public PropertyMap profileProperties;

    @Shadow
    public abstract void processKeyBinds();

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public boolean actionKeyF3;

    @Shadow
    public abstract void updateDebugProfilerName(int keyCount);

    @Shadow
    public abstract void displayInGameMenu();

    @Shadow
    public abstract boolean processKeyF3(int auxKey);

    @Shadow
    @Nullable
    public GuiScreen currentScreen;

    @Shadow
    public EntityRenderer entityRenderer;

    @Shadow
    public abstract void dispatchKeypresses();

    @Shadow
    public long debugCrashKeyPressTime;

    /**
     * @author Sasha Stevens
     */
    @Overwrite
    public void runTickKeyboard() throws IOException {
        {
            while (Keyboard.next()) {
                int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

                if (this.debugCrashKeyPressTime > 0L) {
                    if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                        throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                    }

                    if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                        this.debugCrashKeyPressTime = -1L;
                    }
                } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                    this.actionKeyF3 = true;
                    this.debugCrashKeyPressTime = getSystemTime();
                }

                this.dispatchKeypresses();

                if (this.currentScreen != null) {
                    this.currentScreen.handleKeyboardInput();
                }

                boolean flag = Keyboard.getEventKeyState();

                if (flag) {
                    if (i == 62 && this.entityRenderer != null) {
                        this.entityRenderer.switchUseShader();
                    }

                    boolean flag1 = false;

                    if (this.currentScreen == null) {
                        if (i == 1) {
                            this.displayInGameMenu();
                        }

                        flag1 = Keyboard.isKeyDown(61) && this.processKeyF3(i);
                        this.actionKeyF3 |= flag1;

                        if (i == 59) {
                            this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                        }
                    }

                    if (flag1) {
                        KeyBinding.setKeyBindState(i, false);
                    } else {
                        KeyBinding.setKeyBindState(i, true);
                        KeyBinding.onTick(i);
                    }

                    if (this.gameSettings.showDebugProfilerChart) {
                        if (i == 11) {
                            this.updateDebugProfilerName(0);
                        }

                        for (int j = 0; j < 9; ++j) {
                            if (i == 2 + j) {
                                this.updateDebugProfilerName(j + 1);
                            }
                        }
                    }
                } else {
                    KeyBinding.setKeyBindState(i, false);
                    ModuleManager.moduleRegistry.stream().filter(m -> m.getKeyBind()==i).forEach(XdolfModule::toggle);

                    if (i == 61) {
                        if (this.actionKeyF3) {
                            this.actionKeyF3 = false;
                        } else {
                            this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                            this.gameSettings.showDebugProfilerChart = this.gameSettings.showDebugInfo && GuiScreen.isShiftKeyDown();
                            this.gameSettings.showLagometer = this.gameSettings.showDebugInfo && GuiScreen.isAltKeyDown();
                        }
                    }
                }
                net.minecraftforge.fml.common.FMLCommonHandler.instance().fireKeyInput();
            }

            this.processKeyBinds();
        }
    }
}
