package com.sasha.xdolf.gui.clickgui.windows;


import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;
import com.sasha.xdolf.module.XdolfCategory;

public class WindowRender extends XdolfWindow {
	public WindowRender() {
		super("Render", 308, 12, true, WindowType.MODULE);
		loadButtonsFromCategory(XdolfCategory.RENDER);
	}
}