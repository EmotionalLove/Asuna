package com.sasha.xdolf.gui.clickgui.windows.optionWindows;

import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

public class WindowOptionsAutoWalk extends XdolfWindow
{
	public WindowOptionsAutoWalk() {
		super("AutoWalk options",2, 12, false,  WindowType.OPTION);
		addOptionsButton("Simple", "AW.S");
		addOptionsButton("Advanced", "AW.A");
	}
}
