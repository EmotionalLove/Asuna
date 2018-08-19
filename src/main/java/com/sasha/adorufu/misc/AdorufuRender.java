package com.sasha.adorufu.misc;

import com.sasha.adorufu.AdorufuMod;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glBegin;

/**
 * Created by Sasha on 09/08/2018 at 7:50 PM
 **/
public abstract class AdorufuRender {
    public static int tracers(){
        int i=0;
        try {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH); // tracer renderer
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_DEPTH_TEST); // minecraft renderer 2929
            GL11.glDisable(GL11.GL_TEXTURE_2D); // minecraft renderer 3553
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(1);
            for (Entity e : AdorufuMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityOtherPlayerMP) {
                    i++;
                    double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) - AdorufuMod.minecraft.renderManager.renderPosX;
                    double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) - AdorufuMod.minecraft.renderManager.renderPosY;
                    double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) - AdorufuMod.minecraft.renderManager.renderPosZ;
                    if (AdorufuMod.FRIEND_MANAGER.isFriended(e.getName())) {
                        GL11.glColor3f(0.0f, 2.0F, 1.0F);
                    } else {
                        float factor = AdorufuMod.minecraft.player.getDistance(e) / 40F;
                        if (factor > 1)
                            factor = 1;

                        GL11.glColor4f(2 - factor * 2F, factor * 2F, 0, 0.5F);
                    }
                    Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float) Math.toRadians(AdorufuMod.minecraft.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(AdorufuMod.minecraft.player.rotationYaw));
                    GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3d(eyes.x, AdorufuMod.minecraft.player.getEyeHeight() + eyes.y, eyes.z);
                    GL11.glVertex3d(posX, posY, posZ);
                    GL11.glEnd();
                    if (AdorufuMod.FRIEND_MANAGER.isFriended(e.getName())) {
                        GL11.glColor3f(0.0f, 2.0F, 1.0F);
                    } else {
                        float factor = AdorufuMod.minecraft.player.getDistance(e) / 40F;
                        if (factor > 1)
                            factor = 1;

                        GL11.glColor4f(2 - factor * 2F, factor * 2F, 0, 0.5F);
                    }
                    GL11.glBegin(2);
                    GL11.glVertex3d(posX, posY, posZ);
                    GL11.glVertex3d(posX, posY + 1.9f, posZ);
                    GL11.glEnd();
                }
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glPopMatrix();
            return i;
        } catch (Exception e) {
        }
        return 0;
    }
    /**
     * @author Sasha Stevens
     */
    public static void storageESP(double x, double y, double z, float r, float g, float b) {
        double posX = x - AdorufuMod.minecraft.renderManager.renderPosX;
        double posY = y - AdorufuMod.minecraft.renderManager.renderPosY;
        double posZ = z - AdorufuMod.minecraft.renderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(2848); // tracer renderer
        GL11.glDisable(2929); // minecraft renderer
        GL11.glDisable(3553); // minecraft renderer
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glColor3f(r, g, b);
        // 1
        //GL11.glColor3f(1f, 0, 0); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ);
        GL11.glVertex3d(posX + 1, posY, posZ);
        GL11.glVertex3d(posX + 1, posY + 1, posZ);
        GL11.glVertex3d(posX, posY + 1, posZ);
        GL11.glEnd();
        // 2
        //GL11.glColor3f(0f, 1f, 0f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ);
        GL11.glVertex3d(posX, posY, posZ + 1);
        //GL11.glVertex3d(posX, posY + 1, posZ + 1);
        GL11.glEnd();
        glBegin(2);
        GL11.glVertex3d(posX, posY + 1, posZ);
        GL11.glVertex3d(posX, posY + 1, posZ + 1);
        GL11.glEnd();
        // 3
        //GL11.glColor3f(0f, 0f, 1f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ + 1);
        GL11.glVertex3d(posX + 1, posY, posZ + 1);
        GL11.glVertex3d(posX + 1, posY + 1, posZ + 1);
        GL11.glVertex3d(posX, posY + 1, posZ + 1);
        GL11.glEnd();
        glBegin(2);
        GL11.glVertex3d(posX, posY + 1, posZ + 1);
        GL11.glVertex3d(posX + 1, posY + 1, posZ + 1);
        GL11.glEnd();
        // 4
        //GL11.glColor3f(1f, 1f, 1f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX + 1, posY, posZ + 1);
        GL11.glVertex3d(posX + 1, posY, posZ);
        GL11.glVertex3d(posX + 1, posY + 1, posZ);
        GL11.glVertex3d(posX + 1, posY + 1, posZ + 1);
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    /**
     * Used for ChunkInspector (aka ChunkTrace)
     * @author Sasha Stevens
     */
    public static void chunkESP(double x, double y, double z, float r, float g, float b, float trans, double maxY) {
        double posX = x - AdorufuMod.minecraft.renderManager.renderPosX;
        double posY = y - AdorufuMod.minecraft.renderManager.renderPosY;
        double posZ = z - AdorufuMod.minecraft.renderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable(2848); // tracer renderer
        GL11.glDisable(2929); // minecraft renderer
        GL11.glDisable(3553); // minecraft renderer
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(0.25f);
        GL11.glColor4f(r, g, b, trans);
        // 1
        //GL11.glColor3f(1f, 0, 0); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ);
        GL11.glVertex3d(posX + 16, posY, posZ);
        GL11.glVertex3d(posX + 16, posY + maxY, posZ);
        GL11.glVertex3d(posX, posY + maxY, posZ);
        GL11.glEnd();
        // 2
        //GL11.glColor3f(0f, 1f, 0f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ);
        GL11.glVertex3d(posX, posY, posZ + 16);
        //GL11.glVertex3d(posX, posY + 1, posZ + 1);
        GL11.glEnd();
        glBegin(2);
        GL11.glVertex3d(posX, posY + maxY, posZ);
        GL11.glVertex3d(posX, posY + maxY, posZ + 16);
        GL11.glEnd();
        // 3
        //GL11.glColor3f(0f, 0f, 1f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX, posY, posZ + 16);
        GL11.glVertex3d(posX + 16, posY, posZ + 16);
        GL11.glVertex3d(posX + 16, posY + maxY, posZ + 16);
        GL11.glVertex3d(posX, posY + maxY, posZ + 16);
        GL11.glEnd();
        glBegin(2);
        GL11.glVertex3d(posX, posY + 1, posZ + 16);
        GL11.glVertex3d(posX + 16, posY + 1, posZ + 16);
        GL11.glEnd();
        // 4
        //GL11.glColor3f(1f, 1f, 1f); // TODO
        glBegin(2);
        GL11.glVertex3d(posX + 16, posY, posZ + 16);
        GL11.glVertex3d(posX + 16, posY, posZ);
        GL11.glVertex3d(posX + 16, posY + maxY, posZ);
        GL11.glVertex3d(posX + 16, posY + maxY, posZ + 16);
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
}
