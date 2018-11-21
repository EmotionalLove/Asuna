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
import com.sasha.asuna.mod.gui.hud.Direction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.glBegin;

/**
 * Created by Sasha on 08/08/2018 at 2:07 PM
 **/
public abstract class AsunaMath {

    public static int calculateFutureSyndromeFix() {
        Collection<PotionEffect> potionEffects = AsunaMod.minecraft.player.getActivePotionEffects();
        // thanks brady <3 love you
        List<Potion> potionList = potionEffects.stream()
                .filter(PotionEffect::doesShowParticles)
                .map(PotionEffect::getPotion)
                .filter(Potion::hasStatusIcon)
                .collect(Collectors.toList());
        return potionList.stream().anyMatch(Potion::isBadEffect) ? 48 : potionList.stream().anyMatch(Potion::isBadEffect) ? 24 : 0;
    }

    public static boolean areAllPotionsAmbient(Collection<PotionEffect> collection) {
        return collection.stream().anyMatch(p -> p.getIsAmbient() && !p.doesShowParticles());
    }

    @Deprecated // todo needs rewrite because this actually sucks
    // i wrote it like a year ago lmao
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

        BigDecimal bd = new BigDecimal(value + "");
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static double dround(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value + "");
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

        GL11.glColor4f(r / 255f, g / 255f, b / 255f, a / 255f);
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
            this.x = (int) x;
            this.y = (int) y;
            this.isVisible = isVisible;
        }
    }
}
