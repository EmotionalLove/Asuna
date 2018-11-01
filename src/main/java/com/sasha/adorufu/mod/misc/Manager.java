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

package com.sasha.adorufu.mod.misc;

import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.IAdorufuFeature;
import com.sasha.adorufu.mod.feature.IAdorufuRenderableFeature;
import com.sasha.adorufu.mod.feature.IAdorufuTickableFeature;
import com.sasha.adorufu.mod.feature.IAdorufuTogglableFeature;
import com.sasha.adorufu.mod.gui.hud.RenderableObject;
import com.sasha.eventsys.SimpleListener;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Sasha at 9:09 AM on 9/17/2018
 */
public class Manager {

    public static class Feature implements SimpleListener {

        public static List<IAdorufuFeature> featureRegistry = new ArrayList<>();

        public static void registerFeature(IAdorufuFeature feature) {
            featureRegistry.add(feature);
            feature.onLoad();
        }

        public static Iterator<IAdorufuFeature> getTogglableFeatures() {
            return featureRegistry
                    .stream()
                    .filter(e->e instanceof IAdorufuTogglableFeature)
                    .iterator();
        }

        /**
         * Tick all Tickable features.
         * If a feature is togglable, it will only tick if it's enabled.
         */
        public static void tickFeatures() {
            featureRegistry
                    .stream()
                    .filter(feature -> feature instanceof IAdorufuTickableFeature)
                    .forEach(tickableFeature -> {
                        if (tickableFeature instanceof IAdorufuTogglableFeature
                                &&
                                ((IAdorufuTogglableFeature) tickableFeature).isEnabled()) {
                            ((IAdorufuTickableFeature) tickableFeature).onTick();
                            return;
                        }
                        ((IAdorufuTickableFeature) tickableFeature).onTick();
                    });
        }
        public static void renderFeatures() {
            featureRegistry
                    .stream()
                    .filter(feature -> feature instanceof IAdorufuRenderableFeature)
                    .forEach(renderableFeature -> {
                        if (renderableFeature instanceof IAdorufuTogglableFeature
                        &&
                        ((IAdorufuTogglableFeature) renderableFeature).isEnabled()) {
                            ((IAdorufuRenderableFeature) renderableFeature).onRender();
                            return;
                        }
                        ((IAdorufuRenderableFeature) renderableFeature).onRender();
                    });
        }

        public static IAdorufuFeature findFeature(Class<? extends IAdorufuFeature> featureClass) {
            for (IAdorufuFeature feature : featureRegistry) {
                if(feature.getClass() == featureClass) return feature;
            }
            return null;
        }

        public static boolean isFeatureEnabled(Class<? extends IAdorufuTogglableFeature> featureClass) {
            for (IAdorufuFeature feature : featureRegistry) {
                if (!(feature instanceof IAdorufuTogglableFeature)) continue;
                if (feature.getClass() == featureClass && ((IAdorufuTogglableFeature) feature).isEnabled()) return true;
            }
            return false;
        }

    }

    public static class Renderable implements SimpleListener {
        public static ArrayList<RenderableObject> renderableRegistry = new ArrayList<>();

        public static void register(RenderableObject robj) {
            renderableRegistry.add(robj);
            AdorufuMod.SETTING_CLASSES.add(robj);
        }
    }

    public static Set<Class> getClassesInPackage(String pckge, Class sub) {
        Reflections reflections = new Reflections(pckge);
        return reflections.getSubTypesOf(sub);
    }

}
