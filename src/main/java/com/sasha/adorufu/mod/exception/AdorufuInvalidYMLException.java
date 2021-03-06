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

package com.sasha.adorufu.mod.exception;

import com.sasha.adorufu.mod.AdorufuMod;
import net.minecraft.crash.CrashReport;

/**
 * Created by Sasha at 11:06 AM on 9/2/2018
 */
public class AdorufuInvalidYMLException extends AdorufuException {
    public AdorufuInvalidYMLException(String error) {
        super(error);
        AdorufuMod.minecraft.displayCrashReport(new CrashReport(error, this));
    }
}
