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

package com.sasha.adorufu.misc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sasha at 6:49 AM on 9/6/2018
 */
public class DualArrayList<K, V> implements Serializable {

    private ArrayList<K> keys = new ArrayList<>();
    private ArrayList<V> values = new ArrayList<>();

    public DualArrayList() {

    }

    public void add(K o, V o2) {
        keys.add(o);
        values.add(o2);
    }
}
