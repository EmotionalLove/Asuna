package com.sasha.xdolf.mixins.client;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.events.PlayerXdolfCommandEvent;
import com.sasha.xdolf.module.ModuleManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Sasha on 08/08/2018 at 7:53 AM
 **/
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Shadow public MovementInput movementInput;

    @Shadow protected int sprintToggleTimer;

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String message, CallbackInfo info) {
        if (message.startsWith(XdolfCommand.commandDelimetre)) {
            XdolfMod.EVENT_MANAGER.invokeEvent(new PlayerXdolfCommandEvent(message));
            info.cancel();
        }
    }
    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo info) {
        ModuleManager.tickModules();
    }
    @Inject(method = "onLivingUpdate", at = @At("RETURN"), cancellable = true)
    public void onLivingUpdate(CallbackInfo info){
        if (ModuleManager.getModuleByName("NoSlow").isEnabled()) {
            this.movementInput.moveForward /= 0.2;
            this.movementInput.moveStrafe /= 0.2;
            this.sprintToggleTimer = 7;
        }
    }
}
