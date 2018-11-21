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

package com.sasha.asuna.mod.gui.clickgui.helper;

import com.sasha.asuna.mod.feature.IAsunaFeature;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import static com.sasha.asuna.mod.AsunaMod.minecraft;

public class OptionToggler implements Runnable, IToggler {

    private IAsunaFeature m;
    private String optionName;

    public OptionToggler(IAsunaFeature m, String optionName) {
        this.m = m;
        this.optionName = optionName;
    }

    @Override
    public void run() {
        minecraft.world.playSound(minecraft.player.posX, minecraft.player.posY, minecraft.player.posZ, SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 1f, 1f, false);
        m.setOption(optionName, !m.getOption(optionName));
    }

    @Override
    public IAsunaFeature getMod() {
        return m;
    }

    public boolean isTrue() {
        return m.getOption(optionName);
    }


}
