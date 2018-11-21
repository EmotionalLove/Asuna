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

package com.sasha.asuna.mod.events.asuna;

import com.sasha.asuna.mod.feature.IAsunaTogglableFeature;
import com.sasha.asuna.mod.misc.ModuleState;
import com.sasha.eventsys.SimpleCancellableEvent;

/**
 * Created by Sasha on 08/08/2018 at 9:18 AM
 **/
public class AsunaTogglableFeatureTogglePreEvent extends SimpleCancellableEvent {
    private IAsunaTogglableFeature toggledModule;
    private ModuleState toggleState;

    public AsunaTogglableFeatureTogglePreEvent(IAsunaTogglableFeature toggledModule, ModuleState toggleState) {
        this.toggledModule = toggledModule;
        this.toggleState = toggleState;
    }

    public ModuleState getToggleState() {
        return toggleState;
    }

    public IAsunaTogglableFeature getToggledModule() {
        return toggledModule;
    }
}

