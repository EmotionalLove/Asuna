package com.sasha.adorufu.gui.fonts;

import java.awt.*;

public class Fonts
{
	public static TTFontRenderer segoe_44;
	public static TTFontRenderer segoe_36;
	public static TTFontRenderer segoe_30;
	
	public static void loadFonts()
	{
		segoe_44 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 44), true, 8);
		segoe_36 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 36), true, 8);
		segoe_30 = new TTFontRenderer(new Font("Segoe UI", Font.PLAIN, 30), true, 8);
	}
}