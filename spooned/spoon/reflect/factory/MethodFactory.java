package spoon.reflect.factory;


public class MethodFactory extends spoon.reflect.factory.ExecutableFactory {
    public MethodFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <R, B extends R> spoon.reflect.declaration.CtMethod<R> create(spoon.reflect.declaration.CtClass<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes, spoon.reflect.code.CtBlock<B> body) {
        spoon.reflect.declaration.CtMethod<R> method = create(target, modifiers, returnType, name, parameters, thrownTypes);
        method.setBody(body);
        return method;
    }

    public <T> spoon.reflect.declaration.CtMethod<T> create(spoon.reflect.declaration.CtType<?> target, spoon.reflect.declaration.CtMethod<T> source, boolean redirectReferences) {
        spoon.reflect.declaration.CtMethod<T> newMethod = source.clone();
        if (redirectReferences && ((source.getDeclaringType()) != null)) {
            spoon.template.Substitution.redirectTypeReferences(newMethod, source.getDeclaringType().getReference(), target.getReference());
        }
        target.addMethod(newMethod);
        return newMethod;
    }

    public <T> spoon.reflect.declaration.CtMethod<T> create(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference<T> returnType, java.lang.String name, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes) {
        spoon.reflect.declaration.CtMethod<T> method = factory.Core().createMethod();
        if (modifiers != null) {
            method.setModifiers(modifiers);
        }
        method.setType(returnType);
        method.setSimpleName(name);
        if (parameters != null) {
            method.setParameters(parameters);
        }
        if (thrownTypes != null) {
            method.setThrownTypes(thrownTypes);
        }
        target.addMethod(method);
        return method;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.declaration.CtMethod<T> m) {
        return factory.Executable().createReference(m);
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(java.lang.reflect.Method method) {
        return createReference(factory.Type().createReference(method.getDeclaringClass()), ((spoon.reflect.reference.CtTypeReference<T>) (factory.Type().createReference(method.getReturnType()))), method.getName(), factory.Type().createReferences(java.util.Arrays.asList(method.getParameterTypes())).toArray(new spoon.reflect.reference.CtTypeReference<?>[0]));
    }

    public java.util.Collection<spoon.reflect.declaration.CtMethod<java.lang.Void>> getMainMethods() {
        java.util.Collection<spoon.reflect.declaration.CtMethod<java.lang.Void>> methods = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtType<?> t : factory.Type().getAll()) {
            if (t instanceof spoon.reflect.declaration.CtClass) {
                spoon.reflect.declaration.CtMethod<java.lang.Void> m = ((spoon.reflect.declaration.CtClass<?>) (t)).getMethod(factory.Type().createReference(void.class), "main", factory.Type().createArrayReference(factory.Type().createReference(java.lang.String.class)));
                if ((m != null) && (m.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC))) {
                    methods.add(m);
                }
            }
        }
        return methods;
    }
}

