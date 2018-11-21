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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;

/**
 * Created by Sasha at 3:23 PM on 9/16/2018
 * The server seems to verify who's signing the book, so this won't work on vanilla or ncp servers for the time being
 */
public class BookForgerFeature extends AbstractAsunaTogglableFeature {

    public static String author = "Asuna_Client";

    public BookForgerFeature() {
        super("BookForger", AsunaCategory.MISC);
    }

    @Override
    public void onEnable() {
        AsunaMod.logWarn(false, "The server seems to verify who's signing the book, " +
                "so this won't work on even vanilla servers for the time being...");
    }
}
