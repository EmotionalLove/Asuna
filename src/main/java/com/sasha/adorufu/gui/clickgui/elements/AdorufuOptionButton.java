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

package com.sasha.adorufu.gui.clickgui.elements;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.AdorufuClickGUI;
import com.sasha.adorufu.misc.AdorufuMath;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import java.util.concurrent.atomic.AtomicBoolean;


public class AdorufuOptionButton {

	private AdorufuWindow window;
	private String text;
	private int x, y;
	boolean overButton;
	private String toToggle;

	AdorufuOptionButton(AdorufuWindow window, String text, int x, int y, String toToggle) {
		this.window = window;
		this.text = text;
		this.x = x;
		this.y = y;
		this.toToggle = toToggle;
	}
	
	public void draw() {
		float var1 = 0f;
		float var2 = 0f;
		float var3 = 0f;
		float var4 = 0.5f;
		AtomicBoolean isTrue = new AtomicBoolean(false);
		ModuleManager.moduleRegistry.stream().filter(AdorufuModule::hasOptions).forEach(mod -> {
			if ((mod.getModuleName() + " Options").toLowerCase().equalsIgnoreCase(this.window.getTitle())) {
				if (mod.getOption(this.toToggle)) isTrue.set(true);
				return;
			}
		});
		if (this.isAnyWindowDragging()) {
			var1 = 0f;
			var2 = isTrue.get() ? 0.7f : 0f;
			var3 = 0f;
			var4 = isTrue.get() ? 0.7f : 0f;
		}
		else if (!overButton) {
			var1 = 0f;
			var2 = isTrue.get() ? 0.7f : 0f;
			var3 = 0f;
			var4 = isTrue.get() ? 0.7f : 0f;
		}
		else {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.7f;
		}
		AdorufuMath.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, var1, var2,var3, var4,0f,0f,0f, 0f);
		AdorufuMod.FONT_MANAGER.segoe_30.drawStringWithShadow(text, (int)(x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && button == 0) {
			AdorufuClickGUI.focusWindow(window);
			Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
			ModuleManager.moduleRegistry.stream().filter(AdorufuModule::hasOptions).forEach(mod -> {
                if ((mod.getModuleName() + " Options").toLowerCase().equalsIgnoreCase(this.window.getTitle())) {
                	if (mod.useModeSelection()){
                		mod.toggleOptionMode(this.toToggle);
					}
                    mod.toggleOption(this.toToggle);
                    return;
                }
            });
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && button == 1) {
			AdorufuClickGUI.focusWindow(window);
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

	@Deprecated
	private void toggleBoolean(boolean booleanToToggle) {
		booleanToToggle = !booleanToToggle;
	}
	
	/*public Module getModule() {
		return mod;
	}*/
	
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
}
