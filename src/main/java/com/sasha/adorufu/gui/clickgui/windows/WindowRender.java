package com.sasha.adorufu.gui.clickgui.windows;


import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.module.AdorufuCategory;

public class WindowRender extends AdorufuWindow {
	public WindowRender() {
		super("Render", 308, 12, true, WindowType.MODULE);
		loadButtonsFromCategory(AdorufuCategory.RENDER);
	}
}