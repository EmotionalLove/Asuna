package com.sasha.xdolf.module.modules;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

import static com.sasha.xdolf.XdolfMod.minecraft;

/**
 * Created by Sasha on 08/08/2018 at 12:23 PM
 **/
@ModuleInfo(description = "Automatically attacks nearby entities, excluding friended players.")
public class ModuleKillaura extends XdolfModule {

    public static double range = 5.0;

    public ModuleKillaura() {
        super("KillAura", XdolfCategory.COMBAT, false);
    }

    @Override
    public void onTick(){
        if (this.isEnabled()) {
            if (minecraft.player.isHandActive()) return; // Return if eating / holding up a shield / ...
            if (minecraft.player.getCooledAttackStrength(1) < 1) return;
            EntityLivingBase b = minecraft.world.loadedEntityList.stream()
                    .filter(e ->
                            e instanceof EntityLivingBase // Is this entity living?
                                    && !(e instanceof EntityPlayerSP)   // Is this entity not the local player?
                                    && minecraft.player.getDistance(e) <= 5.0f // Is this entity closer than 5 blocks?
                                    && e.isEntityAlive()    // Is this entity alive?
                                    && ((EntityLivingBase) e).hurtTime == 0 // Has this entity not been hurt recently?
                                    && (!(e instanceof EntityPlayer) || !XdolfMod.FRIEND_MANAGER.isFriended(e.getName())) // Is this entity a player? If so, are they not friended?
                    ).map(entity -> (EntityLivingBase) entity)
                    .findFirst().orElse(null);
            if (b != null) {
                float yaw = minecraft.player.rotationYaw;
                float pitch = minecraft.player.rotationPitch;
                float yawh = minecraft.player.rotationYawHead;
                boolean wasSprinting = minecraft.player.isSprinting();
                rotateTowardsEntity(b);
                minecraft.player.setSprinting(false);
                minecraft.playerController.attackEntity(minecraft.player, b);
                minecraft.player.swingArm(EnumHand.MAIN_HAND);
                minecraft.player.rotationYaw = yaw;
                minecraft.player.rotationPitch = pitch;
                minecraft.player.rotationYawHead = yawh;
                if (wasSprinting)
                    minecraft.player.setSprinting(true);
            }
        }
    }
    private static void rotateTowardsEntity(Entity entity) {
        double x = entity.posX - minecraft.player.posX;
        double z = entity.posZ - minecraft.player.posZ;
        double y = entity.posY + entity.getEyeHeight() / 1.4D - minecraft.player.posY/* + minecraft.player.getEyeHeight() / 1.4D**/;
        double rotatesq = MathHelper.sqrt(x * x + z * z);
        float newYaw = (float) Math.toDegrees(-Math.atan(x / z));
        float newPitch = (float) -Math.toDegrees(Math.atan(y / rotatesq) - 0.8f);
        if ((z < 0.0D) && (x < 0.0D)) {
            newYaw = (float) (90.0D + Math.toDegrees(Math.atan(z / x)));
        } else if ((z < 0.0D) && (x > 0.0D)) {
            newYaw = (float) (-90.0D + Math.toDegrees(Math.atan(z / x)));
        }
        minecraft.player.rotationYaw = newYaw;
        minecraft.player.rotationPitch = newPitch;
        minecraft.player.rotationYawHead = newPitch;
    }
}
