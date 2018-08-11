package com.sasha.xdolf.gui.clickgui.windows;

import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

import static com.sasha.xdolf.module.XdolfCategory.COMBAT;

public class WindowCombat extends XdolfWindow
{
	public WindowCombat() {
		super("Combat", 2, 12, true, WindowType.MODULE);
		loadButtonsFromCategory(COMBAT);
	}
}
