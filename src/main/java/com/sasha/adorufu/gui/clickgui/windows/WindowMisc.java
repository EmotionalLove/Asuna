package com.sasha.adorufu.gui.clickgui.windows;


import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;

import static com.sasha.adorufu.module.AdorufuCategory.MISC;

public class WindowMisc extends AdorufuWindow
{
	public WindowMisc() {
		super("Misc", 410, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(MISC);
	}
}
