package com.sasha.xdolf.gui.clickgui.windows.optionWindows;

import com.sasha.xdolf.gui.clickgui.elements.WindowType;
import com.sasha.xdolf.gui.clickgui.elements.XdolfWindow;

public class WindowOptionsChunkInspector extends XdolfWindow
{
	public WindowOptionsChunkInspector() {
		super("ChunkInspector options",2, 12, false,  WindowType.OPTION);
		addOptionsButton("New Chunks", "CI.NC");
		addOptionsButton("Chunk ESP", "CI.ESP");
		addOptionsButton("Pearls", "CI.P");
		addOptionsButton("Block Updates", "CI.BU");
	}
}
