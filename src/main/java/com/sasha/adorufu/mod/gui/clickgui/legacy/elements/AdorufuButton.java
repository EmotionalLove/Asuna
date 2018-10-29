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

package com.sasha.adorufu.mod.gui.clickgui.legacy.elements;


import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.gui.clickgui.legacy.AdorufuClickGUI;
import com.sasha.adorufu.mod.misc.AdorufuMath;
import com.sasha.adorufu.mod.feature.AdorufuModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import static com.sasha.adorufu.mod.gui.clickgui.legacy.AdorufuClickGUI.getRegisteredWindows;


@Deprecated
public class AdorufuButton {

	static Minecraft mc = Minecraft.getMinecraft();
	
	private AdorufuWindow window;
	private AdorufuModule mod;
	private int x, y;
	boolean overButton;
	private String displayName;
	
	AdorufuButton(AdorufuWindow window, AdorufuModule mod, int x, int y) {
		this.window = window;
		this.mod = mod;
		this.x = x;
		this.y = y;
		this.displayName = mod.getModuleName();
		if (mod.hasOptions()) {
            class NewOption extends AdorufuWindow {
                public NewOption() {
                    super(mod.getModuleName() + " Options", 2, 12, false, WindowType.OPTION);
                    mod.getModuleOptionsMap().forEach((str,bool) -> this.addOptionsButton(str));
                    AdorufuClickGUI.registeredWindows.add(this);
                }
            }
            new NewOption();
        }
	}
	
	public void draw() {
		float var1 = 0f;
		float var2 = 0f;
		float var3 = 0f;
		float var4 = 0.5f;
		float varia = 0.0f;
		if (this.isAnyWindowDragging() && !mod.isEnabled()) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		else if (this.isAnyWindowDragging() && mod.isEnabled()) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.5f;
			varia = 0.3f;
		}
		else if (mod.isEnabled() && overButton) {
			var1 = 1.0f;
			var2 = 0.0f;
			var3 = 0.0f;
			var4 = 1.0f;
			varia = 0.5f;
		}
		else if (mod.isEnabled() && !overButton) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.5f;
			varia = 0.3f;
		}
		else if (!mod.isEnabled() && overButton) {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 0.5f;
			var4 = 0.5f;
			varia = 0f;
		}
		else if (!mod.isEnabled() && !overButton) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		AdorufuMath.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, var1, var2,var3, var4,1f,1f,1f, varia);
		AdorufuMod.FONT_MANAGER.segoe_30.drawStringWithShadow(displayName, (x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
		 if (mod.hasOptions()) {
			 this.setDisplayName(mod.getModuleName() + " " + "\247" + "8[...]");
		 }
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && button == 0) {
			AdorufuClickGUI.focusWindow(window);
			mod.toggle();
			mc.world.playSound(mc.player.posX, mc.player.posY, mc.player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && button == 1) {
			AdorufuClickGUI.focusWindow(window);
			mc.world.playSound(mc.player.posX, mc.player.posY, mc.player.posZ, SoundEvents.BLOCK_CHEST_OPEN ,SoundCategory.MASTER, 1f, 1f, false);
			if (mod.hasOptions()) {
				for (AdorufuWindow w : getRegisteredWindows()) {
					if (w.getTitle().toLowerCase().contains(mod.getModuleName().toLowerCase() +" options")) {
						w.setShown(true);
						w.drag((window.getX() + window.getDragX()) + 100, y);
						break;
					}
				}
			}
		}
	}

	private boolean isAnyWindowDragging() {
		for (AdorufuWindow w : AdorufuClickGUI.registeredWindows) {
			if (w.dragging) {
				return true;
			}
		}
		return false;
	}
	
	public AdorufuModule getModule() {
		return mod;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
