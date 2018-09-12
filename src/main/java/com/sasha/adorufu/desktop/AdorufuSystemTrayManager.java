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

package com.sasha.adorufu.desktop;

import com.sasha.adorufu.AdorufuMod;
import net.minecraft.client.gui.GuiMainMenu;

import java.awt.*;

/**
 * Created by Sasha at 6:33 AM on 9/12/2018
 */
public class AdorufuSystemTrayManager {

    public TrayIcon trayIcon;

    public AdorufuSystemTrayManager() {
        if (!SystemTray.isSupported()) {
            AdorufuMod.logErr(false, "Your OS doesn't support this!");
            return;
        }
        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("adorufu_tray.jpg"));
            trayIcon = new TrayIcon(image, "Adorufu " + AdorufuMod.VERSION);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Minecraft - Adorufu " + AdorufuMod.VERSION);
            PopupMenu pm = new PopupMenu("Adorufu");
            //
            MenuItem herobrineCmd = new MenuItem("Summon Herobrine...");
            herobrineCmd.setEnabled(false);
            MenuItem killCmd = new MenuItem("/kill");
            killCmd.addActionListener(e -> {
                if (AdorufuMod.minecraft.world != null) {
                    AdorufuMod.minecraft.player.sendChatMessage("/kill");
                }
            });
            MenuItem disconnect = new MenuItem("Disconnect");
            disconnect.addActionListener(e -> {
                if (AdorufuMod.minecraft.world != null) {
                    AdorufuMod.minecraft.world.sendQuittingDisconnectingPacket();
                    AdorufuMod.minecraft.displayGuiScreen(new GuiMainMenu());
                }
            });
            MenuItem quitGame = new MenuItem("Quit Game");
            quitGame.addActionListener(e -> AdorufuMod.minecraft.shutdown());
            pm.add(herobrineCmd);
            pm.addSeparator();
            pm.add(killCmd);
            pm.add(disconnect);
            pm.addSeparator();
            pm.add(quitGame);
            //
            trayIcon.setPopupMenu(pm);
            tray.add(trayIcon);
            AdorufuMod.EVENT_MANAGER.registerListener(new AdorufuSystemTrayEventHandler());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void rebuild() {
        if (trayIcon == null) return;
        PopupMenu pm = new PopupMenu("Adorufu");
        MenuItem herobrineCmd = new MenuItem("Summon Herobrine...");
        herobrineCmd.setEnabled(false);
        MenuItem killCmd = new MenuItem("/kill");
        if (AdorufuMod.minecraft.world == null) {
            killCmd.setEnabled(false);
        }
        killCmd.addActionListener(e -> {
            if (AdorufuMod.minecraft.world != null) {
                AdorufuMod.minecraft.player.sendChatMessage("/kill");
            }
        });
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
        pm.add(herobrineCmd);
        pm.addSeparator();
        pm.add(killCmd);
        pm.add(disconnect);
        pm.addSeparator();
        pm.add(quitGame);
        trayIcon.setPopupMenu(pm);
    }
}
