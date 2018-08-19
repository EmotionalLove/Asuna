package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import com.sasha.xdolf.events.ClientPacketSendEvent;
import com.sasha.xdolf.events.ClientPushOutOfBlocksEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.world.GameType;

import static com.sasha.xdolf.XdolfMod.logMsg;

/**
 * Created by Sasha on 12/08/2018 at 9:12 AM
 **/
@ModuleInfo(description = "Client-sided spectator mode.") //todo fix the fact that you cant fly thru stuff
public class ModuleFreecam extends XdolfModule implements SimpleListener {

    public static double oldX, oldY, oldZ;
    public static GameType oldGameType;

    public ModuleFreecam() {
        super("Freecam", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        oldX=XdolfMod.minecraft.player.posX;
        oldY=XdolfMod.minecraft.player.posY;
        oldZ=XdolfMod.minecraft.player.posZ;
        oldGameType = XdolfMod.minecraft.playerController.currentGameType;
        XdolfMod.minecraft.playerController.setGameType(GameType.SPECTATOR);
        XdolfMod.minecraft.player.setGameType(GameType.SPECTATOR);
        XdolfMod.minecraft.player.noClip = true;
    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.player.setLocationAndAngles(oldX, oldY, oldZ, XdolfMod.minecraft.player.rotationYaw, XdolfMod.minecraft.player.rotationPitch);
        XdolfMod.minecraft.playerController.setGameType(oldGameType);
        XdolfMod.minecraft.player.setGameType(oldGameType);
        XdolfMod.minecraft.player.noClip = false;
    }

    @Override
    public void onTick() {
        XdolfMod.minecraft.player.noClip = true;
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
            if(e.getSendPacket() instanceof CPacketPlayer || e.getSendPacket() instanceof CPacketInput) {
                e.setCancelled(true);
            }
        }
    }
    @SimpleEventHandler
    public void onPushoutofblocks(ClientPushOutOfBlocksEvent e) {
        if (this.isEnabled()) e.setCancelled(true);
    }
}
