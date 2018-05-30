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
        return new spoon.support.visitor.java.reflect.RtMethod(method.getDeclaringClass(), method, method.getName(), method.getReturnType(), method.getGenericReturnType(), method.getTypeParameters(), method.getParameterTypes(), method.getGenericParameterTypes(), method.getExceptionTypes(), method.getModifiers(), method.getDeclaredAnnotations(), method.getParameterAnnotations(), method.isVarArgs(), spoon.support.visitor.java.reflect.RtMethod._java8_isDefault(method));
    }

    private static java.lang.reflect.Method _method_isDefault;

    static {
        try {
            spoon.support.visitor.java.reflect.RtMethod._method_isDefault = java.lang.reflect.Method.class.getMethod("isDefault");
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            spoon.support.visitor.java.reflect.RtMethod._method_isDefault = null;
        }
    }

    private static boolean _java8_isDefault(java.lang.reflect.Method method) {
        if ((spoon.support.visitor.java.reflect.RtMethod._method_isDefault) == null) {
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

