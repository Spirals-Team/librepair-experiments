/* Copyright (C) 2013-2018 TU Dortmund
 * This file is part of AutomataLib, http://www.automatalib.net/.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.automatalib.commons.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectUtil {

    private static final Class<?>[][] W2P_MAPPING = {{Void.class, void.class},
                                                     {Boolean.class, boolean.class},
                                                     {Byte.class, byte.class},
                                                     {Character.class, char.class},
                                                     {Short.class, short.class},
                                                     {Integer.class, int.class},
                                                     {Long.class, long.class},
                                                     {Float.class, float.class},
                                                     {Double.class, double.class}};

    private static final Map<Class<?>, Class<?>> W2P_MAP;

    static {
        W2P_MAP = new HashMap<>();

        for (Class<?>[] w2p : W2P_MAPPING) {
            W2P_MAP.put(w2p[0], w2p[1]);
        }
    }

    private ReflectUtil() {
    }

    public static <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>... params)
            throws SecurityException, NoSuchMethodException {
        try {
            return clazz.getConstructor(params);
        } catch (NoSuchMethodException e) {
            Class<?>[] primParams = wrapperToPrimitive(params);
            if (primParams != params) {
                try {
                    return clazz.getConstructor(primParams);
                } catch (NoSuchMethodException e2) {
                    @SuppressWarnings("unchecked")
                    Constructor<T>[] ctors = (Constructor<T>[]) clazz.getConstructors();

                    for (Constructor<T> candidate : ctors) {
                        if (w2pEquals(candidate.getParameterTypes(), params)) {
                            return candidate;
                        }
                    }

                    throw e2;
                }
            }

            throw e;
        }
    }

    public static boolean w2pEquals(Class<?>[] a, Class<?>... b) {
        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            if (!w2pEquals(a[i], b[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean w2pEquals(Class<?> a, Class<?> b) {
        final Class<?> wrappedA = wrapperToPrimitive(a);
        final Class<?> wrappedB = wrapperToPrimitive(b);
        return wrappedA.equals(wrappedB);
    }

    public static Class<?>[] wrapperToPrimitive(Class<?>... clazzes) {
        Class<?>[] result = clazzes;
        for (int i = 0; i < result.length; i++) {
            Class<?> curr = result[i];
            Class<?> prim = wrapperToPrimitive(curr);
            if (prim != curr) {
                if (result == clazzes) {
                    result = clazzes.clone();
                }
                result[i] = prim;
            }
        }

        return result;
    }

    public static Class<?> wrapperToPrimitive(Class<?> wrapperClazz) {
        Class<?> prim = W2P_MAP.get(wrapperClazz);
        if (prim == null) {
            return wrapperClazz;
        }
        return prim;
    }

    public static Method findMatchingMethod(Class<?> clazz, String name, Object... args) {
        for (Method m : clazz.getMethods()) {
            if (!m.getName().equals(name)) {
                continue;
            }

            if (isMatch(m.getParameterTypes(), args)) {
                return m;
            }
        }

        return null;
    }

    public static boolean isMatch(Class<?>[] paramTypes, Object... args) {
        if (paramTypes.length != args.length) {
            return false;
        }

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            Object arg = args[i];
            if (paramType.isPrimitive()) {
                if (arg == null) {
                    return false;
                }
                Class<?> argType = arg.getClass();
                if (paramType != wrapperToPrimitive(argType)) {
                    return false;
                }
            } else {
                if (arg != null) {
                    Class<?> argType = arg.getClass();
                    if (!paramType.isAssignableFrom(argType)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... params)
            throws SecurityException, NoSuchMethodException {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            Class<?>[] primParams = wrapperToPrimitive(params);
            if (primParams != params) {
                try {
                    return clazz.getMethod(name, primParams);
                } catch (NoSuchMethodException e2) {
                    Method[] methods = clazz.getMethods();

                    for (Method candidate : methods) {
                        if (w2pEquals(candidate.getParameterTypes())) {
                            return candidate;
                        }
                    }

                    throw e2;
                }
            }

            throw e;
        }
    }

    public static Method findMethodRT(Class<?> clazz, String name, Class<?> returnType, Class<?>... params)
            throws SecurityException, NoSuchMethodException {
        Method m = findMethod(clazz, name, params);

        if (returnType == null) {
            return m;
        }

        Class<?> rt = m.getReturnType();

        if (w2pEquals(rt, returnType) || returnType.isAssignableFrom(rt)) {
            return m;
        }

        throw new NoSuchMethodException(
                "Method with matching parameters but incompatible return type " + rt.getName() + " (expected " +
                returnType.getName() + ") found");
    }
}
