package com.sasha.adorufu.gui.clickgui;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.windows.*;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AdorufuClickGUI extends GuiScreen {
	
	public static ArrayList<AdorufuWindow> windowList = new ArrayList<>();
	public static ArrayList<AdorufuWindow> unFocusedWindows = new ArrayList<>();


	public static WindowRender render = new WindowRender();
	public static WindowMisc misc = new WindowMisc();
	public static WindowCombat combat = new WindowCombat();
	public static WindowMovement movement = new WindowMovement();
	public static WindowHUD hud = new WindowHUD();
	public static WindowChat chat = new WindowChat();


	@Override
	public void initGui() {
		for (AdorufuWindow w : windowList) {
			if (w.getType() == WindowType.OPTION) {
				w.drag(3000,3000);
			}
			if (w.getType() == WindowType.MODULE) {
				boolean isNew = false;
				try {
					int[] lol = AdorufuMod.DATA_MANAGER.getSavedGuiPos(w);
					w.dragX = lol[0];
					w.dragY = lol[1];
				} catch (Exception e) {
						AdorufuMod.logErr(true,"Couldn't load ClickGUI positions");
				}
			}
		}
	}


	public void onGuiClosed() {
		for (AdorufuWindow w : windowList) {
			AdorufuMod.scheduler.schedule(()-> {
                try {
                    AdorufuMod.DATA_MANAGER.saveGuiPos(w);
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
			for(AdorufuWindow w : windowList) {
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
			for(AdorufuWindow w : windowList) {
				w.mouseReleased(x, y, state);
			}
			super.mouseReleased(x, y, state);
		}catch(Exception e) {
			//
		}
	}

	public void drawScreen(int x, int y, float ticks) {
		drawRect(0, 0, width, height, Integer.MIN_VALUE);
		for(AdorufuWindow w : windowList) {
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
	
	public static void sendPanelToFront(AdorufuWindow window)
	{
		if(windowList.contains(window))
		{
			int panelIndex = windowList.indexOf(window);
			windowList.remove(panelIndex);
			windowList.add(windowList.size(), window);
		}
	}
	public static ArrayList<AdorufuWindow> getWindowList() {
		return windowList;
	}
}

