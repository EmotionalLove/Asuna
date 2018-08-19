package com.sasha.adorufu.gui.clickgui.windows;

import com.sasha.adorufu.gui.clickgui.elements.WindowType;
import com.sasha.adorufu.gui.clickgui.elements.AdorufuWindow;

import static com.sasha.adorufu.module.AdorufuCategory.CHAT;

public class WindowChat extends AdorufuWindow {
	
	public WindowChat() {
		super("Chat", 206, 12, true, WindowType.MODULE);
		this.loadButtonsFromCategory(CHAT);
	}
}

