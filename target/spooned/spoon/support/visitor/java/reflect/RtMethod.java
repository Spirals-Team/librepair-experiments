/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.visitor.java.reflect;


public class RtMethod {
    private final java.lang.Class<?> clazz;

    private final java.lang.reflect.Method method;

    private final java.lang.String name;

    private final java.lang.Class<?> returnType;

    private final java.lang.reflect.Type genericReturnType;

    private final java.lang.reflect.TypeVariable<java.lang.reflect.Method>[] typeParameters;

    private final java.lang.Class<?>[] parameterTypes;

    private final java.lang.reflect.Type[] genericParameterTypes;

    private final java.lang.Class<?>[] exceptionTypes;

    private final int modifiers;

    private final java.lang.annotation.Annotation[] annotations;

    private final java.lang.annotation.Annotation[][] parameterAnnotations;

    private final boolean isVarArgs;

    private final boolean isDefault;

    public RtMethod(java.lang.Class<?> clazz, java.lang.reflect.Method method, java.lang.String name, java.lang.Class<?> returnType, java.lang.reflect.Type genericReturnType, java.lang.reflect.TypeVariable<java.lang.reflect.Method>[] typeParameters, java.lang.Class<?>[] parameterTypes, java.lang.reflect.Type[] genericParameterTypes, java.lang.Class<?>[] exceptionTypes, int modifiers, java.lang.annotation.Annotation[] annotations, java.lang.annotation.Annotation[][] parameterAnnotations, boolean isVarArgs, boolean isDefault) {
        this.clazz = clazz;
        this.method = method;
        this.name = name;
        this.returnType = returnType;
        this.genericReturnType = genericReturnType;
        this.typeParameters = typeParameters;
        this.parameterTypes = parameterTypes;
        this.genericParameterTypes = genericParameterTypes;
        this.exceptionTypes = exceptionTypes;
        this.modifiers = modifiers;
        this.annotations = annotations;
        this.parameterAnnotations = parameterAnnotations;
        this.isVarArgs = isVarArgs;
        this.isDefault = isDefault;
    }

    public java.lang.Class<?> getDeclaringClass() {
        return clazz;
    }

