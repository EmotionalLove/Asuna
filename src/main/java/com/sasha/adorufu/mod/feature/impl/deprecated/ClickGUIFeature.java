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

package com.sasha.adorufu.mod.feature.impl.deprecated;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.AbstractAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.AdorufuCategory;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import com.sasha.adorufu.mod.feature.annotation.PostToggleExec;
import com.sasha.adorufu.mod.gui.clickgui.AdorufuClickGUI;


/**
 * Created by Sasha on 11/08/2018 at 10:27 AM
 **/
@PostToggleExec
@FeatureInfo(description = "Displays the Clickgui")
public class ClickGUIFeature extends AbstractAdorufuTogglableFeature {
    public ClickGUIFeature() {
        super("ClickGUI", AdorufuCategory.NA);
    }

    @Override
    public void onEnable(){
        AdorufuMod.minecraft.displayGuiScreen(new AdorufuClickGUI());
        this.toggleState();
    }

    @Override
    public void onDisable() {

    }
}
