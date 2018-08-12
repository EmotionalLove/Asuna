package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketSendEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Created by Sasha on 10/08/2018 at 12:47 PM
 **/
@ModuleInfo(description = "Makes your hunger last longer while sprinting/jumping/etc")
public class ModuleAntiHunger extends XdolfModule implements SimpleListener {
    public ModuleAntiHunger() {
        super("AntiHunger", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled() && (!XdolfMod.minecraft.gameSettings.keyBindAttack.isPressed() || !XdolfMod.minecraft.gameSettings.keyBindAttack.isKeyDown())) {
            XdolfMod.minecraft.getConnection().sendPacket(new CPacketPlayer(false));
        }
    }
    @SimpleEventHandler
    public void packetSent(ClientPacketSendEvent e) {
        if (!this.isEnabled()){
            return;
        }
        if (e.getSendPacket() instanceof CPacketPlayer) {
            if (!XdolfMod.minecraft.gameSettings.keyBindAttack.isPressed() || !XdolfMod.minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                ((CPacketPlayer) e.getSendPacket()).onGround = false;
            }
        }
    }
}
