/*
 * Copyright (c) Sasha Stevens (2017 - 2019)
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

package com.sasha.asuna.mod.gui.clickgui;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaFeature;
import com.sasha.asuna.mod.feature.IAsunaTogglableFeature;
import com.sasha.asuna.mod.gui.clickgui.elements.AsunaGuiButton;
import com.sasha.asuna.mod.gui.clickgui.elements.AsunaGuiWindow;
import com.sasha.asuna.mod.gui.clickgui.elements.IAsunaGuiElement;
import com.sasha.asuna.mod.gui.clickgui.helper.FeatureToggler;
import com.sasha.asuna.mod.misc.Manager;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsunaClickGUI extends GuiScreen {
    public static Lock lock = new ReentrantLock();
    public static List<IAsunaGuiElement> elementList = new ArrayList<>();

    public AsunaClickGUI() {
        elementList.clear();
        // as you can tell IM FUCKING RETARDED
        AsunaMod.scheduler.schedule(() -> {
            lock.lock();
            try {
                ArrayList<IAsunaGuiElement> misc_elements = new ArrayList<>();
                ArrayList<IAsunaGuiElement> gui_elements = new ArrayList<>();
                ArrayList<IAsunaGuiElement> combat_elements = new ArrayList<>();
                ArrayList<IAsunaGuiElement> chat_elements = new ArrayList<>();
                ArrayList<IAsunaGuiElement> render_elements = new ArrayList<>();
                ArrayList<IAsunaGuiElement> movement_elements = new ArrayList<>();
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.MISC).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            misc_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords = AsunaMod.DATA_MANAGER.loadGuiElementPos("Misc");
                elementList.add(new AsunaGuiWindow(coords[0], coords[1], calcListLength(misc_elements.size(), 15), 100, 0f, 181f, 150f, 255f, "Misc", misc_elements));
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.GUI).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            gui_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords$0 = AsunaMod.DATA_MANAGER.loadGuiElementPos("HUD");
                elementList.add(new AsunaGuiWindow(coords$0[0], coords$0[1], calcListLength(gui_elements.size(), 15), 100, 79f, 79f, 79f, 255f
                        , "HUD", gui_elements));
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.COMBAT).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            combat_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords$1 = AsunaMod.DATA_MANAGER.loadGuiElementPos("Combat");
                elementList.add(new AsunaGuiWindow(coords$1[0], coords$1[1], calcListLength(combat_elements.size(), 15), 100, 175f, 0f, 0f, 255f
                        , "Combat", combat_elements));
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.CHAT).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            chat_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords$2 = AsunaMod.DATA_MANAGER.loadGuiElementPos("Chat");
                elementList.add(new AsunaGuiWindow(coords$2[0], coords$2[1], calcListLength(chat_elements.size(), 15), 100, 0f, 91f, 99f, 255f
                        , "Chat", chat_elements));
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.RENDER).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            render_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords$3 = AsunaMod.DATA_MANAGER.loadGuiElementPos("Render");
                elementList.add(new AsunaGuiWindow(coords$3[0], coords$3[1], calcListLength(render_elements.size(), 15), 100, 204f, 136f, 0f, 255f
                        , "Render", render_elements));
                Manager.Feature.featureRegistry.stream()
                        .filter(e -> e instanceof IAsunaTogglableFeature)
                        .filter(e -> e.getCategory() == AsunaCategory.MOVEMENT).sorted(Comparator.comparing(IAsunaFeature::getFeatureName))
                        .forEach(e -> {
                            movement_elements.add(new AsunaGuiButton(e.getFeatureName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new FeatureToggler((IAsunaTogglableFeature) e)));
                        });
                int[] coords$4 = AsunaMod.DATA_MANAGER.loadGuiElementPos("Movement");
                elementList.add(new AsunaGuiWindow(coords$4[0], coords$4[1], calcListLength(movement_elements.size(), 15), 100, 119f, 0f, 103f, 255f
                        , "Movement", movement_elements));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, 0, TimeUnit.SECONDS);
    }

    /**
     * Calculates what the length of you list should be
     *
     * @param i the amount of buttons that the window will have
     * @return the length value
     */
    public static int calcListLength(int i, int heightPerButton) {
        return i * heightPerButton;
    }

    //todo
    public static boolean hasFocus(AsunaGuiWindow element) {
        lock.lock();
        try {
            if (!elementList.contains(element)) return false;
            return elementList.indexOf(element) == 0;
        } finally {
            lock.unlock();
        }
    }

    //todo
    public static void requestFocus(AsunaGuiWindow element) {
        new Thread(() -> {
            lock.lock();
            try {
                if (elementList.indexOf(element) == elementList.size() - 1) return;
                elementList.remove(element);
                elementList.add(element);
            } finally {
                lock.unlock();
            }
        }).start();
    }

    @Override
    public void onGuiClosed() {
        elementList.forEach(f -> {
            if (f instanceof AsunaGuiWindow) {
                try {
                    AsunaMod.DATA_MANAGER.saveGuiElementPos((AsunaGuiWindow) f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        lock.lock();
        try {
            elementList.forEach(e -> e.drawElement(x, y));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        lock.lock();
        try {
            elementList.stream().filter(e -> e.onMouseEngage(x, y, b)).findFirst();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        lock.lock();
        try {
            elementList.stream().filter(e -> e.onMouseRelease(x, y, b)).findFirst();
        } finally {
            lock.unlock();
        }
    }

}
