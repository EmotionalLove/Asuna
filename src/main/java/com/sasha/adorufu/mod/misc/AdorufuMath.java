/*
 * Copyright (c) Sasha Stevens 2018.
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

package com.sasha.adorufu.mod.misc;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.gui.hud.Direction;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.opengl.GL11.glBegin;

/**
 * Created by Sasha on 08/08/2018 at 2:07 PM
 **/
public abstract class AdorufuMath {

    public static int calculateFutureSyndromeFix() {
        int returnint = 0;
        Collection<PotionEffect> collection_Adorufu = AdorufuMod.minecraft.player.getActivePotionEffects();
        if (collection_Adorufu.isEmpty() || AdorufuMath.areAllPotionsAmbient(collection_Adorufu)) {
            return returnint;
        }
        else { // hello witchcraft my old friend
            AtomicInteger l = new AtomicInteger(1);
            collection_Adorufu.forEach(potion -> {
                if (potion.getPotion().hasStatusIcon() && potion.doesShowParticles()) {
                    if (potion.getPotion().isBeneficial()) {
                    } else {
                        l.addAndGet(26);
                    }
                }
            });
            return (l.get() + 3) + 18;
        }
    }

    public static boolean areAllPotionsAmbient(Collection<PotionEffect> collection) {
        for (PotionEffect effect : collection) {
            if (effect.getIsAmbient() && !effect.doesShowParticles()) {
                continue; // continues if potion is ambient, time to check the next one.
            }
            return false; // returns false when a potion ISN'T ambien
        }
        return true; // returns true if the loop finishes without tripping the return false
    }

