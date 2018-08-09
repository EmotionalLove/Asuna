package com.sasha.xdolf.module.modules.hudelements;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.friend.FriendManager;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

import static com.sasha.xdolf.XdolfMod.mc;

/**
 * Created by Sasha on 08/08/2018 at 12:23 PM
 **/
public class ModuleKillaura extends XdolfModule {

    public static double range = 5.0;

    public ModuleKillaura() {
        super("KillAura", XdolfCategory.COMBAT, false);
    }

    @Override
    public void onTick(){
        if (this.isEnabled()) {
            if (mc.player.isHandActive()) return; // Return if eating / holding up a shield / ...
            if (mc.player.getCooledAttackStrength(1) < 1) return;
            EntityLivingBase b = mc.world.loadedEntityList.stream()
                    .filter(e ->
                            e instanceof EntityLivingBase // Is this entity living?
                                    && !(e instanceof EntityPlayerSP)   // Is this entity not the local player?
                                    && mc.player.getDistance(e) <= 5.0f // Is this entity closer than 5 blocks?
                                    && e.isEntityAlive()    // Is this entity alive?
                                    && ((EntityLivingBase) e).hurtTime == 0 // Has this entity not been hurt recently?
                                    && (!(e instanceof EntityPlayer) || !XdolfMod.FRIEND_MANAGER.isFriended(e.getName())) // Is this entity a player? If so, are they not friended?
                    ).map(entity -> (EntityLivingBase) entity)
                    .findFirst().orElse(null);
            if (b != null) {
                float yaw = mc.player.rotationYaw;
                float pitch = mc.player.rotationPitch;
                float yawh = mc.player.rotationYawHead;
                boolean wasSprinting = mc.player.isSprinting();
                rotateTowardsEntity(b);
                mc.player.setSprinting(false);
                mc.playerController.attackEntity(mc.player, b);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.rotationYaw = yaw;
                mc.player.rotationPitch = pitch;
                mc.player.rotationYawHead = yawh;
                if (wasSprinting)
                    mc.player.setSprinting(true);
            }
        }
    }
    private static void rotateTowardsEntity(Entity entity) {
        double x = entity.posX - mc.player.posX;
        double z = entity.posZ - mc.player.posZ;
        double y = entity.posY + entity.getEyeHeight() / 1.4D - mc.player.posY/* + mc.player.getEyeHeight() / 1.4D**/;
        double rotatesq = MathHelper.sqrt(x * x + z * z);
        float newYaw = (float) Math.toDegrees(-Math.atan(x / z));
        float newPitch = (float) -Math.toDegrees(Math.atan(y / rotatesq) - 0.8f);
        if ((z < 0.0D) && (x < 0.0D)) {
            newYaw = (float) (90.0D + Math.toDegrees(Math.atan(z / x)));
        } else if ((z < 0.0D) && (x > 0.0D)) {
            newYaw = (float) (-90.0D + Math.toDegrees(Math.atan(z / x)));
        }
        mc.player.rotationYaw = newYaw;
        mc.player.rotationPitch = newPitch;
        mc.player.rotationYawHead = newPitch;
    }
}
