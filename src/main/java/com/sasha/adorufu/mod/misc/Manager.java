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

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.sasha.adorufu.mod.AdorufuMod;
import com.sasha.adorufu.mod.feature.IAdorufuFeature;
import com.sasha.adorufu.mod.feature.IAdorufuRenderableFeature;
import com.sasha.adorufu.mod.feature.IAdorufuTickableFeature;
import com.sasha.adorufu.mod.feature.IAdorufuTogglableFeature;
import com.sasha.adorufu.mod.feature.annotation.FeatureInfo;
import com.sasha.adorufu.mod.gui.hud.RenderableObject;
import com.sasha.eventsys.SimpleListener;
import org.reflections.util.ClasspathHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sasha at 9:09 AM on 9/17/2018
 */
public class Manager {

    private static List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    static {
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

    }

    public static class Feature implements SimpleListener {

        public static List<IAdorufuFeature> featureRegistry = new ArrayList<>();

        public static void registerFeature(IAdorufuFeature feature) {
            featureRegistry.add(feature);
            feature.onLoad();
        }

        public static Iterator<IAdorufuTogglableFeature> getTogglableFeatures() {
            return featureRegistry
                    .stream()
                    .filter(IAdorufuTogglableFeature.class::isInstance)
                    .map(IAdorufuTogglableFeature.class::cast)
                    .iterator();
        }

        /**
         * Tick all Tickable features.
         * If a feature is togglable, it will only tick if it's enabled.
         */
        public static void tickFeatures() {
            featureRegistry
                    .stream()
                    .filter(IAdorufuTickableFeature.class::isInstance)
                    .map(IAdorufuTickableFeature.class::cast)
                    .forEach(feature -> {
                        if (feature instanceof IAdorufuTogglableFeature) {
                            if (((IAdorufuTogglableFeature) feature).isEnabled()) {
                                feature.onTick();
                            }
                            return;
                        }
                        feature.onTick();
                    });
        }

        public static void renderFeatures() {
            featureRegistry
                    .stream()
                    .filter(IAdorufuRenderableFeature.class::isInstance)
                    .map(IAdorufuRenderableFeature.class::cast)
                    .forEach(feature -> {
                        if (feature instanceof IAdorufuTogglableFeature) {
                            if (((IAdorufuTogglableFeature) feature).isEnabled()) {
                                feature.onRender();
                            }
                            return;
                        }
                        feature.onRender();
                    });
        }

        public static String getFeatureInfo(Class<? extends IAdorufuFeature> featureClass) {
            if (featureClass.getAnnotation(FeatureInfo.class) == null) {
                return "No information provided for this feature!";
            }
            FeatureInfo info = featureClass.getAnnotation(FeatureInfo.class);
            return info.description();
        }

        public static <T extends IAdorufuFeature> T findFeature(Class<T> featureClass) {
            // noinspection unchecked
            return (T) featureRegistry.stream()
                    .filter(f -> f.getClass().equals(featureClass))
                    .findFirst().orElse(null);
        }

        public static <T extends IAdorufuFeature> boolean isFeatureEnabled(Class<T> featureClass) {
            IAdorufuFeature feature = findFeature(featureClass);
            return feature instanceof IAdorufuTogglableFeature && ((IAdorufuTogglableFeature) feature).isEnabled();
        }
    }

    public static class Renderable implements SimpleListener {

        public static List<RenderableObject> renderableRegistry = new ArrayList<>();

        public static void register(RenderableObject robj) {
            renderableRegistry.add(robj);
            AdorufuMod.SETTING_CLASSES.add(robj);
        }
    }
    public static ImmutableSet<ClassPath.ClassInfo> findClasses(String pkg) throws IOException {
        return ClassPath.from(Manager.class.getClassLoader()).getTopLevelClassesRecursive(pkg);
    }
}

