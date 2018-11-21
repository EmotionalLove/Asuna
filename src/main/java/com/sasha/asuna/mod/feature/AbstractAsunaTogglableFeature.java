/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

package com.sasha.asuna.mod.feature;

import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;
import com.sasha.simplesettings.annotation.SerialiseSuper;

@SerialiseSuper
public abstract class AbstractAsunaTogglableFeature
        extends AbstractAsunaFeature implements IAsunaTogglableFeature {

    private boolean enabled;
    private int keycode;

    public AbstractAsunaTogglableFeature(String name, AsunaCategory category) {
        super(name, category);
    }

    @SafeVarargs public AbstractAsunaTogglableFeature(String name, AsunaCategory category, AsunaFeatureOption<Boolean>... featureOption) {
        super(name, category, featureOption);
    }

    @SafeVarargs public AbstractAsunaTogglableFeature(String name, AsunaCategory category, AsunaFeatureOptionBehaviour behaviour, AsunaFeatureOption<Boolean>... featureOption) {
        super(name, category, behaviour, featureOption);
    }

    @Override
    public int getKeycode() {
        return keycode;
    }

    @Override
    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    @Override
    public final boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void toggleState() {
        this.enabled = !enabled;
        if (this.enabled) {
            this.onEnable();
            return;
        }
        this.onDisable();
    }

    @Override
    public void setState(boolean state, boolean execute) {
        this.enabled = state;
        if (execute) {
            if (state) this.onEnable();
            else this.onDisable();
        }
    }

    public boolean shouldShowInFeatureList() {
        return this.getCategory() != AsunaCategory.NA && this.getCategory() != AsunaCategory.GUI;
    }

}