    private static Direction getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return Direction.negX;
        } else if (22.5 <= rot && rot < 67.5) {
            return Direction.negXdiag1;
        } else if (67.5 <= rot && rot < 112.5) {
            return Direction.negZ;
        } else if (112.5 <= rot && rot < 157.5) {
            return Direction.posZdiag1;
        } else if (157.5 <= rot && rot < 202.5) {
            return Direction.posX;
        } else if (202.5 <= rot && rot < 247.5) {
            return Direction.posZdiag2;
        } else if (247.5 <= rot && rot < 292.5) {
            return Direction.posZ;
        } else if (292.5 <= rot && rot < 337.5) {
            return Direction.negXdiag2;
        } else if (337.5 <= rot && rot < 360.0) {
            return Direction.negX;
        } else {
            return null;
        }
    }

    public static Direction getCardinalDirection(float yaw) {
        double rot = (yaw - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    public static String getStringDirection(Direction d) {
        if (d == Direction.negZ) {
            return "-Z"; //east
        }
        if (d == Direction.negX) {
            return "-X"; // north
        }
        if (d == Direction.negXdiag1) {
            return "-X-Z"; // ne
        }
        if (d == Direction.negXdiag2) {
            return "-X+Z"; // nw
        }
        if (d == Direction.posX) {
            return "+X"; // s
        }
        if (d == Direction.posZdiag1) {
            return "+X-Z"; //se
        }
        if (d == Direction.posZ) {
            return "+Z"; // w
        }
        if (d == Direction.posZdiag2) {
            return "+X+Z";// sw
        }
        return null;
    }

    public static double percentage(double actual, double max) {
        double result = actual / max;
        result = result * 100;
        return result;
    }


    public static float fround(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static double dround(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Integer determineKeyCode(String letter) {
        if (letter.equals("a")) {
            return 30;
        }
        if (letter.equals("b")) {
            return 48;
        }
        if (letter.equals("c")) {
            return 46;
        }
        if (letter.equals("d")) {
            return 32;
        }
        if (letter.equals("e")) {
            return 18;
        }
        if (letter.equals("f")) {
            return Keyboard.KEY_F;
        }
        if (letter.equals("g")) {
            return 34;
        }
        if (letter.equals("h")) {
            return 35;
        }
        if (letter.equals("i")) {
            return 23;
        }
        if (letter.equals("j")) {
            return 36;
        }
        if (letter.equals("k")) {
            return 37;
        }
        if (letter.equals("l")) {
            return 38;
        }
        if (letter.equals("m")) {
            return 50;
        }
        if (letter.equals("n")) {
            return 49;
        }
        if (letter.equals("o")) {
            return 24;
        }
        if (letter.equals("p")) {
            return 25;
        }
        if (letter.equals("q")) {
            return 16;
        }
        if (letter.equals("r")) {
            return 19;
        }
        if (letter.equals("s")) {
            return 31;
        }
        if (letter.equals("t")) {
            return 20;
        }
        if (letter.equals("u")) {
            return 22;
        }
        if (letter.equals("v")) {
            return 47;
        }
        if (letter.equals("w")) {
            return 17;
        }
        if (letter.equals("x")) {
            return 45;
        }
        if (letter.equals("y")) {
            return 21;
        }
        if (letter.equals("z")) {
            return 44;
        }
        if (letter.equals("rshift")) {
            return Keyboard.KEY_RSHIFT;
        }
        if (letter.equals("lshift")) {
            return Keyboard.KEY_LSHIFT;
        }
        if (letter.equals("lctrl")) {
            return Keyboard.KEY_LCONTROL;
        }
        if (letter.equals("`") || letter.equals("~")) {
            return Keyboard.KEY_GRAVE;
        }
        if (letter.equals("none")) {
            return 0;
        }
        return null;
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, float y1, float size, int borderC, int insideC) {
        drawRect(x, y, x1, y1, insideC); //inside
        drawRect(x, y, x1, y + size, borderC); //top
        drawRect(x, y1, x1, y1 + size, borderC); //bottom
        drawRect(x1, y, x1 + size, y1 + size, borderC); //left
        drawRect(x, y, x + size, y1 + size, borderC); //right
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, int borderC, int insideC) {
        drawRect(x, y, x1, y1, insideC); //inside
        drawRect(x, y, x1, y + size, borderC); //top
        drawRect(x, y1, x1, y1 + size, borderC); //bottom
        drawRect(x1, y, x1 + size, y1 + size, borderC); //left
        drawRect(x, y, x + size, y1 + size, borderC); //right
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, float y1, float size, float br, float bg, float bb, float ba, float ir, float ig, float ib, float ia) {
        drawRect(x, y, x1, y1, ir, ig, ib, ia); //inside
        drawRect(x, y, x1, y + size, br, bg, bb, ba); //top
        drawRect(x, y1, x1, y1 + size, br, bg, bb, ba); //bottom
        drawRect(x1, y, x1 + size, y1 + size, br, bg, bb, ba); //left
        drawRect(x, y, x + size, y1 + size, br, bg, bb, ba); //right
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, float br, float bg, float bb, float ba, float ir, float ig, float ib, float ia) {
        drawRect(x, y, x1, y1, ir, ig, ib, ia); //inside
        drawRect(x, y, x1, y + size, br, bg, bb, ba); //top
        drawRect(x, y1, x1, y1 + size, br, bg, bb, ba); //bottom
        drawRect(x1, y, x1 + size, y1 + size, br, bg, bb, ba); //left
        drawRect(x, y, x + size, y1 + size, br, bg, bb, ba); //right
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(3553);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(2848);

        GL11.glColor4f(r, g, b, a);
        glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(paramXEnd, paramYStart);
        GL11.glVertex2d(paramXStart, paramYStart);
        GL11.glVertex2d(paramXStart, paramYEnd);
        GL11.glVertex2d(paramXEnd, paramYEnd);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
        float alpha = (float) (paramColor >> 24 & 0xFF) / 255F;
        float red = (float) (paramColor >> 16 & 0xFF) / 255F;
        float green = (float) (paramColor >> 8 & 0xFF) / 255F;
        float blue = (float) (paramColor & 0xFF) / 255F;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(2848);

        GL11.glColor4f(red, green, blue, alpha);
        glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(paramXEnd, paramYStart);
        GL11.glVertex2d(paramXStart, paramYStart);
        GL11.glVertex2d(paramXStart, paramYEnd);
        GL11.glVertex2d(paramXEnd, paramYEnd);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }
    public static class ScreenPos {
        public final int x;
        public final int y;
        public final boolean isVisible;

        public ScreenPos(double x, double y, boolean isVisible) {
            this.x = (int)x;
            this.y = (int)y;
            this.isVisible = isVisible;
        }
    }
}
