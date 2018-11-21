/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

package com.sasha.asuna.api.adapter;

import com.sasha.asuna.mod.misc.Pair;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sasha at 4:27 PM on 9/18/2018
 */
public class MappingUtils {
    private static final File MAP_FIELD = new File("mc_mappings" + File.separator + "fields.csv");
    private static final File MAP_FUNC = new File("mc_mappings" + File.separator + "methods.csv");
    private static final File MAP_PARAM = new File("mc_mappings" + File.separator + "params.csv");

    public static Pair<String, /*superclass*/Class<?>> translateUnobf(Class<?> targetClass, String unobfuscatedName, TranslateTypeEnum type) {
        List<String> possibilities = new ArrayList<>();
        LinkedHashMap<String, String> map = getUnobfVsObfMap(type);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(unobfuscatedName)) {
                possibilities.add(entry.getValue());
            }
        }
        for (String possibility : possibilities) {
            Class<?> clazz = determineByClass(targetClass, possibility, type);
            if (determineByClass(targetClass, possibility, type) != null) return new Pair<>(possibility, clazz);
        }
        return new Pair<>("", null);
    }

    protected static Class<?> determineByClass(Class<?> targetClass,
                                               String obfuscatedName,
                                               TranslateTypeEnum type) {
        switch (type) {
            case FIELD:
                Class<?> currentSuper = targetClass;
                while (true) {
                    Field[] fields = currentSuper.getDeclaredFields();
                    for (Field field : fields) {
                        if (Modifier.isStatic(field.getModifiers())) continue;
                        if (field.getName()/* obfuscated name */.equals(obfuscatedName)) {
                            return currentSuper;
                        }
                    }
                    currentSuper = currentSuper.getSuperclass();
                    if (currentSuper == null || currentSuper == Object.class) {
                        break;
                    }
                }
                return null;
            case FUNCTION:
                Method[] functions = targetClass.getDeclaredMethods();
                Class<?> currentSuper$0 = targetClass;
                while (true) {
                    for (Method func : functions) {
                        if (Modifier.isStatic(func.getModifiers())) continue;
                        if (func.getName()/* obfuscated name */.equals(obfuscatedName)) {
                            return currentSuper$0;
                        }
                    }
                    currentSuper$0 = currentSuper$0.getSuperclass();
                    if (currentSuper$0 == null || currentSuper$0 == Object.class) {
                        break;
                    }
                }
                return null;
            case PARAMETER:
                // todo?
                return null;
        }
        return null;
    }

    private static LinkedHashMap<String, String> getUnobfVsObfMap(TranslateTypeEnum type) {
        try {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            switch (type) {
                case FIELD: {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_FIELD)));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] thangs = line.split(",");
                        if (!thangs[0].startsWith("field_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    return map;
                }
                case FUNCTION: {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_FUNC)));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] thangs = line.split(",");
                        if (!thangs[0].startsWith("func_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    return map;
                }
                case PARAMETER: {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_PARAM)));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] thangs = line.split(",");
                        if (!thangs[0].startsWith("param_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    return map;
                }
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
