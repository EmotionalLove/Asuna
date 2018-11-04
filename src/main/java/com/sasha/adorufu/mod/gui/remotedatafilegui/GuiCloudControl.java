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

package com.sasha.adorufu.mod.gui.remotedatafilegui;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.remote.AdorufuDataClient;
import com.sasha.adorufu.mod.remote.packet.RequestSaveDataFilePacket;
import com.sasha.adorufu.mod.remote.packet.RetrieveDataFileRequestPacket;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

import static com.sasha.adorufu.mod.gui.remotedatafilegui.GuiCloudLogin.message;

/**
 * Created by Sasha at 7:22 PM on 8/25/2018
 */
public class GuiCloudControl extends GuiScreen {

    public GuiScreen parent;
    //private GuiPasswordField passwordConfirmBox;
    private GuiButton retrieveButton;
    private GuiButton saveButton;
    private GuiButton backButton;
    //private static String message = "fYou can save or retrieve your datafile from the server.";

    public GuiCloudControl(GuiScreen paramScreen) {
        this.parent = paramScreen;
    }

    public void initGui() {
        message = "fYou can save or retrieve your datafile from the server.";
        this.retrieveButton = new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Retrieve Data File");
        this.saveButton = new GuiButton(2, width / 2 - 100, height / 4 + 96 + 36, "Save Data File");
        this.backButton = new GuiButton(3, width / 2 - 100, height / 4 + 96 + 60, "Log off");
        Keyboard.enableRepeatEvents(true);
        buttonList.add(retrieveButton);
        buttonList.add(saveButton);
        buttonList.add(backButton);
        //usernameBox = new GuiTextField(0, this.fontRenderer, width / 2 - 100, 76 - 25, 200, 20);
        //passwordBox = new GuiPasswordField(2, this.fontRenderer, width / 2 - 100, 116 - 25, 200, 20);
        //altBox = new GuiTextField(4, this.fontRenderer, width / 2 - 100, 156-25, 200, 20);
        //usernameBox.setMaxStringLength(16);
        //passwordBox.setMaxStringLength(200);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
    }

    public void mouseClicked(int x, int y, int b) throws IOException {
        super.mouseClicked(x, y, b);
    }

    @ParametersAreNonnullByDefault
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                RetrieveDataFileRequestPacket pck = new RetrieveDataFileRequestPacket(AdorufuDataClient.processor, AdorufuMod.REMOTE_DATA_MANAGER.adorufuSessionId);
                pck.dispatchPck();
                message = "bRetrieving the data file...";
                break;
            case 2:
                RequestSaveDataFilePacket ssdfp = new RequestSaveDataFilePacket(AdorufuDataClient.processor, AdorufuMod.REMOTE_DATA_MANAGER.adorufuSessionId);
                ssdfp.dispatchPck();
                message = "bSaving the data file...";
                break;
            case 3:
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

    protected void keyTyped(char c, int i) {
    }

    public void drawScreen(int x, int y, float f) {
        drawDefaultBackground();
        //drawString(this.fontRenderer, "Username", width / 2 - 100, 63 - 25, 0xA0A0A0);
        //drawString(this.fontRenderer, "Password", width / 2 - 100, 104 - 25, 0xA0A0A0);
        drawCenteredString(this.fontRenderer, "\247" + message, width / 2, height - 40, 0xffffff);
        //drawString(this.fontRenderer, "Confirm Password", width / 2 - 100, 143 - 25, 0xA0A0A0);
        super.drawScreen(x, y, f);
    }

    public static class GuiCloudLoginEventHandler implements SimpleListener {

    }
}
