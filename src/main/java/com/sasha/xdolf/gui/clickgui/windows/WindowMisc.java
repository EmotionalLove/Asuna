package com.sasha.xdolf.gui.clickgui.windows;


import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

import static com.sasha.xdolf.module.XdolfCategory.MISC;

public class WindowMisc extends XdolfWindow
{
	public WindowMisc() {
		super("Misc", 410, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(MISC);
	}
}
