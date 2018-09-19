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

import com.sasha.adorufu.AdorufuMod;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sasha at 6:53 PM on 9/17/2018
 * ok take two of sasha's retardation: live action 2018
 */
public class MinecraftAdapter {

    private final Class<?> targetClass;
    private final Object targetInstance;

    public MinecraftAdapter(Class<?> targetClass, Object targetInstance) {
        this.targetClass = targetClass;
        this.targetInstance = targetInstance;
    }

    public MinecraftAdapter(Class<?> targetClass, int constructorIndex, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.targetClass = targetClass;
        this.targetInstance = targetClass.getDeclaredConstructors()
                [constructorIndex].newInstance(args);
    }

    /**
     * Gets the value of a field in this MinecraftAdapter's set class.
     * @see StaticMinecraftAdapter for getting the value of STATIC fields.
     * @param unobfFieldName The unobfuscated name of the field you're trying to get.
     * @return a generic object of the returned thingy
     */
    @Nullable
    public Object getField(String unobfFieldName) {
        String obfname = MappingUtils.translateUnobf(this.targetClass, unobfFieldName, TranslateTypeEnum.FIELD);
        try {
            Field f = this.getClass().getDeclaredField(obfname);
            f.setAccessible(true);
            return f.get(this.targetInstance);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setField(String unobfFieldName, Object value) {
        String obfname = MappingUtils.translateUnobf(this.targetClass, unobfFieldName, TranslateTypeEnum.FIELD);
        try {
            Field f = this.getClass().getDeclaredField(obfname);
            f.setAccessible(true);
            f.set(this.targetInstance, value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }










    //=======================DEPRECATED=========================//


    /**
     * Finds specific instance-methods based on the parameters you specify and their types. In generic cases where a class may have
     * two functions that take an int as a parameter, both will be returned in the array.
     * <p>
     * This is to get around Forge's obfuscation when a plugin developer is trying to call Minecraft methods in their plugin, as simply
     * importing an unobfuscated mc jar simply throws ClassNotFoundException's when it's in obfuscated enviroment
     * <p>
     * blah blah blah im also really fucking retarded so that's that
     *
     * @param args the arguments to the function you're trying to invoke
     * @return a list of possible methods
     */
    @Deprecated
    @Nullable
    public List<Method> findFunc(boolean debug, Object... args) {
        List<Method> preFuncs = new ArrayList<>();
        Arrays.stream(targetClass.getDeclaredMethods()).filter(f -> Modifier.isPublic(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())).forEach(preFuncs::add);
        List<Method> parametreSizeFuncs = new ArrayList<>();
        preFuncs.stream()
                .filter(f -> f.getParameterCount() == args.length)
                .forEach(func -> {
                    parametreSizeFuncs.add(func);
                    if (debug) AdorufuMod.logMsg(true, "Parametre count matched: " + func.getName());
                });
        if (parametreSizeFuncs.size() == 0) {
            if (debug) AdorufuMod.logMsg(true, "No matching functions were found!");
            return null;
        }
        if (parametreSizeFuncs.size() == 1) {
            List<Method> l = new ArrayList<>();
            l.add(parametreSizeFuncs.get(0));
            if (debug) AdorufuMod.logMsg(true, "Found exactly one function: " + l.get(0).getName());
            return l;
        }
        /* ok so like there's multiple functions in this arraylist so now we have to narrow them down
         * EVEN MORE smh ;-;
         **/
        if (debug) AdorufuMod.logMsg(true, "Need to be more specific...");
        List<Method> theMethods = new ArrayList<>();
        parametreSizeFuncs.forEach(func -> {
            for (int i = 0; i < args.length; i++) {
                boolean flag = func.getParameterTypes()[i].isInstance(args[i]);
                if (debug) AdorufuMod.logWarn(true, func.getParameterTypes()[i].getSimpleName()
                        + " vs " + args[i].getClass().getSimpleName() + " = " + flag);
                if (!flag) {
                    return;
                }
            }
            if (debug) AdorufuMod.logMsg(true, "Specifically found: " + func.getName());
            theMethods.add(func);
        });
        return theMethods;
    }

    @Deprecated
    @Nullable
    public Object invoke(Method func, Object... args) {
        func.setAccessible(true);
        if (func.getGenericReturnType().getTypeName().equalsIgnoreCase("void")) {
            try {
                func.invoke(this.targetInstance, args);
                return null;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        try {
            return func.invoke(this.targetInstance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @Nullable
    public List<Object> invoke(List<Method> funcs, Object... args) {
        List<Object> returns = new ArrayList<>();
        int c = -1;
        for (Method func : funcs) {
            c++;
            AdorufuMod.logMsg(false, "Executing " + func.getName() + " @ position " + c);
            func.setAccessible(true);
            if (func.getGenericReturnType().getTypeName().equalsIgnoreCase("void")) {
                try {
                    func.invoke(this.targetInstance, args);
                    returns.add(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            try {
                returns.add(func.invoke(this.targetInstance, args));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            returns.add(null);
        }
        return returns;
    }
}
