/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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

package com.sasha.asuna.api;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.eventsys.SimpleEventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * Created by Sasha at 9:07 AM on 9/17/2018
 */
public abstract class AsunaPlugin {

    private String pluginName;
    private String pluginDescription;
    private String pluginAuthor;

    private boolean infoSet = false;

    public void onEnable(){}

    public void onDisable(){}

    public void onFeatureRegistration(){}

    public void onRenderableRegistration(){}

    public void onCommandRegistration(){}

    public SimpleEventManager getEventManager() {
        return AsunaMod.EVENT_MANAGER;
    }

    protected void setInfo(String pluginName, String pluginDescription, String pluginAuthor) {
        if (infoSet) {
            return;
        }
        this.pluginAuthor = pluginAuthor;
        this.pluginDescription = pluginDescription;
        this.pluginName = pluginName;
        infoSet = true;
    }

    public String getPluginAuthor() {
        return pluginAuthor;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginDescription() {
        return pluginDescription;
    }

    public EntityPlayerSP getPlayer() {
        return AsunaMod.minecraft.player;
    }

    public Minecraft getMinecraft() {
        return AsunaMod.minecraft;
    }
}
