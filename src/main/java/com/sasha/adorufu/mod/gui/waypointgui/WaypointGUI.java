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
import com.sasha.adorufu.mod.waypoint.Waypoint;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class WaypointGUI extends GuiScreen {
	
	public static ArrayList<WaypointWindow> windowList = new ArrayList<>();
	public static ArrayList<WaypointWindow> unFocusedWindows = new ArrayList<>();

	public static ArrayList<WaypointSearch> searchBoxes = new ArrayList<>();

	public static WaypointWindow ww = new WaypointWindow("Waypoint Manager", 100, 150, true);
	public static WaypointSearch tt = new WaypointSearch(windowList.get(0), "Search...", 102, 160);


	@Override
	public void initGui() {
		windowList.get(0).getButtonList().clear();
		for (Waypoint wp : AdorufuMod.WAYPOINT_MANAGER.getWaypoints()){
			windowList.get(0).addButton(wp);
		}
	}


	public void onGuiClosed() {

	}
	
	public void mouseClicked(int x, int y, int b) throws IOException {
		try {
			for(WaypointWindow w : windowList) {
				w.mouseClicked(x, y, b);
			}
			super.mouseClicked(x, y, b);
		}
		catch(Exception e) {
			//
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode){
		for (WaypointSearch s : searchBoxes) {
			s.updateText(typedChar, keyCode);
		}
	}
	
	public void mouseReleased(int x, int y, int state) {
		try {
			for(WaypointWindow w : windowList) {
				w.mouseReleased(x, y, state);
			}
			super.mouseReleased(x, y, state);
		}catch(Exception e) {
			//
		}
	}

	public void drawScreen(int x, int y, float ticks) {
		drawRect(0, 0, width, height, Integer.MIN_VALUE);
		for(WaypointWindow w : windowList) {
			if (w.isShown()) {
				w.draw(x, y);
			}
		}
		super.drawScreen(x, y, ticks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static void sendPanelToFront(WaypointWindow window)
	{
		if(windowList.contains(window))
		{
			int panelIndex = windowList.indexOf(window);
			windowList.remove(panelIndex);
			windowList.add(windowList.size(), window);
		}
	}
	public static ArrayList<WaypointWindow> getWindowList() {
		return windowList;
	}
	
	//public static AsunaWindow getFocusedPanel()
	//{
	//	return registeredWindows.get(registeredWindows.size() - 1);
	//}
}

