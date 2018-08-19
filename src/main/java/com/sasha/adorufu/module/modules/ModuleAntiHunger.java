package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientPacketSendEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * Created by Sasha on 10/08/2018 at 12:47 PM
 **/
@ModuleInfo(description = "Makes your hunger last longer while sprinting/jumping/etc")
public class ModuleAntiHunger extends AdorufuModule implements SimpleListener {
    public ModuleAntiHunger() {
        super("AntiHunger", AdorufuCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled() && (!AdorufuMod.minecraft.gameSettings.keyBindAttack.isPressed() || !AdorufuMod.minecraft.gameSettings.keyBindAttack.isKeyDown())) {
            AdorufuMod.minecraft.getConnection().sendPacket(new CPacketPlayer(false));
        }
    }
    @SimpleEventHandler
    public void packetSent(ClientPacketSendEvent e) {
        if (!this.isEnabled()){
            return;
        }
        if (e.getSendPacket() instanceof CPacketPlayer) {
            if (!AdorufuMod.minecraft.gameSettings.keyBindAttack.isPressed() || !AdorufuMod.minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                ((CPacketPlayer) e.getSendPacket()).onGround = false;
            }
        }
    }
}
