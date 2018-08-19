package com.sasha.adorufu.gui.clickgui.windows;


import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.module.AdorufuCategory;

public class WindowHUD extends AdorufuWindow {

	public WindowHUD() {
		super("HUD", 2, 250, true, WindowType.MODULE);
		this.loadButtonsFromCategory(AdorufuCategory.GUI);
	}
}

