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

package com.sasha.adorufu.api.adapter;

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
    private static final File MAP_PARAM = new File("mc_mappings" + File.separator + "parameters.csv");

    public static String translateUnobf(Class<?> targetClass, String unobfuscatedName, TranslateTypeEnum type) {
        List<String> possibilities = new ArrayList<>();
        LinkedHashMap<String, String> map = getUnobfVsObfMap(type);
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            if (stringStringEntry.getKey().equals(unobfuscatedName)) {
                possibilities.add(stringStringEntry.getKey());
            }
        }
        for (String possibility : possibilities) {
            if (determineByClass(targetClass, possibility, type)) return possibility;
        }
        return "";
    }

    protected static boolean determineByClass(Class<?> targetClass,
                                             String obfuscatedName,
                                             TranslateTypeEnum type)
    {
        switch(type) {
            case FIELD:
                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())) continue;
                    if (field.getName()/* obfuscated name */.equals(obfuscatedName)) {
                        return true;
                    }
                }
                return false;
            case FUNCTION:
                Method[] functions = targetClass.getMethods();
                for (Method func : functions) {
                    if (func.getName()/* obfuscated name */.equals(obfuscatedName)) {
                        return true;
                    }
                }
                return false;
            case PARAMETRE:
                // todo?
                return false;
        }
        return false;
    }

    private static LinkedHashMap<String, String> getUnobfVsObfMap(TranslateTypeEnum type) {
        try {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            switch (type) {
                case FIELD:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_FIELD)));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] thangs = line.split(",");
                        if (!thangs[1].startsWith("field_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    break;
                case FUNCTION:
                    BufferedReader reader$0 = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_FIELD)));
                    String line$0;
                    while ((line$0 = reader$0.readLine()) != null) {
                        String[] thangs = line$0.split(",");
                        if (!thangs[1].startsWith("func_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    break;
                case PARAMETRE:
                    BufferedReader reader$1 = new BufferedReader(new InputStreamReader(new FileInputStream(MAP_FIELD)));
                    String line$1;
                    while ((line$1 = reader$1.readLine()) != null) {
                        String[] thangs = line$1.split(",");
                        if (!thangs[1].startsWith("func_")) continue;
                        map.put(thangs[1], thangs[0]);
                    }
                    break;
            }
            return map;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedHashMap<>();
    }

}
