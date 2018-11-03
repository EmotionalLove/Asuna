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

package com.sasha.adorufu.mod.gui.hud.renderableobjects;


import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.IAdorufuFeature;
import com.sasha.adorufu.mod.feature.IAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.impl.FeaturelistRenderableFeature;
import com.sasha.adorufu.mod.gui.hud.RenderableObject;
import com.sasha.adorufu.mod.gui.hud.ScreenCornerPos;
import com.sasha.adorufu.mod.misc.Manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sasha.adorufu.mod.gui.hud.AdorufuHUD.sWidth;

public class RenderableFeatureList extends RenderableObject {
    public RenderableFeatureList() {
        super("Featurelist", ScreenCornerPos.RIGHTBOTTOM,
                Manager.Feature.findFeature(FeaturelistRenderableFeature.class));

    }

    @Override
    public void renderObjectLT(int yyy) {
        int count = 0;
        for (IAdorufuTogglableFeature module : getValidList(false)) {
            if (module.getSuffix().equals("")) {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName(), 4, (yyy) + (10 * count), 0xffffff);
                count++;
            } else {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName() + module.getSuffix(), 4, (yyy) - (10 * count), 0xffffff);
                count++;
            }
        }
    }

    @Override
    public void renderObjectLB(int yyy) {
        // TODO
    }

    @Override
    public void renderObjectRT(int yyy) {
        int count = 0;
        for (IAdorufuTogglableFeature module : getValidList(false)) {
            if (module.isEnabled() && module.getSuffix().equals("")) {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getFeatureName(), sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName()) - 2, (yyy) + (10 * count), 0xffffff);
                count++;
            } else if (module.isEnabled()) {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getFeatureName() + module.getSuffix(), sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName() + module.getSuffix()) - 2, (yyy) + (10 * count), 0xffffff);
                count++;
            }
        }
    }

    @Override
    public void renderObjectRB(int yyy) {
        int count = 0;
        for (IAdorufuTogglableFeature module : getValidList(true)) {
            if (module.getSuffix().equals("")) {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName(), sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(module.getColouredName()) - 2, (yyy) - (10 * count), 0xffffff);
                count++;
            } else if (module.isEnabled()) {
                AdorufuMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName() + module.getSuffix(), sWidth - AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName() + module.getSuffix()) - 2, (yyy) - (10 * count), 0xffffff);
                count++;
            }
        }
    }

    private List<IAdorufuTogglableFeature> getValidList(boolean reverse) {
        List<IAdorufuTogglableFeature> activeFeatureList = new ArrayList<>();
        Manager.Feature.getTogglableFeatures().forEachRemaining(e -> {
            if (e.isEnabled()) {
                activeFeatureList.add(e);
            }
        });
        return !reverse ? activeFeatureList
                .stream()
                .sorted(Comparator
                        .comparing(e -> AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth
                                (e.getColouredName() + e.getSuffix())))
                .collect(Collectors.toList())
        :
        activeFeatureList
                .stream()
                .sorted(Comparator.comparing(e -> AdorufuMod.FONT_MANAGER.segoe_36.getStringWidth
                        (((IAdorufuFeature)e).getColouredName() + ((IAdorufuFeature)e).getSuffix())).reversed())
                .collect(Collectors.toList());
    }
}
