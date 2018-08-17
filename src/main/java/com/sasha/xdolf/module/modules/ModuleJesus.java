package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketSendEvent;
import com.sasha.xdolf.events.CollisionBoxEvent;
import com.sasha.xdolf.module.PostToggleExec;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sasha on 12/08/2018 at 2:48 PM
 **/
@PostToggleExec
public class ModuleJesus extends XdolfModule implements SimpleListener {
    public ModuleJesus() {
        super("Jesus", XdolfCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            if (XdolfMod.minecraft.player.isInWater()) {
                XdolfMod.minecraft.player.motionY = 0.05;
            }
        }
    }
    @SimpleEventHandler
    public void onPacketTx(ClientPacketSendEvent e) {
        if (!this.isEnabled()) return;
        if (e.getSendPacket() instanceof CPacketPlayer) {
            if (isOverWater(XdolfMod.minecraft.player) && !XdolfMod.minecraft.gameSettings.keyBindSneak.isPressed()) {
                int t = XdolfMod.minecraft.player.ticksExisted % 2;
                if (t == 0) {
                    ((CPacketPlayer) e.getSendPacket()).y += 0.02;
                }
            }
        }
    }
    @SimpleEventHandler
    public void onBoxFormed(CollisionBoxEvent e) {
        if (!this.isEnabled()) {
            return;
        }
        if (!e.getBlock().getUnlocalizedName().trim().matches("tile\\.water|tile\\.lava")) {
            return;
        }
        if (inLiquid(XdolfMod.minecraft.player) || XdolfMod.minecraft.gameSettings.keyBindSneak.isKeyDown() || XdolfMod.minecraft.player.fallDistance > 2) {
            return;
        }
        e.setAabb(new AxisAlignedBB(0, 0, 0, 1, 0.99, 1));
    }
    private boolean isOverWater(Entity e) {
        BlockPos pos = e.getPosition();
        IBlockState state = XdolfMod.minecraft.world.getBlockState(pos.add(0, -1, 0));
        if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.LAVA) {
            return !XdolfMod.minecraft.player.isInWater() && state.getBlock() != Blocks.FLOWING_WATER && state.getBlock() != Blocks.FLOWING_LAVA;
        }
        return false;
    }

    private boolean inLiquid(Entity e) {
        double Y = e.getPosition().getY() + 0.1;
        Block block = XdolfMod.minecraft.world.getBlockState(new BlockPos(e.getPosition().getX(), Y, e.getPosition().getZ())).getBlock();
        return block instanceof BlockLiquid && block != Blocks.FLOWING_WATER && block != Blocks.FLOWING_LAVA;
    }
}
