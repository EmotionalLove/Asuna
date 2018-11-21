/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.mod.gui.hud;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.events.client.ClientOverlayRenderEvent;
import com.sasha.asuna.mod.misc.AsunaMath;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.sasha.asuna.mod.misc.Manager.Renderable.renderableRegistry;

public class AsunaHUD extends GuiScreen implements SimpleListener {

    public static int sHeight;
    public static int sWidth;

    // starts at x = 12
    private ArrayList<RenderableObject> leftTop = new ArrayList<>();

    // starts at x = (sHeight - 15)
    // possible blockades: chatbox
    private ArrayList<RenderableObject> leftBottom = new ArrayList<>();

    // starts at x = 2
    // possible blockades: potion effects
    private ArrayList<RenderableObject> rightTop = new ArrayList<>();

    // starts at x = (sHeight - 15)
    // possible blockades: hack arraylist
    private ArrayList<RenderableObject> rightBottom = new ArrayList<>();

    @SimpleEventHandler
    public void onOverlayRender(ClientOverlayRenderEvent e) {
        renderScreen();
    }

    /* todo optimise this */
    public void setupHUD() {
        AsunaMod.logWarn(true, "The HUD is (re)setting up!");
        for (RenderableObject renderableElement : renderableRegistry) {
            ScreenCornerPos thePos = renderableElement.getPos();
            if (renderableElement.getPos() == null) {
                thePos = renderableElement.getDefaultPos();
            }
            switch (thePos) {
                case LEFTTOP:
                    leftTop.add(renderableElement);
                    break;
                case LEFTBOTTOM:
                    leftBottom.add(renderableElement);
                    break;
                case RIGHTBOTTOM:
                    rightBottom.add(renderableElement);
                    break;
                case RIGHTTOP:
                    rightTop.add(renderableElement);
                    break;
            }

        }
    }

    public void renderScreen() {
        sHeight = new ScaledResolution(AsunaMod.minecraft).getScaledHeight();
        sWidth = new ScaledResolution(AsunaMod.minecraft).getScaledWidth();
        if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && AsunaMod.minecraft.world.isRemote && AsunaMod.minecraft.currentScreen == null) {
            return;
        }

        int lt_count = 0;
        for (RenderableObject o : leftTop) {
            if (!o.shouldRender()) continue;
            o.renderTheObject((2) + (10 * lt_count));
            lt_count++;
        }
        int lb_count = 0;
        for (RenderableObject o : leftBottom) {
            if (!o.shouldRender()) continue;
            if (AsunaMod.minecraft.currentScreen instanceof GuiChat) {
                o.renderTheObject(((sHeight - 15) - (10 * lb_count)) - 14);
                lb_count++;
                continue;
            }
            o.renderTheObject((sHeight - 15) - (10 * lb_count));
            lb_count++;
        }
        int rt_count = 0;
        for (RenderableObject o : rightTop) {
            if (!o.shouldRender()) continue;
            o.renderTheObject(((2) + (10 * rt_count)) + AsunaMath.calculateFutureSyndromeFix());
            rt_count++;
        }
        int rb_count = 0;
        for (RenderableObject o : rightBottom) {
            if (!o.shouldRender()) continue;
            if (AsunaMod.minecraft.currentScreen instanceof GuiChat) {
                o.renderTheObject(((sHeight - 15) - (10 * rb_count)) - 14);
                rb_count++;
                continue;
            }
            o.renderTheObject((sHeight - 15) - (10 * rb_count));
            rb_count++;
        }
    }
}