    public java.lang.reflect.Method getMethod() {
        return method;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.lang.Class<?> getReturnType() {
        return returnType;
    }

    public java.lang.reflect.TypeVariable<java.lang.reflect.Method>[] getTypeParameters() {
        return typeParameters;
    }

    public java.lang.Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public java.lang.Class<?>[] getExceptionTypes() {
        return exceptionTypes;
    }

    public int getModifiers() {
        return modifiers;
    }

    public java.lang.annotation.Annotation[] getDeclaredAnnotations() {
        return annotations;
    }

    public java.lang.annotation.Annotation[][] getParameterAnnotations() {
        return parameterAnnotations;
    }

    public boolean isVarArgs() {
        return isVarArgs;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public java.lang.reflect.Type getGenericReturnType() {
        return genericReturnType;
    }

    public java.lang.reflect.Type[] getGenericParameterTypes() {
        return genericParameterTypes;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        if ((o == null) || ((getClass()) != (o.getClass()))) {
            return false;
        }
        spoon.support.visitor.java.reflect.RtMethod rtMethod = ((spoon.support.visitor.java.reflect.RtMethod) (o));
        if ((name) != null ? !(name.equals(rtMethod.name)) : (rtMethod.name) != null) {
            return false;
        }
        if ((returnType) != null ? !(returnType.equals(rtMethod.returnType)) : (rtMethod.returnType) != null) {
            return false;
        }
        if (!(java.util.Arrays.equals(parameterTypes, rtMethod.parameterTypes))) {
            return false;
        }
        return java.util.Arrays.equals(exceptionTypes, rtMethod.exceptionTypes);
    }

    @java.lang.Override
    public int hashCode() {
        return (getDeclaringClass().getName().hashCode()) ^ (getName().hashCode());
    }

    public static spoon.support.visitor.java.reflect.RtMethod create(java.lang.reflect.Method method) {
        return // spoon is compatible with Java 7, so compilation fails here
        // method.isDefault());
        new spoon.support.visitor.java.reflect.RtMethod(method.getDeclaringClass(), method, method.getName(), method.getReturnType(), method.getGenericReturnType(), method.getTypeParameters(), method.getParameterTypes(), method.getGenericParameterTypes(), method.getExceptionTypes(), method.getModifiers(), method.getDeclaredAnnotations(), method.getParameterAnnotations(), method.isVarArgs(), spoon.support.visitor.java.reflect.RtMethod._java8_isDefault(method));
    }

    private static java.lang.reflect.Method _method_isDefault;

    static {
        try {
            spoon.support.visitor.java.reflect.RtMethod._method_isDefault = java.lang.reflect.Method.class.getMethod("isDefault");
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            // spoon is running with java 7 JDK
            spoon.support.visitor.java.reflect.RtMethod._method_isDefault = null;
        }
    }

    private static boolean _java8_isDefault(java.lang.reflect.Method method) {
        if ((spoon.support.visitor.java.reflect.RtMethod._method_isDefault) == null) {
            // spoon is running with java 7 JDK, all methods are not default, because java 7 does not have default methods
            return false;
        }
        try {
            return ((java.lang.Boolean) (spoon.support.visitor.java.reflect.RtMethod._method_isDefault.invoke(method)));
        } catch (java.lang.IllegalAccessException | java.lang.IllegalArgumentException e) {
            throw new spoon.SpoonException("Calling of Java8 Method#isDefault() failed", e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw new spoon.SpoonException("Calling of Java8 Method#isDefault() failed", e.getTargetException());
        }
    }

    public static <T> spoon.support.visitor.java.reflect.RtMethod[] methodsOf(java.lang.Class<T> clazz) {
        final spoon.support.visitor.java.reflect.RtMethod[] methods = new spoon.support.visitor.java.reflect.RtMethod[clazz.getDeclaredMethods().length];
        int i = 0;
        for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
            methods[(i++)] = spoon.support.visitor.java.reflect.RtMethod.create(method);
        }
        return methods;
    }

    /**
     * Returns the methods of `klass` that have the same signature (according to runtime reflection) but a different return type of at least one of the methods
     * in `comparedMethods` given as parameter.
     */
    public static <T> spoon.support.visitor.java.reflect.RtMethod[] sameMethodsWithDifferentTypeOf(java.lang.Class<T> klass, java.util.List<spoon.support.visitor.java.reflect.RtMethod> comparedMethods) {
        final java.util.List<spoon.support.visitor.java.reflect.RtMethod> methods = new java.util.ArrayList<>();
        for (java.lang.reflect.Method method : klass.getDeclaredMethods()) {
            final spoon.support.visitor.java.reflect.RtMethod rtMethod = spoon.support.visitor.java.reflect.RtMethod.create(method);
            for (spoon.support.visitor.java.reflect.RtMethod potential : comparedMethods) {
                if ((potential.isLightEquals(rtMethod)) && (!(rtMethod.returnType.equals(potential.returnType)))) {
                    methods.add(rtMethod);
                }
            }
        }
        return methods.toArray(new spoon.support.visitor.java.reflect.RtMethod[methods.size()]);
    }

    private boolean isLightEquals(spoon.support.visitor.java.reflect.RtMethod rtMethod) {
        if ((this) == rtMethod) {
            return true;
        }
        if ((rtMethod == null) || ((getClass()) != (rtMethod.getClass()))) {
            return false;
        }
        if ((name) != null ? !(name.equals(rtMethod.name)) : (rtMethod.name) != null) {
            return false;
        }
        return java.util.Arrays.equals(parameterTypes, rtMethod.parameterTypes);
    }
}

