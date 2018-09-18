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
     * Finds specific instance-methods based on the parameters you specify and their types. In generic cases where a class may have
     * two functions that take an int as a parameter, both will be returned in the array.
     *
     * This is to get around Forge's obfuscation when a plugin developer is trying to call Minecraft methods in their plugin, as simply
     * importing an unobfuscated mc jar simply throws ClassNotFoundException's when it's in obfuscated enviroment
     *
     * blah blah blah im also really fucking retarded so that's that
     *
     * @param args the arguments to the function you're trying to invoke
     * @return a list of possible methods
     */
    @Nullable
    public List<Method> findFunc(Object... args) {
        List<Method> preFuncs = new ArrayList<>();
        Arrays.stream(targetClass.getDeclaredMethods()).filter(f -> f.isAccessible() && !Modifier.isStatic(f.getModifiers())).forEach(preFuncs::add);
        List<Method> parametreSizeFuncs = new ArrayList<>();
        preFuncs.stream()
                .filter(f -> f.getParameterCount() == args.length)
                .forEach(parametreSizeFuncs::add);
        if (parametreSizeFuncs.size() == 0) {
            return null;
        }
        if (parametreSizeFuncs.size() == 1) {
            List<Method> l = new ArrayList<Method>();
            l.add(parametreSizeFuncs.get(0));
            return l;
        }
        /* ok so like there's multiple functions in this arraylist so now we have to narrow them down
         * EVEN MORE smh ;-;
         **/
        List<Method> theMethods = new ArrayList<>();
        parametreSizeFuncs.forEach(func -> {
            for (int i = 0; i < args.length; i++) {
                AdorufuMod.logWarn(true, func.getParameterTypes()[i].getSimpleName()
                        + " vs " + args[i].getClass().getSimpleName());
                if (!func.getParameterTypes()[i].isInstance(args[i])) {
                    return;
                }
            }
            theMethods.add(func);
        });
        return theMethods;
    }

    @Nullable
    public Object invoke(Method func, Object... args) {
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
}
