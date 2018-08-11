package com.sasha.xdolf.gui.clickgui.windows;

import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

import static com.sasha.xdolf.module.XdolfCategory.CHAT;

public class WindowChat extends XdolfWindow {
	
	public WindowChat() {
		super("Chat", 206, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(CHAT);
	}
}

