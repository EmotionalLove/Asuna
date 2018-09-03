/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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