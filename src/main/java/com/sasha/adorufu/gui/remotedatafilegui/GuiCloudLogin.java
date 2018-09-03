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

package com.sasha.adorufu.gui.remotedatafilegui;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.AdorufuDataClient;
import com.sasha.adorufu.remote.packet.AttemptLoginPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

/**
 * Created by Sasha at 7:22 PM on 8/25/2018
 */
public class GuiCloudLogin extends GuiScreen {

    public GuiScreen parent;
    private GuiTextField usernameBox;
    private GuiPasswordField passwordBox;
    //private GuiPasswordField passwordConfirmBox;
    private GuiButton loginButton;
    private GuiButton registerButton;
    private GuiButton backButton;
    private GuiButton ipButton;
    public static String message;
    public static boolean previouslyConnected = false;

    public GuiCloudLogin(GuiScreen paramScreen)
    {
        this.parent = paramScreen;
    }
    public GuiCloudLogin()
    {
        this.parent = new GuiMainMenu();
    }


    public void initGui()
    {
        this.loginButton = new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Login");
        this.registerButton = new GuiButton(2, width / 2 - 100, height / 4 + 96 + 36, "Register");
        this.backButton = new GuiButton(3, width / 2 - 100, height / 4 + 96 + 60, "Back");
        this.ipButton = new GuiButton(4, 5, 5, fontRenderer.getStringWidth("Advanced") + 10, 20, "Advanced");
        Keyboard.enableRepeatEvents(true);
        buttonList.add(loginButton);
        buttonList.add(registerButton);
        buttonList.add(backButton);
        buttonList.add(ipButton);
        usernameBox = new GuiTextField(0, this.fontRenderer, width / 2 - 100, 76 - 25, 200, 20);
        passwordBox = new GuiPasswordField(2, this.fontRenderer, width / 2 - 100, 116 - 25, 200, 20);
        //altBox = new GuiTextField(4, this.fontRenderer, width / 2 - 100, 156-25, 200, 20);
        usernameBox.setMaxStringLength(16);
        passwordBox.setMaxStringLength(200);
        if (!previouslyConnected) {
            AdorufuMod.REMOTE_DATA_MANAGER.connect();
            previouslyConnected = true;
        }
        AdorufuMod.REMOTE_DATA_MANAGER.loggedIn = false;
        message = "fUse your Adorufu Cloud credentials to log in, or, create an account";
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen()
    {
        usernameBox.updateCursorCounter();
        passwordBox.updateCursorCounter();
        //passwordConfirmBox.updateCursorCounter();
    }

    public void mouseClicked(int x, int y, int b) throws IOException
    {
        usernameBox.mouseClicked(x, y, b);
        passwordBox.mouseClicked(x, y, b);
        //passwordConfirmBox.mouseClicked(x,y,b);
        super.mouseClicked(x, y, b);
    }

    @ParametersAreNonnullByDefault
    public void actionPerformed(GuiButton button)
    {
        switch (button.id) {
            case 1:
                if (this.usernameBox.getText().equalsIgnoreCase("")) {
                    message = "cThe username field is empty!";
                    break;
                }
                if (this.passwordBox.getText().equalsIgnoreCase("")) {
                    message = "cThe password field is empty!";
                    break;
                }
                /*if (this.passwordConfirmBox.getText().equalsIgnoreCase("")) {
                    message = "\247cThe password confirmation field is empty!";
                    break;
                }*/
                AttemptLoginPacket pck = new AttemptLoginPacket(AdorufuDataClient.processor);
                pck.setCredentials(this.usernameBox.getText(), this.passwordBox.getText());
                pck.dispatchPck();
                break;
            case 2:
                mc.displayGuiScreen(new GuiCloudRegister(new GuiMainMenu()));
                break;
            case 3:
                mc.displayGuiScreen(parent);
                break;
            case 4:
                mc.displayGuiScreen(new GuiCloudIp(this));
        }
        /*
        if (button.id == 1) {
            
        }
        else if (button.id == 2) {
            mc.displayGuiScreen(parent);
        }
        else if (button.id == 3) {
            Authenticator authh = new Authenticator(altBox.getText().substring(0, altBox.getText().indexOf(":")), altBox.getText().substring(altBox.getText().indexOf(":") + 1, altBox.getText().length()));
            if (authh.login()) {
                mc.session = authh.session;
            }
            new Thread(() -> {
                for (int i = 30; i >= 0; i--) {
                    timer = i;
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }*/
    }
    protected void keyTyped(char c, int i)
    {
        usernameBox.textboxKeyTyped(c, i);
        passwordBox.textboxKeyTyped(c, i);
        //passwordConfirmBox.textboxKeyTyped(c, i);
        if(c == '\t')
        {
            if(usernameBox.isFocused())
            {
                usernameBox.setFocused(false);
                //passwordConfirmBox.setFocused(false);
                passwordBox.setFocused(true);
            }
            else if(passwordBox.isFocused())
            {
                usernameBox.setFocused(false);
                //passwordConfirmBox.setFocused(true);
                passwordBox.setFocused(false);
            }/*
            else if (passwordConfirmBox.isFocused()) {
                usernameBox.setFocused(false);
                passwordBox.setFocused(false);
                //passwordConfirmBox.setFocused(false);
            }*/
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    public void drawScreen(int x, int y, float f)
    {
        if (AdorufuMod.REMOTE_DATA_MANAGER.loggedIn) {
            mc.displayGuiScreen(new GuiCloudControl(new GuiMainMenu()));
        }
        drawDefaultBackground();
        drawString(this.fontRenderer, "Username", width / 2 - 100, 63 - 25, 0xA0A0A0);
        drawString(this.fontRenderer, "Password", width / 2 - 100, 104 - 25, 0xA0A0A0);
        drawCenteredString(this.fontRenderer, "\247" + message, width / 2, height - 40, 0xffffff);
        //drawString(this.fontRenderer, "Confirm Password", width / 2 - 100, 143 - 25, 0xA0A0A0);
        try{
            //passwordConfirmBox.setEnabled(true);
            usernameBox.setEnabled(true);
            passwordBox.setEnabled(true);
            //passwordConfirmBox.drawTextBox();
            usernameBox.drawTextBox();
            passwordBox.drawTextBox();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        super.drawScreen(x, y, f);
    }
}
