package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketRecieveEvent;
import com.sasha.adorufu.events.ClientPacketSendEvent;
import com.sasha.adorufu.events.ClientPushOutOfBlocksEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.world.GameType;

import static com.sasha.adorufu.AdorufuMod.logMsg;
import static com.sasha.adorufu.AdorufuMod.minecraft;

/**
 * Created by Sasha on 12/08/2018 at 9:12 AM
 **/
@ModuleInfo(description = "Client-sided spectator mode.") //todo fix the fact that you cant fly thru stuff
public class ModuleFreecam extends AdorufuModule implements SimpleListener {

    public static double oldX, oldY, oldZ;
    public static GameType oldGameType;

    public ModuleFreecam() {
        super("Freecam", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        oldX=AdorufuMod.minecraft.player.posX;
        oldY=AdorufuMod.minecraft.player.posY;
        oldZ=AdorufuMod.minecraft.player.posZ;
        oldGameType = AdorufuMod.minecraft.playerController.currentGameType;
        AdorufuMod.minecraft.playerController.setGameType(GameType.SPECTATOR);
        AdorufuMod.minecraft.player.setGameType(GameType.SPECTATOR);
        AdorufuMod.minecraft.player.noClip = true;
    }

    @Override
    public void onDisable() {
        AdorufuMod.minecraft.player.setLocationAndAngles(oldX, oldY, oldZ, AdorufuMod.minecraft.player.rotationYaw, AdorufuMod.minecraft.player.rotationPitch);
        AdorufuMod.minecraft.playerController.setGameType(oldGameType);
        AdorufuMod.minecraft.player.setGameType(oldGameType);
        AdorufuMod.minecraft.player.noClip = false;
    }

    @Override
    public void onTick() {
        if (minecraft.world == null) this.toggle();
        AdorufuMod.minecraft.player.noClip = true;
    }
    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e){
        if (this.isEnabled()) {
            if (e.getRecievedPacket() instanceof SPacketTimeUpdate) {
                return;
            }
            if (e.getRecievedPacket() instanceof CPacketKeepAlive) {
                return;
            }
            if (e.getRecievedPacket() instanceof SPacketChat) {
                return;
            }
            e.setCancelled(true);
        }
    }
    @SimpleEventHandler
    public void onPacketTx(ClientPacketSendEvent e){
        if (this.isEnabled()){
            if (minecraft.world == null) this.toggle();
            if(e.getSendPacket() instanceof CPacketPlayer || e.getSendPacket() instanceof CPacketInput) {
                e.setCancelled(true);
            }
        }
    }
    @SimpleEventHandler
    public void onPushoutofblocks(ClientPushOutOfBlocksEvent e) {
        if (minecraft.world == null && this.isEnabled()) this.toggle();
        if (this.isEnabled()) e.setCancelled(true);
    }
}
