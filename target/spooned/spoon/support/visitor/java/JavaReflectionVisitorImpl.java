package spoon.support.visitor.java;


class JavaReflectionVisitorImpl implements spoon.support.visitor.java.JavaReflectionVisitor {
    @java.lang.Override
    public void visitPackage(java.lang.Package aPackage) {
    }

    @java.lang.Override
    public <T> void visitClass(java.lang.Class<T> clazz) {
        if ((clazz.getPackage()) != null) {
            clazz.getPackage();
        }
        for (java.lang.reflect.TypeVariable<java.lang.Class<T>> generic : clazz.getTypeParameters()) {
            visitTypeParameter(generic);
        }
        if (((clazz.getGenericSuperclass()) != null) && ((clazz.getGenericSuperclass()) != (java.lang.Object.class))) {
            visitTypeReference(spoon.reflect.path.CtRole.SUPER_TYPE, clazz.getGenericSuperclass());
        }
        for (java.lang.reflect.Type anInterface : clazz.getGenericInterfaces()) {
            visitTypeReference(spoon.reflect.path.CtRole.INTERFACE, anInterface);
        }
        for (java.lang.annotation.Annotation annotation : clazz.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        for (java.lang.reflect.Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isSynthetic()) {
                continue;
            }
            visitConstructor(constructor);
        }
        for (spoon.support.visitor.java.reflect.RtMethod method : getDeclaredMethods(clazz)) {
            if (method.getMethod().isSynthetic()) {
                continue;
            }
            visitMethod(method);
        }
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            visitField(field);
        }
        for (java.lang.Class<?> aClass : clazz.getDeclaredClasses()) {
            visitType(aClass);
        }
    }

    protected final <T> void visitType(java.lang.Class<T> aClass) {
        if (aClass.isAnnotation()) {
            visitAnnotationClass(((java.lang.Class<java.lang.annotation.Annotation>) (aClass)));
        }else
            if (aClass.isInterface()) {
                visitInterface(aClass);
            }else
                if (aClass.isEnum()) {
                    visitEnum(aClass);
                }else {
                    visitClass(aClass);
                }


    }

    @java.lang.Override
    public <T> void visitInterface(java.lang.Class<T> clazz) {
        assert clazz.isInterface();
        if ((clazz.getPackage()) != null) {
            clazz.getPackage();
        }
        for (java.lang.reflect.Type anInterface : clazz.getGenericInterfaces()) {
            visitTypeReference(spoon.reflect.path.CtRole.INTERFACE, anInterface);
        }
        for (java.lang.annotation.Annotation annotation : clazz.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        for (spoon.support.visitor.java.reflect.RtMethod method : getDeclaredMethods(clazz)) {
            if (method.getMethod().isSynthetic()) {
                continue;
            }
            visitMethod(method);
        }
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            visitField(field);
        }
        for (java.lang.Class<?> aClass : clazz.getDeclaredClasses()) {
            visitType(aClass);
        }
        for (java.lang.reflect.TypeVariable<java.lang.Class<T>> generic : clazz.getTypeParameters()) {
            visitTypeParameter(generic);
        }
    }

    @java.lang.Override
    public <T> void visitEnum(java.lang.Class<T> clazz) {
        assert clazz.isEnum();
        if ((clazz.getPackage()) != null) {
            clazz.getPackage();
        }
        for (java.lang.reflect.Type anInterface : clazz.getGenericInterfaces()) {
            visitTypeReference(spoon.reflect.path.CtRole.INTERFACE, anInterface);
        }
        for (java.lang.annotation.Annotation annotation : clazz.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        for (java.lang.reflect.Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (java.lang.reflect.Modifier.isPrivate(constructor.getModifiers())) {
                java.lang.Class<?>[] paramTypes = constructor.getParameterTypes();
                if ((((paramTypes.length) == 2) && ((paramTypes[0]) == (java.lang.String.class))) && ((paramTypes[1]) == (int.class))) {
                    continue;
                }
            }
            if (constructor.isSynthetic()) {
                continue;
            }
            visitConstructor(constructor);
        }
        for (spoon.support.visitor.java.reflect.RtMethod method : getDeclaredMethods(clazz)) {
            if (((("valueOf".equals(method.getName())) && ((method.getParameterTypes().length) == 1)) && (java.lang.String.class.equals(method.getParameterTypes()[0]))) || ("values".equals(method.getName()))) {
                continue;
            }
            if (method.getMethod().isSynthetic()) {
                continue;
            }
            visitMethod(method);
        }
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            if (field.isEnumConstant()) {
                visitEnumValue(field);
            }else {
                visitField(field);
            }
        }
        for (java.lang.Class<?> aClass : clazz.getDeclaredClasses()) {
            visitType(aClass);
        }
    }

    @java.lang.Override
    public <T extends java.lang.annotation.Annotation> void visitAnnotationClass(java.lang.Class<T> clazz) {
        assert clazz.isAnnotation();
        if ((clazz.getPackage()) != null) {
            clazz.getPackage();
        }
        for (java.lang.annotation.Annotation annotation : clazz.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        for (spoon.support.visitor.java.reflect.RtMethod method : getDeclaredMethods(clazz)) {
            if (method.getMethod().isSynthetic()) {
                continue;
            }
            visitMethod(method);
        }
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) {
                continue;
            }
            visitField(field);
        }
        for (java.lang.Class<?> aClass : clazz.getDeclaredClasses()) {
            visitType(aClass);
        }
    }

    @java.lang.Override
    public void visitAnnotation(java.lang.annotation.Annotation annotation) {
        if ((annotation.annotationType()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.ANNOTATION_TYPE, annotation.annotationType());
            java.util.List<spoon.support.visitor.java.reflect.RtMethod> methods = getDeclaredMethods(annotation.annotationType());
            for (spoon.support.visitor.java.reflect.RtMethod method : methods) {
                visitMethod(method, annotation);
            }
        }
    }

    @java.lang.Override
    public <T> void visitConstructor(java.lang.reflect.Constructor<T> constructor) {
        for (java.lang.annotation.Annotation annotation : constructor.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        int nrEnclosingClasses = getNumberOfEnclosingClasses(constructor.getDeclaringClass());
        for (spoon.support.visitor.java.reflect.RtParameter parameter : spoon.support.visitor.java.reflect.RtParameter.parametersOf(constructor)) {
            if (nrEnclosingClasses > 0) {
                nrEnclosingClasses--;
                continue;
            }
            visitParameter(parameter);
        }
        for (java.lang.reflect.TypeVariable<java.lang.reflect.Constructor<T>> aTypeParameter : constructor.getTypeParameters()) {
            visitTypeParameter(aTypeParameter);
        }
        for (java.lang.Class<?> exceptionType : constructor.getExceptionTypes()) {
            visitTypeReference(spoon.reflect.path.CtRole.THROWN, exceptionType);
        }
    }

    private int getNumberOfEnclosingClasses(java.lang.Class<?> clazz) {
        int depth = 0;
        while (((java.lang.reflect.Modifier.isStatic(clazz.getModifiers())) == false) && ((clazz = clazz.getEnclosingClass()) != null)) {
            depth++;
        } 
        return depth;
    }

    @java.lang.Override
    public final void visitMethod(spoon.support.visitor.java.reflect.RtMethod method) {
        this.visitMethod(method, null);
    }

    protected void visitMethod(spoon.support.visitor.java.reflect.RtMethod method, java.lang.annotation.Annotation parent) {
        for (java.lang.annotation.Annotation annotation : method.getDeclaredAnnotations()) {
            if ((parent == null) || (!(annotation.annotationType().equals(parent.annotationType())))) {
                visitAnnotation(annotation);
            }
        }
        for (java.lang.reflect.TypeVariable<java.lang.reflect.Method> aTypeParameter : method.getTypeParameters()) {
            visitTypeParameter(aTypeParameter);
        }
        for (spoon.support.visitor.java.reflect.RtParameter parameter : spoon.support.visitor.java.reflect.RtParameter.parametersOf(method)) {
            visitParameter(parameter);
        }
        if ((method.getReturnType()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.TYPE, method.getGenericReturnType());
        }
        for (java.lang.Class<?> exceptionType : method.getExceptionTypes()) {
            visitTypeReference(spoon.reflect.path.CtRole.THROWN, exceptionType);
        }
    }

    @java.lang.Override
    public void visitField(java.lang.reflect.Field field) {
        for (java.lang.annotation.Annotation annotation : field.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        if ((field.getGenericType()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.TYPE, field.getGenericType());
        }
    }

    @java.lang.Override
    public void visitEnumValue(java.lang.reflect.Field field) {
        for (java.lang.annotation.Annotation annotation : field.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        if ((field.getType()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.TYPE, field.getType());
        }
    }

    @java.lang.Override
    public void visitParameter(spoon.support.visitor.java.reflect.RtParameter parameter) {
        for (java.lang.annotation.Annotation annotation : parameter.getDeclaredAnnotations()) {
            visitAnnotation(annotation);
        }
        if ((parameter.getGenericType()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.TYPE, parameter.getGenericType());
        }
    }

    @java.lang.Override
    public <T extends java.lang.reflect.GenericDeclaration> void visitTypeParameter(java.lang.reflect.TypeVariable<T> parameter) {
        for (java.lang.reflect.Type type : parameter.getBounds()) {
            if (type == (java.lang.Object.class)) {
                continue;
            }
            visitTypeReference(spoon.reflect.path.CtRole.SUPER_TYPE, type);
        }
    }

    @java.lang.Override
    public <T extends java.lang.reflect.GenericDeclaration> void visitTypeParameterReference(spoon.reflect.path.CtRole role, java.lang.reflect.TypeVariable<T> parameter) {
        for (java.lang.reflect.Type type : parameter.getBounds()) {
            if (type == (java.lang.Object.class)) {
                continue;
            }
            visitTypeReference(spoon.reflect.path.CtRole.SUPER_TYPE, type);
        }
    }

    @java.lang.Override
    public final void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.Type type) {
        spoon.reflect.reference.CtTypeReference<?> ctTypeReference;
        if (type instanceof java.lang.reflect.TypeVariable) {
            this.visitTypeParameterReference(role, ((java.lang.reflect.TypeVariable<?>) (type)));
            return;
        }
        if (type instanceof java.lang.reflect.ParameterizedType) {
            this.visitTypeReference(role, ((java.lang.reflect.ParameterizedType) (type)));
            return;
        }
        if (type instanceof java.lang.reflect.WildcardType) {
            this.visitTypeReference(role, ((java.lang.reflect.WildcardType) (type)));
            return;
        }
        if (type instanceof java.lang.reflect.GenericArrayType) {
            visitArrayReference(role, ((java.lang.reflect.GenericArrayType) (type)).getGenericComponentType());
            return;
        }
        if (type instanceof java.lang.Class) {
            java.lang.Class<?> clazz = ((java.lang.Class<?>) (type));
            if (clazz.isArray()) {
                visitArrayReference(role, clazz.getComponentType());
                return;
            }
            this.visitTypeReference(role, clazz);
            return;
        }
        throw new spoon.SpoonException(("Unexpected java reflection type: " + (type.getClass().getName())));
    }

    @java.lang.Override
    public void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.ParameterizedType type) {
        java.lang.reflect.Type rawType = type.getRawType();
        if (!(rawType instanceof java.lang.Class)) {
            throw new java.lang.UnsupportedOperationException("Rawtype of the parameterized type should be a class.");
        }
        java.lang.Class<?> classRaw = ((java.lang.Class<?>) (rawType));
        if ((classRaw.getPackage()) != null) {
            visitPackage(classRaw.getPackage());
        }
        if ((classRaw.getEnclosingClass()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.DECLARING_TYPE, classRaw.getEnclosingClass());
        }
        for (java.lang.reflect.Type generic : type.getActualTypeArguments()) {
            visitTypeReference(spoon.reflect.path.CtRole.TYPE_ARGUMENT, generic);
        }
    }

    @java.lang.Override
    public void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.WildcardType type) {
        if ((((type.getUpperBounds()) != null) && ((type.getUpperBounds().length) > 0)) && (!(type.getUpperBounds()[0].equals(java.lang.Object.class)))) {
            for (java.lang.reflect.Type upper : type.getUpperBounds()) {
                visitTypeReference(spoon.reflect.path.CtRole.BOUNDING_TYPE, upper);
            }
        }
        for (java.lang.reflect.Type lower : type.getLowerBounds()) {
            visitTypeReference(spoon.reflect.path.CtRole.BOUNDING_TYPE, lower);
        }
    }

    @java.lang.Override
    public <T> void visitArrayReference(spoon.reflect.path.CtRole role, java.lang.reflect.Type typeArray) {
        visitTypeReference(role, typeArray);
    }

    @java.lang.Override
    public <T> void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.Class<T> clazz) {
        if (((clazz.getPackage()) != null) && ((clazz.getEnclosingClass()) == null)) {
            visitPackage(clazz.getPackage());
        }
        if ((clazz.getEnclosingClass()) != null) {
            visitTypeReference(spoon.reflect.path.CtRole.DECLARING_TYPE, clazz.getEnclosingClass());
        }
    }

    private <T> java.util.List<spoon.support.visitor.java.reflect.RtMethod> getDeclaredMethods(java.lang.Class<T> clazz) {
        java.lang.reflect.Method[] javaMethods = clazz.getDeclaredMethods();
        java.util.List<spoon.support.visitor.java.reflect.RtMethod> rtMethods = new java.util.ArrayList<>();
        for (java.lang.reflect.Method method : javaMethods) {
            if (method.isSynthetic()) {
                continue;
            }
            rtMethods.add(spoon.support.visitor.java.reflect.RtMethod.create(method));
        }
        return rtMethods;
    }
}

