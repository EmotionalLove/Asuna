/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.asuna.mod.misc;

import com.sasha.asuna.mod.AsunaMod;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;


/**
 * Created by Sasha on 09/08/2018 at 7:50 PM
 **/
public abstract class AsunaRender {
    public static int tracers() {
        int i = 0;
        try {
            glPushMatrix();
            glEnable(GL_BLEND);
            glEnable(GL_LINE_SMOOTH); // tracer renderer
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_DEPTH_TEST); // minecraft renderer 2929
            glDisable(GL_TEXTURE_2D); // minecraft renderer 3553
            glDepthMask(false);
            glBlendFunc(770, 771);
            glEnable(3042);
            glLineWidth(1);
            for (Entity e : AsunaMod.minecraft.world.loadedEntityList) {
                if (e instanceof EntityOtherPlayerMP) {
                    i++;
                    double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) - AsunaMod.minecraft.renderManager.renderPosX;
                    double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) - AsunaMod.minecraft.renderManager.renderPosY;
                    double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) - AsunaMod.minecraft.renderManager.renderPosZ;
                    if (AsunaMod.FRIEND_MANAGER.isFriended(e.getName())) {
                        glColor3f(0.0f, 2.0F, 1.0F);
                    } else {
                        float factor = AsunaMod.minecraft.player.getDistance(e) / 40F;
                        if (factor > 1)
                            factor = 1;

                        glColor4f(2 - factor * 2F, factor * 2F, 0, 0.5F);
                    }
                    Vec3d eyes = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-(float) Math.toRadians(AsunaMod.minecraft.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(AsunaMod.minecraft.player.rotationYaw));
                    glBegin(GL_LINES);
                    glVertex3d(eyes.x, AsunaMod.minecraft.player.getEyeHeight() + eyes.y, eyes.z);
                    glVertex3d(posX, posY, posZ);
                    glEnd();
                    if (AsunaMod.FRIEND_MANAGER.isFriended(e.getName())) {
                        glColor3f(0.0f, 2.0F, 1.0F);
                    } else {
                        float factor = AsunaMod.minecraft.player.getDistance(e) / 40F;
                        if (factor > 1)
                            factor = 1;

                        glColor4f(2 - factor * 2F, factor * 2F, 0, 0.5F);
                    }
                    glBegin(2);
                    glVertex3d(posX, posY, posZ);
                    glVertex3d(posX, posY + 1.9f, posZ);
                    glEnd();
                }
            }
            glDisable(GL_BLEND);
            glDepthMask(true);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            glDisable(GL_LINE_SMOOTH);
            glPopMatrix();
            return i;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * @author Sasha Stevens
     */
    public static void storageESP(double x, double y, double z, float r, float g, float b) {
        double posX = x - AsunaMod.minecraft.renderManager.renderPosX;
        double posY = y - AsunaMod.minecraft.renderManager.renderPosY;
        double posZ = z - AsunaMod.minecraft.renderManager.renderPosZ;
        glPushMatrix();
        glEnable(2848); // tracer renderer
        glDisable(2929); // minecraft renderer
        glDisable(3553); // minecraft renderer
        glDepthMask(false);
        glBlendFunc(770, 771);
        glEnable(3042);
        glLineWidth(2.0f);
        glColor3f(r, g, b);
        // 1
        //GL11.glColor3f(1f, 0, 0); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX + 1, posY, posZ);
        glVertex3d(posX + 1, posY + 1, posZ);
        glVertex3d(posX, posY + 1, posZ);
        glEnd();
        // 2
        //GL11.glColor3f(0f, 1f, 0f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX, posY, posZ + 1);
        //GL11.glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + 1, posZ);
        glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        // 3
        //GL11.glColor3f(0f, 0f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ + 1);
        glVertex3d(posX + 1, posY, posZ + 1);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + 1, posZ + 1);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glEnd();
        // 4
        //GL11.glColor3f(1f, 1f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX + 1, posY, posZ + 1);
        glVertex3d(posX + 1, posY, posZ);
        glVertex3d(posX + 1, posY + 1, posZ);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glColor3f(1f, 1f, 1f);
        glEnd();
        glDisable(3042);
        glDepthMask(true);
        glEnable(3553);
        glEnable(2929);
        glDisable(2848);
        glPopMatrix();
    }

    public static void ghostBlock(double x, double y, double z, float r, float g, float b, float trans) {
        double posX = x - AsunaMod.minecraft.renderManager.renderPosX;
        double posY = y - AsunaMod.minecraft.renderManager.renderPosY;
        double posZ = z - AsunaMod.minecraft.renderManager.renderPosZ;
        glPushMatrix();
        glEnable(2848); // tracer renderer
        glDisable(2929); // minecraft renderer
        glDisable(3553); // minecraft renderer
        glDepthMask(false);
        glBlendFunc(770, 771);
        glEnable(3042);
        glLineWidth(2.0f);
        glColor4f(r, g, b, trans);
        // 1
        //GL11.glColor3f(1f, 0, 0); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX + 1, posY, posZ);
        glVertex3d(posX + 1, posY + 1, posZ);
        glVertex3d(posX, posY + 1, posZ);
        glEnd();
        // 2
        //GL11.glColor3f(0f, 1f, 0f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX, posY, posZ + 1);
        //GL11.glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + 1, posZ);
        glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        // 3
        //GL11.glColor3f(0f, 0f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ + 1);
        glVertex3d(posX + 1, posY, posZ + 1);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + 1, posZ + 1);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glEnd();
        // 4
        //GL11.glColor3f(1f, 1f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX + 1, posY, posZ + 1);
        glVertex3d(posX + 1, posY, posZ);
        glVertex3d(posX + 1, posY + 1, posZ);
        glVertex3d(posX + 1, posY + 1, posZ + 1);
        glColor4f(1f, 1f, 1f, 1f);
        glEnd();
        glDisable(3042);
        glDepthMask(true);
        glEnable(3553);
        glEnable(2929);
        glDisable(2848);
        glPopMatrix();
    }

    /**
     * Used for ChunkInspector (aka ChunkTrace)
     *
     * @author Sasha Stevens
     */
    public static void chunkESP(double x, double y, double z, float r, float g, float b, float trans, double maxY) {
        double posX = x - AsunaMod.minecraft.renderManager.renderPosX;
        double posY = y - AsunaMod.minecraft.renderManager.renderPosY;
        double posZ = z - AsunaMod.minecraft.renderManager.renderPosZ;
        glPushMatrix();
        glEnable(2848); // tracer renderer
        glDisable(2929); // minecraft renderer
        glDisable(3553); // minecraft renderer
        glDepthMask(false);
        glBlendFunc(770, 771);
        glEnable(3042);
        glLineWidth(0.25f);
        glColor4f(r, g, b, trans);
        // 1
        //GL11.glColor3f(1f, 0, 0); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX + 16, posY, posZ);
        glVertex3d(posX + 16, posY + maxY, posZ);
        glVertex3d(posX, posY + maxY, posZ);
        glEnd();
        // 2
        //GL11.glColor3f(0f, 1f, 0f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ);
        glVertex3d(posX, posY, posZ + 16);
        //GL11.glVertex3d(posX, posY + 1, posZ + 1);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + maxY, posZ);
        glVertex3d(posX, posY + maxY, posZ + 16);
        glEnd();
        // 3
        //GL11.glColor3f(0f, 0f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX, posY, posZ + 16);
        glVertex3d(posX + 16, posY, posZ + 16);
        glVertex3d(posX + 16, posY + maxY, posZ + 16);
        glVertex3d(posX, posY + maxY, posZ + 16);
        glEnd();
        glBegin(2);
        glVertex3d(posX, posY + 1, posZ + 16);
        glVertex3d(posX + 16, posY + 1, posZ + 16);
        glEnd();
        // 4
        //GL11.glColor3f(1f, 1f, 1f); // TODO
        glBegin(2);
        glVertex3d(posX + 16, posY, posZ + 16);
        glVertex3d(posX + 16, posY, posZ);
        glVertex3d(posX + 16, posY + maxY, posZ);
        glVertex3d(posX + 16, posY + maxY, posZ + 16);
        glColor3f(1f, 1f, 1f);
        glEnd();
        glDisable(3042);
        glDepthMask(true);
        glEnable(3553);
        glEnable(2929);
        glDisable(2848);
        glPopMatrix();
    }
}
