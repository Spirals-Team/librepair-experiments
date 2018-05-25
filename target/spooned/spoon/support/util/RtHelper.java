package spoon.support.util;


public abstract class RtHelper {
    private RtHelper() {
    }

    public static java.lang.reflect.Field[] getAllFields(java.lang.Class<?> c) {
        java.util.List<java.lang.reflect.Field> fields = new java.util.ArrayList<>();
        spoon.support.util.RtHelper.addAllFields(c, fields);
        java.lang.reflect.Field[] result = new java.lang.reflect.Field[fields.size()];
        return fields.toArray(result);
    }

    private static void addAllFields(java.lang.Class<?> c, java.util.List<java.lang.reflect.Field> fields) {
        if ((c != null) && (c != (java.lang.Object.class))) {
            for (java.lang.reflect.Field f : c.getDeclaredFields()) {
                fields.add(f);
            }
            spoon.support.util.RtHelper.addAllFields(c.getSuperclass(), fields);
            for (java.lang.Class<?> iface : c.getInterfaces()) {
                spoon.support.util.RtHelper.addAllFields(iface, fields);
            }
        }
    }

    public static java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getAllFields(java.lang.Class<?> c, spoon.reflect.factory.Factory factory) {
        java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> l = new java.util.ArrayList<>();
        for (java.lang.reflect.Field f : spoon.support.util.RtHelper.getAllFields(c)) {
            l.add(factory.Field().createReference(f));
        }
        return l;
    }

    public static java.lang.reflect.Method[] getAllMethods(java.lang.Class<?> c) {
        java.util.List<java.lang.reflect.Method> methods = new java.util.ArrayList<>();
        if (c.isInterface()) {
            spoon.support.util.RtHelper.getAllIMethods(c, methods);
        }else {
            while ((c != null) && (c != (java.lang.Object.class))) {
                for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
                    methods.add(m);
                }
                c = c.getSuperclass();
            } 
        }
        java.lang.reflect.Method[] result = new java.lang.reflect.Method[methods.size()];
        return methods.toArray(result);
    }

    private static void getAllIMethods(java.lang.Class<?> c, java.util.List<java.lang.reflect.Method> methods) {
        for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
            methods.add(m);
        }
        for (java.lang.Class<?> i : c.getInterfaces()) {
            spoon.support.util.RtHelper.getAllIMethods(i, methods);
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public static <T> T invoke(spoon.reflect.code.CtInvocation<T> i) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.reflect.InvocationTargetException {
        java.lang.Object target = ((i.getTarget()) == null) ? null : ((spoon.reflect.code.CtLiteral<?>) (i.getTarget())).getValue();
        java.util.List<java.lang.Object> args = new java.util.ArrayList<>();
        for (spoon.reflect.code.CtExpression<?> e : i.getArguments()) {
            args.add(((spoon.reflect.code.CtLiteral<?>) (e)).getValue());
        }
        java.lang.Class<?> c = i.getExecutable().getDeclaringType().getActualClass();
        java.util.ArrayList<java.lang.Class<?>> argTypes = new java.util.ArrayList<>();
        for (spoon.reflect.reference.CtTypeReference<?> type : i.getExecutable().getActualTypeArguments()) {
            argTypes.add(type.getActualClass());
        }
        return ((T) (c.getMethod(i.getExecutable().getSimpleName(), argTypes.toArray(new java.lang.Class[argTypes.size()])).invoke(target, args.toArray())));
    }

    public static java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers(int mod) {
        java.util.Set<spoon.reflect.declaration.ModifierKind> set = new java.util.HashSet<>();
        if (java.lang.reflect.Modifier.isAbstract(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isFinal(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.FINAL);
        }
        if (java.lang.reflect.Modifier.isNative(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.NATIVE);
        }
        if (java.lang.reflect.Modifier.isPrivate(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isProtected(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPublic(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isStatic(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.STATIC);
        }
        if (java.lang.reflect.Modifier.isStrict(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.STRICTFP);
        }
        if (java.lang.reflect.Modifier.isSynchronized(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.SYNCHRONIZED);
        }
        if (java.lang.reflect.Modifier.isTransient(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.TRANSIENT);
        }
        if (java.lang.reflect.Modifier.isVolatile(mod)) {
            set.add(spoon.reflect.declaration.ModifierKind.VOLATILE);
        }
        return set;
    }

    public static java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables(java.lang.Class<?> clazz, spoon.reflect.factory.Factory factory) {
        java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>();
        for (java.lang.reflect.Method m : clazz.getDeclaredMethods()) {
            l.add(factory.Method().createReference(m));
        }
        for (java.lang.reflect.Constructor<?> c : clazz.getDeclaredConstructors()) {
            l.add(factory.Constructor().createReference(c));
        }
        return l;
    }

    public static java.lang.reflect.Method getMethod(java.lang.Class<?> clazz, java.lang.String methodName, int numParams) {
        java.lang.reflect.Method[] methods = clazz.getMethods();
        for (java.lang.reflect.Method method : methods) {
            if (((method.isSynthetic()) == false) && (method.getName().equals(methodName))) {
                java.lang.Class<?>[] params = method.getParameterTypes();
                if ((params.length) == numParams) {
                    return method;
                }
            }
        }
        return null;
    }
}

