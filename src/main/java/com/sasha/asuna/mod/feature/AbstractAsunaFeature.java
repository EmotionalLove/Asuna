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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class AbstractAsunaFeature implements IAsunaFeature {

    private String name;
    private String suffix = "";
    private AsunaCategory category;
    private List<AsunaFeatureOption<Boolean>> featureOptions = new ArrayList<>();
    private AsunaFeatureOptionBehaviour behaviour;

    /**
     * Create a Feature without any options.
     */
    public AbstractAsunaFeature(String name, AsunaCategory category) {
        this.name = name;
        this.category = category;
        this.behaviour = new AsunaFeatureOptionBehaviour(false);
    }

    /**
     * Create a Feature with options using the default behaviour.
     */
    @SafeVarargs public AbstractAsunaFeature(String name, AsunaCategory category, AsunaFeatureOption<Boolean>... featureOption) {
        this.name = name;
        this.category = category;
        this.featureOptions = Arrays.asList(featureOption);
        this.behaviour = new AsunaFeatureOptionBehaviour(false);
    }

    /**
     * Create a Feature with options using a defined behaviour.
     */
    @SafeVarargs public AbstractAsunaFeature(String name, AsunaCategory category, AsunaFeatureOptionBehaviour behaviour, AsunaFeatureOption<Boolean>... featureOption) {
        this.name = name;
        this.category = category;
        this.featureOptions = Arrays.asList(featureOption);
        this.behaviour = behaviour;
    }

    public AsunaCategory getCategory() {
        return this.category;
    }

    public AsunaFeatureOptionBehaviour getOptionBehaviour() {
        return behaviour;
    }

    public List<AsunaFeatureOption<Boolean>> getOptions() {
        return this.featureOptions;
    }

    public void setOption(String key, boolean state) {
        if (this.getOptionBehaviour().isUsingModeBehaviour() && !state) return;
        for (AsunaFeatureOption<Boolean> option : this.getOptions()) {
            if (this.getOptionBehaviour().isUsingModeBehaviour() && !option.getIdentifer().equalsIgnoreCase(key)) {
                option.setStatus(false);
            }
            if (option.getIdentifer().equalsIgnoreCase(key)) {
                option.setStatus(state);
            }
        }
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(Map<String, Boolean> boolMap) {
        StringBuilder b = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();
        boolMap.entrySet().stream().filter(Map.Entry::getValue).forEach(strBool -> {
            if (counter.get() == 0) {
                b.append(strBool.getKey());
                counter.getAndIncrement();
                return;
            }
            b.append(", ").append(strBool.getKey());
        });
        if (counter.get() == 0) {
            b.append("\247cNone");
        }
        this.suffix = " \2478[\2477" + b.toString() + "\2478]";
    }

    public boolean getOption(String key) {
        for (AsunaFeatureOption<Boolean> option : this.getOptions()) {
            if (option.getIdentifer().equalsIgnoreCase(key)) {
                return option.getStatus();
            }
        }
        return false;
    }

    //todo
    public Map<String, Boolean> getFormattableOptionsMap() {
        return this.featureOptions
                .stream()
                .collect(
                        Collectors
                                .toMap
                                        (AsunaFeatureOption::getIdentifer,
                                                AsunaFeatureOption::getStatus));
    }

    public boolean hasOptions() {
        return !this.featureOptions.isEmpty();
    }

    public String getFeatureName() {
        return this.name;
    }

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
            case RENDER:
                colour += "d";
                break;
            default:
                colour += "8";
        }
        colour += name;
        return colour;
    }

    public void setSuffix(String s) {
        this.suffix = " \2478[\2477" + s + "\2478]";
    }

    public void setSuffix(String[] s) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i == 0) {
                b.append(s[i]);
                continue;
            }
            b.append(", ").append(s[i]);
        }
        this.suffix = " \2478[\2477" + b.toString() + "\2478]";
    }

}
