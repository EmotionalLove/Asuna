package com.sasha.adorufu.gui.remotedatafilegui;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.remote.AdorufuDataClient;
import com.sasha.adorufu.remote.packet.AttemptRegisterPacket;
import com.sasha.adorufu.remote.packet.events.RegisterResponseEvent;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

/**
 * Created by Sasha at 7:22 PM on 8/25/2018
 */
public class GuiCloudRegister extends GuiScreen {

    public GuiScreen parent;
    private GuiTextField usernameBox;
    private GuiPasswordField passwordBox;
    private GuiPasswordField passwordConfirmBox;
    private GuiButton loginButton;
    //private GuiButton registerButton;
    private GuiButton backButton;

    public GuiCloudRegister(GuiScreen paramScreen)
    {
        this.parent = paramScreen;
    }

    public static class GuiCloudRegisterEventHandler implements SimpleListener {

    }

    public void initGui()
    {
        this.loginButton = new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Register");
        this.backButton = new GuiButton(2, width / 2 - 100, height / 4 + 96 + 36, "Back");
        //this.backButton = new GuiButton(3, width / 2 - 100, height / 4 + 96 + 60, "Back");
        Keyboard.enableRepeatEvents(true);
        buttonList.add(loginButton);
        buttonList.add(backButton);
        usernameBox = new GuiTextField(0, this.fontRenderer, width / 2 - 100, 76 - 25, 200, 20);
        passwordBox = new GuiPasswordField(1, this.fontRenderer, width / 2 - 100, 116 - 25, 200, 20);
        passwordConfirmBox = new GuiPasswordField(2, this.fontRenderer, width / 2 - 100, 156 - 25, 200, 20);
        //altBox = new GuiTextField(4, this.fontRenderer, width / 2 - 100, 156-25, 200, 20);
        usernameBox.setMaxStringLength(16);
        passwordBox.setMaxStringLength(200);
        passwordConfirmBox.setMaxStringLength(200);
        GuiCloudLogin.message = "fCreate a new Adorufu Cloud account.";
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen()
    {
        usernameBox.updateCursorCounter();
        passwordBox.updateCursorCounter();
        passwordConfirmBox.updateCursorCounter();
    }

    public void mouseClicked(int x, int y, int b) throws IOException
    {
        usernameBox.mouseClicked(x, y, b);
        passwordBox.mouseClicked(x, y, b);
        passwordConfirmBox.mouseClicked(x,y,b);
        super.mouseClicked(x, y, b);
    }

    @ParametersAreNonnullByDefault
    public void actionPerformed(GuiButton button)
    {
        switch (button.id) {
            case 1:
                if (this.usernameBox.getText().equalsIgnoreCase("")) {
                    GuiCloudLogin.message = "\247cThe username field is empty!";
                    break;
                }
                if (this.passwordBox.getText().equalsIgnoreCase("")) {
                    GuiCloudLogin.message = "cThe password field is empty!";
                    break;
                }
                if (this.passwordConfirmBox.getText().equalsIgnoreCase("")) {
                    GuiCloudLogin.message = "cThe password confirmation field is empty!";
                    break;
                }
                AttemptRegisterPacket pck = new AttemptRegisterPacket(AdorufuDataClient.processor);
                pck.setCredentials(this.usernameBox.getText(), this.passwordBox.getText(), this.passwordConfirmBox.getText());
                pck.dispatchPck();
                break;
            case 2:
                mc.displayGuiScreen(parent);
                break;
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
        passwordConfirmBox.textboxKeyTyped(c, i);
        if(c == '\t')
        {
            if(usernameBox.isFocused())
            {
                usernameBox.setFocused(false);
                passwordConfirmBox.setFocused(false);
                passwordBox.setFocused(true);
            }
            else if(passwordBox.isFocused())
            {
                usernameBox.setFocused(false);
                passwordConfirmBox.setFocused(true);
                passwordBox.setFocused(false);
            }
            else if (passwordConfirmBox.isFocused()) {
                usernameBox.setFocused(false);
                passwordBox.setFocused(false);
                passwordConfirmBox.setFocused(false);
            }
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    public void drawScreen(int x, int y, float f)
    {
        drawDefaultBackground();
        drawString(this.fontRenderer, "Username", width / 2 - 100, 63 - 25, 0xA0A0A0);
        drawString(this.fontRenderer, "Password", width / 2 - 100, 104 - 25, 0xA0A0A0);
        drawCenteredString(this.fontRenderer, "\247" + GuiCloudLogin.message, width / 2, height - 40, 0xffffff);
        drawString(this.fontRenderer, "Confirm Password", width / 2 - 100, 143 - 25, 0xA0A0A0);
        try{
            passwordConfirmBox.setEnabled(true);
            usernameBox.setEnabled(true);
            passwordBox.setEnabled(true);
            passwordConfirmBox.drawTextBox();
            usernameBox.drawTextBox();
            passwordBox.drawTextBox();
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        super.drawScreen(x, y, f);
    }
}
