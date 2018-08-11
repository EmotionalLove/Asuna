package com.sasha.xdolf.gui.clickgui;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;
import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.windows.*;
import com.sasha.xdolf.gui.clickgui.windows.optionWindows.WindowOptionsAutoWalk;
import com.sasha.xdolf.gui.clickgui.windows.optionWindows.WindowOptionsChunkInspector;
import com.sasha.xdolf.gui.clickgui.windows.optionWindows.WindowOptionsESP;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class XdolfClickGUI extends GuiScreen {
	
	public static ArrayList<XdolfWindow> windowList = new ArrayList<>();
	public static ArrayList<XdolfWindow> unFocusedWindows = new ArrayList<>();


	public static WindowRender render = new WindowRender();
	public static WindowMisc misc = new WindowMisc();
	public static WindowCombat combat = new WindowCombat();
	public static WindowMovement movement = new WindowMovement();
	public static WindowHUD hud = new WindowHUD();
	public static WindowChat chat = new WindowChat();
	public static WindowOptionsESP espoptions = new WindowOptionsESP();
	public static WindowOptionsChunkInspector cioptions = new WindowOptionsChunkInspector();
	public static WindowOptionsAutoWalk aaa = new WindowOptionsAutoWalk();


	@Override
	public void initGui() {
		for (XdolfWindow w : windowList) {
			if (w.getType() == WindowType.OPTION) {
				w.drag(3000,3000);
			}
			if (w.getType() == WindowType.MODULE) {
				boolean isNew = false;
				try {
					int[] lol = XdolfMod.DATA_MANAGER.getSavedGuiPos(w);
					w.dragX = lol[0];
					w.dragY = lol[1];
				} catch (Exception e) {
						XdolfMod.logErr(true,"Couldn't load ClickGUI positions");
				}
			}
		}
	}


	public void onGuiClosed() {
		for (XdolfWindow w : windowList) {
			XdolfMod.scheduler.schedule(()-> {
                try {
                    XdolfMod.DATA_MANAGER.saveGuiPos(w);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, TimeUnit.NANOSECONDS);
			if (w.getType() == WindowType.OPTION) {
				w.setShown(false);
				w.drag(3000,3000);
			}
		}
	}
	
	public void mouseClicked(int x, int y, int b) throws IOException {
		try {
			for(XdolfWindow w : windowList) {
				w.mouseClicked(x, y, b);
			}
			super.mouseClicked(x, y, b);
		}
		catch(Exception e) {
			//
		}
	}
	
	public void mouseReleased(int x, int y, int state) {
		try {
			for(XdolfWindow w : windowList) {
				w.mouseReleased(x, y, state);
			}
			super.mouseReleased(x, y, state);
		}catch(Exception e) {
			//
		}
	}

	public void drawScreen(int x, int y, float ticks) {
		drawRect(0, 0, width, height, Integer.MIN_VALUE);
		for(XdolfWindow w : windowList) {
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
	
	public static void sendPanelToFront(XdolfWindow window)
	{
		if(windowList.contains(window))
		{
			int panelIndex = windowList.indexOf(window);
			windowList.remove(panelIndex);
			windowList.add(windowList.size(), window);
		}
	}
	public static ArrayList<XdolfWindow> getWindowList() {
		return windowList;
	}
}

