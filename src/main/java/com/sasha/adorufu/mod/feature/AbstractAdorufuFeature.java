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

package com.sasha.adorufu.mod.feature;

import com.sasha.adorufu.mod.feature.option.AdorufuFeatureOption;
import com.sasha.simplesettings.annotation.SerialiseSuper;
import com.sasha.simplesettings.annotation.Transiant;

@SerialiseSuper
public abstract class AbstractAdorufuFeature implements IAdorufuFeature {

    @Transiant
    private String name;
    @Transiant
    private AdorufuCategory category;
    @Transiant
    private AdorufuFeatureOption featureOption;

    public String getColouredName() {
        String colour = "\247";
        switch (category) {
            case COMBAT:
                colour += "4";
                break;
            case CHAT:
                colour += "3";
                break;
            case GUI:
                colour += "7";
                break;
            case MISC:
                colour += "b";
                break;
            case MOVEMENT:
                colour += "6";
                break;
            default:
                colour += "8";
        }
        colour += name;
        return colour;
    }

}
