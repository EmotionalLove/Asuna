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

package com.sasha.adorufu.mod.feature.option;

import com.sasha.adorufu.mod.feature.IAdorufuFeature;
import com.sasha.simplesettings.annotation.SerialiseSuper;
import com.sasha.simplesettings.annotation.Transiant;

@SerialiseSuper
public class AdorufuFeatureOption {

    @Transiant private IAdorufuFeature feature;
    private String identifer;
    private boolean status;

    public AdorufuFeatureOption(IAdorufuFeature feature, String identifer, boolean def) {
        this.feature = feature;
        this.identifer = identifer;
        this.status = def;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void toggleStatus() {
        this.status = !status;
    }

    public boolean isTrue() {
        return status;
    }

    public String getIdentifer() {
        return identifer;
    }

    public IAdorufuFeature getFeature() {
        return feature;
    }
}
