package com.sasha.xdolf.gui.clickgui.elements;


import com.sasha.xdolf.gui.clickgui.XdolfClickGUI;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.misc.XdolfMath;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import static com.sasha.xdolf.gui.clickgui.XdolfClickGUI.getWindowList;


public class XdolfButton {

	static Minecraft mc = Minecraft.getMinecraft();
	
	private XdolfWindow window;
	private XdolfModule mod;
	private int x, y;
	boolean overButton;
	private String displayName;
	
	XdolfButton(XdolfWindow window, XdolfModule mod, int x, int y) {
		this.window = window;
		this.mod = mod;
		this.x = x;
		this.y = y;
		this.displayName = mod.getModuleName();
		if (mod.hasOptions()) {
            class someThing extends XdolfWindow {
                public someThing() {
                    super(mod.getModuleName() + " Options", 2, 12, false, WindowType.OPTION);
                    mod.getModuleOptionsMap().forEach((str,bool) -> this.addOptionsButton(str));
                }
            }
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
		XdolfMath.drawBetterBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, var1, var2,var3, var4,1f,1f,1f, varia);
		Fonts.segoe_30.drawStringWithShadow(displayName, (x + 4 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
		 if (mod.hasOptions()) {
			 this.setDisplayName(mod.getModuleName() + " " + "\247" + "8[...]");
		 }
	}
	
	public void mouseClicked(int x, int y, int button) { //TODO: make sure only one button gets toggled even if the windows are overlapping
		if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 0) {
			XdolfClickGUI.sendPanelToFront(window);
			mod.toggle();
			mc.world.playSound(mc.player.posX, mc.player.posY, mc.player.posZ, SoundEvents.UI_BUTTON_CLICK , SoundCategory.AMBIENT, 1f, 1f, false);
		} else if (x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen() && button == 1) {
			XdolfClickGUI.sendPanelToFront(window);
			mc.world.playSound(mc.player.posX, mc.player.posY, mc.player.posZ, SoundEvents.BLOCK_CHEST_OPEN ,SoundCategory.MASTER, 1f, 1f, false);
			if (mod.hasOptions()) {
				for (XdolfWindow w : getWindowList()) {
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
		for (XdolfWindow w : XdolfClickGUI.windowList) {
			if (w.dragging) {
				return true;
			}
		}
		return false;
	}
	
	public XdolfModule getModule() {
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
