package com.sasha.xdolf.gui.clickgui.windows;


import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;
import com.sasha.xdolf.module.XdolfCategory;


public class WindowMovement extends XdolfWindow
{
	public WindowMovement() {
		super("Movement", 104, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(XdolfCategory.MOVEMENT);
	}
}
