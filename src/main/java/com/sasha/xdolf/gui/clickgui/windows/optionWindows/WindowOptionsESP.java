package com.sasha.xdolf.gui.clickgui.windows.optionWindows;


import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

public class WindowOptionsESP extends XdolfWindow
{
	public WindowOptionsESP() {
		super("ESP options",2, 12, false,  WindowType.OPTION);
		addOptionsButton("Players", "ESP.P");
		addOptionsButton("Passives", "ESP.PS");
		addOptionsButton("Hostiles", "ESP.H");
	}
}
