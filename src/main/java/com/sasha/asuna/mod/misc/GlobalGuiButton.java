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

package com.sasha.asuna.mod.misc;

import net.minecraft.client.gui.GuiButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GlobalGuiButton extends GuiButton {

    private String identifier;
    private Consumer<GlobalGuiButton> consumer;

    public GlobalGuiButton(String identifier, int buttonId, int x, int y, String buttonText, Consumer<GlobalGuiButton> consumer) {
        super(buttonId, x, y, buttonText);
        this.identifier = identifier;
        this.consumer = consumer;
    }

    public GlobalGuiButton(String identifier, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Consumer<GlobalGuiButton> consumer) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.identifier = identifier;
        this.consumer = consumer;
    }

    public void onClick() {
        if (consumer != null) consumer.accept(this);
    }

    public static class Manager {
        public List<GlobalGuiButton> globalButtons = new ArrayList<>();

        public void performAction(GlobalGuiButton btn) {
            globalButtons.forEach(b -> {
                if (b.identifier.equals(btn.identifier)) {
                    b.onClick();
                }
            });
        }

    }

}
