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
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiModuleButton;
import com.sasha.adorufu.mod.gui.clickgui.elements.AdorufuGuiWindow;
import com.sasha.adorufu.mod.gui.clickgui.elements.IAdorufuGuiElement;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.module.AdorufuCategory;
import com.sasha.adorufu.mod.module.AdorufuModule;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AdorufuClickGUI extends GuiScreen {
    private static Lock lock = new ReentrantLock();
    private static List<IAdorufuGuiElement> elementList = new ArrayList<>();

    public AdorufuClickGUI() {
        elementList.clear();
        /*ArrayList<IAdorufuGuiElement> buttons = new ArrayList<>();
        ArrayList<IAdorufuGuiElement> fbuttons = new ArrayList<>();
        buttons.add(new AdorufuGuiModuleButton("GayAura", 0, 0, 100, 15, new ModuleToggler(Manager.Module.getModule(ModuleKillaura.class))));
        buttons.add(new AdorufuGuiModuleButton("Fishing", 0, 0, 100, 15, new ModuleToggler(Manager.Module.getModule(ModuleAFKFish.class))));
        fbuttons.add(new AdorufuGuiModuleButton("Hax", 0, 0, 100, 15, new ModuleToggler(Manager.Module.getModule(ModuleAFKFish.class))));
        fbuttons.add(new AdorufuGuiModuleButton("Meme", 0, 0, 100, 15, new ModuleToggler(Manager.Module.getModule(ModuleAFKFish.class))));
        elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f,  "Future Client", buttons));
        elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f,  "KAMI Client", fbuttons));
        */
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
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.MISC)
                        .forEach(e -> {
                            misc_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f,  "Misc", misc_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.GUI)
                        .forEach(e -> {
                            gui_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f
                        , "HUD", gui_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.COMBAT)
                        .forEach(e -> {
                            combat_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f
                        , "Combat", combat_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.CHAT)
                        .forEach(e -> {
                            chat_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f
                        , "Chat", chat_elements));

                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.RENDER)
                        .forEach(e -> {
                            render_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f
                        , "Render", render_elements));
                Manager.Module.moduleRegistry.stream()
                        .filter(e -> e.getModuleCategory() == AdorufuCategory.MOVEMENT)
                        .forEach(e -> {
                            movement_elements.add(new AdorufuGuiModuleButton(e.getModuleName() + (e.hasOptions() ? " \2477[...]" : ""), 0, 0, 100, 15, new ModuleToggler(e)));
                        });
                elementList.add(new AdorufuGuiWindow(100, 100, 60, 100, 79f, 79f, 79f, 255f
                        , "Movement", movement_elements));
            }
            finally {
                lock.unlock();
            }
        }, 0, TimeUnit.SECONDS);
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
    public static int calcListLength(int i) {
        return 0; //todo
    }

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
class ModuleToggler implements Runnable {

    private AdorufuModule m;

    public ModuleToggler(AdorufuModule m) {
        this.m = m;
    }

    @Override
    public void run() {
        m.toggle();
    }
}
