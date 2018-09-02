package com.sasha.adorufu.gui.fonts;

import java.awt.*;

public class FontManager
{
	public TTFontRenderer segoe_44;
	public TTFontRenderer segoe_36;
	public TTFontRenderer segoe_30;
	
	public void loadFonts()
	{
		this.segoe_44 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 44), true, 8);
		this.segoe_36 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 36), true, 8);
		this.segoe_30 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 30), true, 8);
	}
}