package com.sasha.adorufu.gui.clickgui.windows;

import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;

import static com.sasha.adorufu.module.AdorufuCategory.COMBAT;

public class WindowCombat extends AdorufuWindow
{
	public WindowCombat() {
		super("Combat", 2, 12, true, WindowType.MODULE);
		loadButtonsFromCategory(COMBAT);
	}
}
