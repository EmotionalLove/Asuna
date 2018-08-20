package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.network.play.client.CPacketPlayer;

public class ModulePacketFly extends AdorufuModule{
    public ModulePacketFly() {
        super("PacketFly", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if(!this.isEnabled())
            return;
        double[] dir = moveLooking(0);
        double xDir = dir[0];
        double zDir = dir[1];
        AdorufuMod.minecraft.player.motionX = 0;
        AdorufuMod.minecraft.player.motionY = 0;
        AdorufuMod.minecraft.player.motionZ = 0;
        AdorufuMod.minecraft.player.connection.sendPacket(
                new CPacketPlayer.PositionRotation
                (AdorufuMod.minecraft.player.posX + AdorufuMod.minecraft.player.motionX + (AdorufuMod.minecraft.gameSettings.keyBindForward.isKeyDown() ? 0.0624 : 0) - (AdorufuMod.minecraft.gameSettings.keyBindBack.isKeyDown() ? 0.0624 : 0)
                 , AdorufuMod.minecraft.player.posY + (AdorufuMod.minecraft.gameSettings.keyBindJump.isKeyDown() ? 0.0624 : 0) - (AdorufuMod.minecraft.gameSettings.keyBindSneak.isKeyDown() ? 0.0624 : 0),
                 AdorufuMod.minecraft.player.posZ + AdorufuMod.minecraft.player.motionZ + (AdorufuMod.minecraft.gameSettings.keyBindRight.isKeyDown() ? 0.0624 : 0) - (AdorufuMod.minecraft.gameSettings.keyBindLeft.isKeyDown() ? 0.0624 : 0)
                        , AdorufuMod.minecraft.player.rotationYaw, AdorufuMod.minecraft.player.rotationPitch, false));
        AdorufuMod.minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(AdorufuMod.minecraft.player.posX + AdorufuMod.minecraft.player.motionX, AdorufuMod.minecraft.player.posY - 42069, AdorufuMod.minecraft.player.posZ + AdorufuMod.minecraft.player.motionZ, AdorufuMod.minecraft.player.rotationYaw , AdorufuMod.minecraft.player.rotationPitch, true));
    }

    private static double[] moveLooking(int ignored) {
        return new double[] {AdorufuMod.minecraft.player.rotationYaw * 360 / 360 * 180 / 180, ignored};
    }
}
