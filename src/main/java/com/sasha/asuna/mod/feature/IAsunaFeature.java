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

package com.sasha.asuna.mod.feature;

import com.sasha.asuna.mod.feature.option.AsunaFeatureOption;
import com.sasha.asuna.mod.feature.option.AsunaFeatureOptionBehaviour;

import java.util.List;
import java.util.Map;

public interface IAsunaFeature {

    default void onLoad() {
        //
    }

    AsunaCategory getCategory();

    List<AsunaFeatureOption<Boolean>> getOptions();

    AsunaFeatureOptionBehaviour getOptionBehaviour();

    void setOption(String key, boolean state);

    boolean getOption(String key);

    Map<String, Boolean> getFormattableOptionsMap();

    boolean hasOptions();

    String getColouredName();

    String getFeatureName();

    void setSuffix(String s);

    void setSuffix(String[] s);

    String getSuffix();

    void setSuffix(Map<String, Boolean> boolMap);

}
