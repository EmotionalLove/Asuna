package com.sasha.adorufu.gui.clickgui.elements;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.gui.clickgui.AdorufuClickGUI;
import com.sasha.adorufu.gui.fonts.Fonts;
import com.sasha.adorufu.misc.AdorufuMath;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.AdorufuModule;
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
		//ClientUtils.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, 0xF0F0F0FF, 0x00000000);
		
		Fonts.segoe_30.drawStringWithShadow(text, (int)(x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 0) {
			AdorufuClickGUI.sendPanelToFront(window);
			Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
			ModuleManager.moduleRegistry.stream().filter(AdorufuModule::hasOptions).forEach(mod -> {
			    //mod.getModuleOptionsMap().forEach((str, bool) -> AdorufuMod.logMsg(str));
                if ((mod.getModuleName() + " Options").toLowerCase().equalsIgnoreCase(this.window.getTitle())) {
                    mod.toggleOption(this.toToggle);
                    return;
                }
            });
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 1) {
			AdorufuClickGUI.sendPanelToFront(window);
		}
	}

	private boolean isAnyWindowDragging() {
		for (AdorufuWindow w : AdorufuClickGUI.windowList) {
			if (w.dragging) {
				return true;
			}
		}
		return false;
	}

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
