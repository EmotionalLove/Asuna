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

package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.ClientScreenChangedEvent;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.gui.GuiMainMenu;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sasha at 3:30 PM on 9/9/2018
 */

public class ModuleDesktopNotifications extends AdorufuModule implements SimpleListener {
    TrayIcon trayIcon = null;
    public ModuleDesktopNotifications() {
        super("DesktopNotifications", AdorufuCategory.MISC, false, true);
        this.addOption("Chat mentions", true);
        this.addOption("Server kick", false);
    }

    @Override
    public void onEnable() {
        if (!SystemTray.isSupported()) {
            AdorufuMod.logErr(false, "Your OS doesn't support this!");
            this.toggle();
        }
        try {
            setup();
        } catch (AWTException | ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            AdorufuMod.logErr(false, "Couldn't initialise the tray icon!");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        if (this.trayIcon == null) {
            this.onEnable();
        }
    }
    private void setup() throws AWTException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("adorufu_tray.jpg"));
        trayIcon = new TrayIcon(image, "Adorufu " + AdorufuMod.VERSION);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Minecraft - Adorufu " + AdorufuMod.VERSION);
        PopupMenu pm = new PopupMenu("Adorufu");
        MenuItem disconnect = new MenuItem("Disconnect");
        disconnect.addActionListener(e -> {
            if (AdorufuMod.minecraft.world != null) {
                AdorufuMod.minecraft.world.sendQuittingDisconnectingPacket();
                AdorufuMod.minecraft.displayGuiScreen(new GuiMainMenu());
            }
        });
        MenuItem quitGame = new MenuItem("Quit Game");
        quitGame.addActionListener(e -> AdorufuMod.minecraft.shutdown());
        pm.add(quitGame);
        trayIcon.setPopupMenu(pm);
        tray.add(trayIcon);
    }
    public void rebuildMenu() {
        PopupMenu pm = new PopupMenu("Adorufu");
        MenuItem disconnect = new MenuItem("Disconnect");
        if (AdorufuMod.minecraft.world == null) {
            disconnect.setEnabled(false);
        }
        disconnect.addActionListener(e -> {
            if (AdorufuMod.minecraft.world != null) {
                AdorufuMod.minecraft.world.sendQuittingDisconnectingPacket();
                AdorufuMod.minecraft.displayGuiScreen(new GuiMainMenu());
            }
        });
        MenuItem quitGame = new MenuItem("Quit Game");
        quitGame.addActionListener(e -> AdorufuMod.minecraft.shutdown());
        pm.add(quitGame);
        trayIcon.setPopupMenu(pm);
    }
    @SimpleEventHandler
    public void onScreenChanged(ClientScreenChangedEvent e) {
        if (this.isEnabled()) AdorufuMod.scheduler.schedule(this::rebuildMenu,0, TimeUnit.NANOSECONDS);
    }
}
