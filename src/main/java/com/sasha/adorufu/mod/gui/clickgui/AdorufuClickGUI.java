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

package com.sasha.adorufu.mod.gui.clickgui;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiButton;
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiWindow;
import com.sasha.adorufu.mod.gui.clickgui.elements.IAdorufuGuiElement;
import com.sasha.adorufu.mod.gui.clickgui.helper.ModuleToggler;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AdorufuClickGUI extends GuiScreen {
    public static Lock lock = new ReentrantLock();
    public static List<IAdorufuGuiElement> elementList = new ArrayList<>();

    public AdorufuClickGUI() {
        elementList.clear();
        // as you can tell IM FUCKING RETARDED
        AdorufuMod.scheduler.schedule(() -> {
            lock.lock();
            try {
                ArrayList<IAdorufuGuiElement> misc_elements = new ArrayList<>();
                ArrayList<IAdorufuGuiElement> gui_elements = new ArrayList<>();
                ArrayList<IAdorufuGuiElement> combat_elements = new ArrayList<>();
                ArrayList<IAdorufuGuiElement> chat_elements = new ArrayList<>();
                ArrayList<IAdorufuGuiElement> render_elements = new ArrayList<>();
                ArrayList<IAdorufuGuiElement> movement_elements = new ArrayList<>();
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.MISC).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            misc_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords = AdorufuMod.DATA_MANAGER.loadGuiElementPos("Misc");
                elementList.add(new AdorufuGuiWindow(coords[0], coords[1], calcListLength(misc_elements.size(),15), 100, 0f, 181f, 150f, 255f,  "Misc", misc_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.GUI).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            gui_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords$0 = AdorufuMod.DATA_MANAGER.loadGuiElementPos("HUD");
                elementList.add(new AdorufuGuiWindow(coords$0[0], coords$0[1], calcListLength(gui_elements.size(),15), 100, 79f, 79f, 79f, 255f
                        , "HUD", gui_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.COMBAT).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            combat_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords$1 = AdorufuMod.DATA_MANAGER.loadGuiElementPos("Combat");
                elementList.add(new AdorufuGuiWindow(coords$1[0], coords$1[1], calcListLength(combat_elements.size(),15), 100, 175f, 0f, 0f, 255f
                        , "Combat", combat_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.CHAT).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            chat_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords$2 = AdorufuMod.DATA_MANAGER.loadGuiElementPos("Chat");
                elementList.add(new AdorufuGuiWindow(coords$2[0], coords$2[1], calcListLength(chat_elements.size(),15), 100, 0f, 91f, 99f, 255f
                        , "Chat", chat_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.RENDER).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            render_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords$3 = AdorufuMod.DATA_MANAGER.loadGuiElementPos("Render");
                elementList.add(new AdorufuGuiWindow(coords$3[0], coords$3[1], calcListLength(render_elements.size(),15), 100, 204f, 136f, 0f, 255f
                        , "Render", render_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.MOVEMENT).sorted(Comparator.comparing(AdorufuModule::getModuleName))
                        .forEach(e -> {
                            movement_elements.add(new AdorufuGuiButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                int[] coords$4 = AdorufuMod.DATA_MANAGER.loadGuiElementPos("Movement");
                elementList.add(new AdorufuGuiWindow(coords$4[0], coords$4[1], calcListLength(movement_elements.size(),15), 100, 119f, 0f, 103f, 255f
                        , "Movement", movement_elements));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, 0, TimeUnit.SECONDS);
    }

    @Override
    public void onGuiClosed() {
        elementList.forEach(f -> {
            if (f instanceof AdorufuGuiWindow) {
                try {
                    AdorufuMod.DATA_MANAGER.saveGuiElementPos((AdorufuGuiWindow) f);
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
        }
        finally {
            lock.unlock();
        }
    }
    @Override
    public void mouseClicked(int x, int y, int b) {
        lock.lock();
        try {
            elementList.stream().filter(e -> e.onMouseEngage(x, y, b)).findFirst();
        }
        finally {
            lock.unlock();
        }
    }
    @Override
    public void mouseReleased(int x, int y, int b) {
        lock.lock();
        try {
            elementList.stream().filter(e -> e.onMouseRelease(x, y, b)).findFirst();
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Calculates what the length of you list should be
     * @param i the amount of buttons that the window will have
     * @return the length value
     */
    public static int calcListLength(int i, int heightPerButton) {
        return i * heightPerButton;
    }

    //todo
    public static boolean hasFocus(AdorufuGuiWindow element) {
        lock.lock();
        try {
            if (!elementList.contains(element)) return false;
            return elementList.indexOf(element) == 0;
        }
        finally {
            lock.unlock();
        }
    }
    //todo
    public static void requestFocus(AdorufuGuiWindow element) {
        lock.lock();
        try {
            if (elementList.indexOf(element) == 0) return;
            elementList.remove(element);
            elementList.add(0, element);
        }
        finally {
            lock.unlock();
        }
    }

}
