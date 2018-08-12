package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import com.sasha.xdolf.events.ClientPacketSendEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.GameType;

import static com.sasha.xdolf.XdolfMod.logMsg;

/**
 * Created by Sasha on 12/08/2018 at 9:12 AM
 **/
@ModuleInfo(description = "Allows you to fly through solid objects.")
public class ModuleFreecam extends XdolfModule implements SimpleListener {

    public static double oldX, oldY, oldZ;
    public static GameType oldGameType;


    /**
     * this module isn't working correctly idk way and idc rn to fix it because my irl can go fuck itself. long story...
     */
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
    }

    @Override
    public void onDisable() {
        XdolfMod.minecraft.player.setLocationAndAngles(oldX, oldY, oldZ, XdolfMod.minecraft.player.rotationYaw, XdolfMod.minecraft.player.rotationPitch);
        XdolfMod.minecraft.playerController.setGameType(oldGameType);
        XdolfMod.minecraft.player.setGameType(oldGameType);
    }

    @Override
    public void onTick() {

    }
    @SimpleEventHandler
    public void onPacketRx(ClientPacketRecieveEvent e){
        //logMsg("oof");
        if (this.isEnabled()) e.setCancelled(true);
    }
    @SimpleEventHandler
    public void onPacketTx(ClientPacketSendEvent e){
        if (this.isEnabled()){
            if (e.getSendPacket() instanceof CPacketPlayer.Position) {
                CPacketPlayer.Position pck = (CPacketPlayer.Position) e.getSendPacket();
                pck.x = oldX;
                pck.y = oldY;
                pck.z = oldZ;
                return;
            }
            if (e.getSendPacket() instanceof CPacketPlayer.PositionRotation) {
                CPacketPlayer.PositionRotation pck = (CPacketPlayer.PositionRotation) e.getSendPacket();
                pck.x = oldX;
                pck.y = oldY;
                pck.z = oldZ;
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
