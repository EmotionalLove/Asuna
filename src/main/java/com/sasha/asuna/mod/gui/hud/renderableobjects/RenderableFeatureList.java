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

package com.sasha.asuna.mod.gui.hud.renderableobjects;


import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.IAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.impl.FeaturelistRenderableFeature;
import com.sasha.asuna.mod.gui.hud.RenderableObject;
import com.sasha.asuna.mod.gui.hud.ScreenCornerPos;
import com.sasha.asuna.mod.misc.Manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.sasha.asuna.mod.gui.hud.AsunaHUD.sWidth;

public class RenderableFeatureList extends RenderableObject {
    public RenderableFeatureList() {
        super("Featurelist", ScreenCornerPos.RIGHTBOTTOM,
                Manager.Feature.findFeature(FeaturelistRenderableFeature.class));

    }

    @Override
    public void renderObjectLT(int yyy) {
        int count = 0;
        for (IAsunaTogglableFeature module : getValidList(false)) {
            if (module.getSuffix().equals("")) {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName(), 4, (yyy) + (10 * count), 0xffffff);
                count++;
            } else {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName() + module.getSuffix(), 4, (yyy) - (10 * count), 0xffffff);
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
        for (IAsunaTogglableFeature module : getValidList(false)) {
            if (module.isEnabled() && module.getSuffix().equals("")) {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getFeatureName(), sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName()) - 2, (yyy) + (10 * count), 0xffffff);
                count++;
            } else if (module.isEnabled()) {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getFeatureName() + module.getSuffix(), sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName() + module.getSuffix()) - 2, (yyy) + (10 * count), 0xffffff);
                count++;
            }
        }
    }

    @Override
    public void renderObjectRB(int yyy) {
        int count = 0;
        for (IAsunaTogglableFeature module : getValidList(true)) {
            if (module.getSuffix().equals("")) {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName(), sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(module.getColouredName()) - 2, (yyy) - (10 * count), 0xffffff);
                count++;
            } else if (module.isEnabled()) {
                AsunaMod.FONT_MANAGER.segoe_36.drawStringWithShadow("" + module.getColouredName() + module.getSuffix(), sWidth - AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(module.getFeatureName() + module.getSuffix()) - 2, (yyy) - (10 * count), 0xffffff);
                count++;
            }
        }
    }

    private List<IAsunaTogglableFeature> getValidList(boolean reverse) {
        List<IAsunaTogglableFeature> activeFeatureList = new ArrayList<>();
        Manager.Feature.getTogglableFeatures().forEachRemaining(e -> {
            if (e.isEnabled() && e.shouldShowInFeatureList()) {
                activeFeatureList.add(e);
            }
        });

        Comparator<IAsunaTogglableFeature> comparator = Comparator.comparingInt(e ->
                AsunaMod.FONT_MANAGER.segoe_36.getStringWidth(e.getColouredName() + e.getSuffix()));

        activeFeatureList.sort(reverse ? comparator.reversed() : comparator);

        return activeFeatureList;
    }
}
