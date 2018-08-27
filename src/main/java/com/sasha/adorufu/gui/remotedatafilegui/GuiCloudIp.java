package com.sasha.adorufu.gui.remotedatafilegui;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.RemoteDataManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

import static com.sasha.adorufu.gui.remotedatafilegui.GuiCloudLogin.message;

/**
 * Created by Sasha at 7:22 PM on 8/25/2018
 */
public class GuiCloudIp extends GuiScreen {

    public GuiScreen parent;
    private GuiTextField ipBox;
    private GuiPasswordField portbox;
    //private GuiPasswordField passwordConfirmBox;
    private GuiButton connectButton;
    //private GuiButton registerButton;
    private GuiButton backButton;

    public GuiCloudIp(GuiScreen paramScreen) {
        this.parent = paramScreen;
    }

    public GuiCloudIp() {
        this.parent = new GuiMainMenu();
    }


    public void initGui() {
        this.connectButton = new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Connect");
        this.backButton = new GuiButton(2, width / 2 - 100, height / 4 + 96 + 60, "Cancel");
        Keyboard.enableRepeatEvents(true);
        buttonList.add(connectButton);
        buttonList.add(backButton);
        ipBox = new GuiTextField(0, this.fontRenderer, width / 2 - 100, 76 - 25, 200, 20);
        portbox = new GuiPasswordField(2, this.fontRenderer, width / 2 - 100, 116 - 25, 200, 20);
        //altBox = new GuiTextField(4, this.fontRenderer, width / 2 - 100, 156-25, 200, 20);
        ipBox.setMaxStringLength(16);
        portbox.setMaxStringLength(200);
        AdorufuMod.REMOTE_DATA_MANAGER.loggedIn = false;
        message = "fEnter the IP of the Adorufu Data Server you want to connect to";
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        ipBox.updateCursorCounter();
        portbox.updateCursorCounter();
        //passwordConfirmBox.updateCursorCounter();
    }

    public void mouseClicked(int x, int y, int b) throws IOException {
        ipBox.mouseClicked(x, y, b);
        portbox.mouseClicked(x, y, b);
        //passwordConfirmBox.mouseClicked(x,y,b);
        super.mouseClicked(x, y, b);
    }

    @ParametersAreNonnullByDefault
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                if (this.ipBox.getText().equalsIgnoreCase("")) {
                    message = "cThe IP field is empty!";
                    break;
                }
                if (this.portbox.getText().equalsIgnoreCase("")) {
                    message = "cThe port field is empty!";
                    break;
                }
                try {
                    if (Integer.parseInt(this.portbox.getText()) < 0 || Integer.parseInt(this.portbox.getText()) > 60000) {
                        message = "cThe port is invalid!";
                        break;
                    }
                } catch (Exception e) {
                    message = "The port must be a number!";
                    break;
                }
                RemoteDataManager.ip = ipBox.getText();
                RemoteDataManager.port = Integer.parseInt(this.portbox.getText());
                GuiCloudLogin.previouslyConnected = false;
                GuiCloudLogin.message = "aIP set!";
                break;
            case 2:
                mc.displayGuiScreen(parent);
                break;
        }
    }

    protected void keyTyped(char c, int i) {
        ipBox.textboxKeyTyped(c, i);
        portbox.textboxKeyTyped(c, i);
        //passwordConfirmBox.textboxKeyTyped(c, i);
        if (c == '\t') {
            if (ipBox.isFocused()) {
                ipBox.setFocused(false);
                //passwordConfirmBox.setFocused(false);
                portbox.setFocused(true);
            } else if (portbox.isFocused()) {
                ipBox.setFocused(false);
                //passwordConfirmBox.setFocused(true);
                portbox.setFocused(false);
            }/*
            else if (passwordConfirmBox.isFocused()) {
                ipBox.setFocused(false);
                portbox.setFocused(false);
                //passwordConfirmBox.setFocused(false);
            }*/
        }
        if (c == '\r') {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    public void drawScreen(int x, int y, float f) {
        if (AdorufuMod.REMOTE_DATA_MANAGER.loggedIn) {
            mc.displayGuiScreen(new GuiCloudControl(new GuiMainMenu()));
        }
        drawDefaultBackground();
        drawString(this.fontRenderer, "IP Address", width / 2 - 100, 63 - 25, 0xA0A0A0);
        drawString(this.fontRenderer, "Port", width / 2 - 100, 104 - 25, 0xA0A0A0);
        drawCenteredString(this.fontRenderer, "\247" + message, width / 2, height - 40, 0xffffff);
        //drawString(this.fontRenderer, "Confirm Password", width / 2 - 100, 143 - 25, 0xA0A0A0);
        try {
            //passwordConfirmBox.setEnabled(true);
            ipBox.setEnabled(true);
            portbox.setEnabled(true);
            //passwordConfirmBox.drawTextBox();
            ipBox.drawTextBox();
            portbox.drawTextBox();
        } catch (Exception err) {
            err.printStackTrace();
        }
        super.drawScreen(x, y, f);
    }
}
