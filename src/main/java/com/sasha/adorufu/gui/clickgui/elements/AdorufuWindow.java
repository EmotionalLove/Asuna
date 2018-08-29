package com.sasha.adorufu.gui.clickgui.elements;

import com.sasha.adorufu.gui.clickgui.AdorufuClickGUI;
import com.sasha.adorufu.gui.fonts.Fonts;
import com.sasha.adorufu.misc.AdorufuMath;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.adorufu.module.ModuleManager;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Some of the code in this class is based off of the code in Xdolf 3's GUI code, which was based off of Xdolf 1 & 2's code
 * (which was licensed under MIT at the time, and later changed to GPL 3)
 * The repo can be found HERE > https://github.com/LeafHacker/xdolf
 * The repo can also be found at that URL on the wayback machine.
 **/
public class AdorufuWindow {
    private String title;
    private int x, y;
    public int dragX;
    public int dragY;
    private int lastDragX;
    private int lastDragY;
    private boolean isShown;
    private WindowType type;
    protected boolean dragging;

    private ArrayList<AdorufuButton> buttonList = new ArrayList<AdorufuButton>();
    private ArrayList<AdorufuOptionButton> optionButtonList = new ArrayList<>();

    public void drag(int x, int y) {
        dragX = x - lastDragX;
        dragY = y - lastDragY;
    }

    public AdorufuWindow(String title, int x, int y, boolean isShown, WindowType type) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.isShown = isShown;
        this.type = type;
        AdorufuClickGUI.registeredWindows.add(this);
        AdorufuClickGUI.unFocusedWindows.add(this);
    }

    public void drawWindow(int x, int y) {
        if (isShown) {
            GL11.glPushMatrix();
            GL11.glPushAttrib(8256);
            int toAdd = 0;
            if (dragging) {
                drag(x, y);
            }
            AdorufuMath.drawBetterBorderedRect(getXAndDrag(), getYAndDrag(), getXAndDrag() + 100, getYAndDrag() + 13 + ((12 * (buttonList.size() + optionButtonList.size()) + 0.5F) + (0 + (0 != 0 ? 2.5F : 0))) + toAdd, 0.5F, 0f, 0.5f, 1f, 1f, 0f, 0f, 0.5f, 0.3f);
            Fonts.segoe_30.drawCenteredString(title, getXAndDrag() + 50, getYAndDrag() + 1, 0xFFFFFF, true);

            for (AdorufuButton b : buttonList) {
                b.draw();
                if (x >= b.getX() + dragX && y >= b.getY() + dragY && x <= b.getX() + 96 + dragX && y <= b.getY() + 11 + dragY) {
                    b.overButton = true;
                } else {
                    b.overButton = false;
                }
            }
            for (AdorufuOptionButton b : optionButtonList) {
                b.draw();
                if (x >= b.getX() + dragX && y >= b.getY() + dragY && x <= b.getX() + 96 + dragX && y <= b.getY() + 11 + dragY) {
                    b.overButton = true;
                } else {
                    b.overButton = false;
                }
            }
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (AdorufuButton b : buttonList) {
            b.mouseClicked(x, y, button);
        }
        for (AdorufuOptionButton b : optionButtonList) {
            b.mouseClicked(x, y, button);
        }

        if (x >= getXAndDrag() && y >= getYAndDrag() && x <= getXAndDrag() + 100 && y <= getYAndDrag() + 11) {
            AdorufuClickGUI.focusWindow(this);
            dragging = true;
            lastDragX = x - dragX;
            lastDragY = y - dragY;
        }
    }

    public void mouseReleased(int x, int y, int state) {
        if (state == 0) {
            dragging = false;
        }
    }

    public void addButton(AdorufuModule module) {
        buttonList.add(new AdorufuButton(this, module, x + 2, y + 12 + (12 * buttonList.size())));
    }

    public void addOptionsButton(String name) {
        optionButtonList.add(new AdorufuOptionButton(this, name, x + 2, y + 12 + (12 * optionButtonList.size()), name));
    }

    public void loadButtonsFromCategory(AdorufuCategory category) {
        for (AdorufuModule m : ModuleManager.moduleRegistry) {
            if (m.getModuleCategory() == category) {
                addButton(m);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDragX() {
        return dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public int getXAndDrag() {
        return x + dragX;
    }

    public int getYAndDrag() {
        return y + dragY;
    }

    public void setTitle(String s) {
        this.title = s;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shownornot) {
        this.isShown = shownornot;
    }

    public ArrayList<AdorufuButton> getButtonList() {
        return buttonList;
    }

    public WindowType getType() {
        return type;
    }
}
