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

package com.sasha.adorufu.mod.gui.waypointgui;


import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.misc.AdorufuMath;
import com.sasha.adorufu.mod.waypoint.Waypoint;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import java.io.IOException;

import static com.sasha.adorufu.mod.gui.waypointgui.WaypointGUI.windowList;

public class WaypointButton {

	private WaypointWindow window;
	private Waypoint mod;
	private int x, y;
	boolean overButton;
	private String displayName;

	WaypointButton(WaypointWindow window, Waypoint mod, int x, int y) {
		this.window = window;
		this.mod = mod;
		this.x = x;
		this.y = y;
		this.displayName = mod.getName();
	}
	
	public void draw() {
		float var1 = 0f;
		float var2 = 0f;
		float var3 = 0f;
		float var4 = 0.5f;
		float varia = 0.0f;
		if (this.isAnyWindowDragging() && !mod.isDoesRender()) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		else if (this.isAnyWindowDragging() && mod.isDoesRender()) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.5f;
			varia = 0.3f;
		}
		else if (mod.isDoesRender() && overButton) {
			var1 = 1.0f;
			var2 = 0.0f;
			var3 = 0.0f;
			var4 = 1.0f;
			varia = 0.5f;
		}
		else if (mod.isDoesRender() && !overButton) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.5f;
			varia = 0.3f;
		}
		else if (!mod.isDoesRender() && overButton) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 0.5f;
			var4 = 0.5f;
			varia = 0f;
		}
		else if (!mod.isDoesRender() && !overButton) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		AdorufuMath.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 196 + window.getDragX(), y + 22  + window.getDragY(), 0.5F, var1, var2,var3, var4,1f,1f,1f, varia);
		//ClientUtils.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, 0xF0F0F0FF, 0x00000000);
		AdorufuMod.FONT_MANAGER.segoe_30.drawStringWithShadow(displayName, (x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
		AdorufuMod.FONT_MANAGER.segoe_30.drawStringWithShadow("\247" + "7XYZ " + "\247" + "r" + mod.getCoords()[0] + " " + mod.getCoords()[1] + " " + mod.getCoords()[2], (x + 4 + window.getDragX()), (y + window.getDragY())+10, 0xFFFFFF);
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 196 + window.getDragX() && y <= getY() + 21 + window.getDragY() && window.isOpen() && button == 0) {
			WaypointGUI.sendPanelToFront(window);
			mod.toggle();
			try {
				AdorufuMod.DATA_MANAGER.saveWaypoint(mod, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			AdorufuMod.minecraft.world.playSound(AdorufuMod.minecraft.player.posX, AdorufuMod.minecraft.player.posY, AdorufuMod.minecraft.player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 196 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 1) {
			WaypointGUI.sendPanelToFront(window);
			AdorufuMod.minecraft.world.playSound(AdorufuMod.minecraft.player.posX, AdorufuMod.minecraft.player.posY, AdorufuMod.minecraft.player.posZ, SoundEvents.BLOCK_CHEST_OPEN , SoundCategory.MASTER, 1f, 1f, false);

			//Client.pushNotification("Under construction");
		}
	}

	private boolean isAnyWindowDragging() {
		for (WaypointWindow w : windowList) {
			if (w.dragging) {
				return true;
			}
		}
		return false;
	}
	
	public Waypoint getModule() {
		return mod;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
