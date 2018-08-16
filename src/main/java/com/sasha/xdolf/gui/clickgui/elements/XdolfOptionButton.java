package com.sasha.xdolf.gui.clickgui.elements;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.gui.clickgui.XdolfClickGUI;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.misc.XdolfMath;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;


public class XdolfOptionButton {

	private XdolfWindow window;
	private String text;
	private int x, y;
	boolean overButton;
	private String toToggle;

	XdolfOptionButton(XdolfWindow window, String text, int x, int y, String toToggle) {
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
		if (this.isAnyWindowDragging()) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		else if (!overButton) {
			var1 = 0f;
			var2 = 0f;
			var3 = 0f;
			var4 = 0f;
		}
		else {
			var1 = 0.5f;
			var2 = 0.5f;
			var3 = 1.5f;
			var4 = 0.7f;
		}
		XdolfMath.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, var1, var2,var3, var4,0f,0f,0f, 0f);
		//ClientUtils.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, 0xF0F0F0FF, 0x00000000);
		
		Fonts.segoe_30.drawStringWithShadow(text, (int)(x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 0) {
			XdolfClickGUI.sendPanelToFront(window);
			Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
			ModuleManager.moduleRegistry.stream().filter(XdolfModule::hasOptions).forEach(mod -> {
			    if (mod.getModuleName().toLowerCase().equalsIgnoreCase(this.window.getTitle() + " options")) {
                    mod.toggleOption(this.toToggle);
                    return;
                }
            });
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 1) {
			XdolfClickGUI.sendPanelToFront(window);
		}
	}

	private boolean isAnyWindowDragging() {
		for (XdolfWindow w : XdolfClickGUI.windowList) {
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
