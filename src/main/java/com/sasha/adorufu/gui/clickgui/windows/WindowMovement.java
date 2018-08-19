package com.sasha.adorufu.gui.clickgui.windows;


import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;
import com.sasha.adorufu.module.AdorufuCategory;


public class WindowMovement extends AdorufuWindow
{
	public WindowMovement() {
		super("Movement", 104, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(AdorufuCategory.MOVEMENT);
	}
}
