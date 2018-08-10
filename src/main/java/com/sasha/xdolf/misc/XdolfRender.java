package com.sasha.xdolf.misc;

import com.sasha.xdolf.XdolfMod;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glBegin;

/**
 * Created by Sasha on 09/08/2018 at 7:50 PM
 **/
public abstract class XdolfRender {
    /**
     * @author Sasha Stevens
     */
    public static void storageESP(double x, double y, double z, float r, float g, float b) {
        double posX = x - XdolfMod.mc.renderManager.renderPosX;
        double posY = y - XdolfMod.mc.renderManager.renderPosY;
        double posZ = z - XdolfMod.mc.renderManager.renderPosZ;
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
        double posX = x - XdolfMod.mc.renderManager.renderPosX;
        double posY = y - XdolfMod.mc.renderManager.renderPosY;
        double posZ = z - XdolfMod.mc.renderManager.renderPosZ;
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
