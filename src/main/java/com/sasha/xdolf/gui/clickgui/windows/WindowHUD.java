package com.sasha.xdolf.gui.clickgui.windows;


import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;
import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.module.XdolfCategory;

public class WindowHUD extends XdolfWindow {

	public WindowHUD() {
		super("HUD", 2, 250, true, WindowType.MODULE);
		this.loadButtonsFromCategory(XdolfCategory.GUI);
	}
}

