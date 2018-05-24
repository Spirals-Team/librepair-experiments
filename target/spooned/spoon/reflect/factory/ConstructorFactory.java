package spoon.reflect.factory;


public class ConstructorFactory extends spoon.reflect.factory.ExecutableFactory {
    public ConstructorFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtConstructor<T> create(spoon.reflect.declaration.CtClass<T> target, spoon.reflect.declaration.CtConstructor<?> source) {
        spoon.reflect.declaration.CtConstructor<T> newConstructor = ((spoon.reflect.declaration.CtConstructor<T>) (source.clone()));
        target.addConstructor(newConstructor);
        return newConstructor;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtConstructor<T> create(spoon.reflect.declaration.CtClass<T> target, spoon.reflect.declaration.CtMethod<?> source) {
        spoon.reflect.declaration.CtMethod<T> method = ((spoon.reflect.declaration.CtMethod<T>) (source.clone()));
        spoon.reflect.declaration.CtConstructor<T> newConstructor = factory.Core().createConstructor();
        newConstructor.setAnnotations(method.getAnnotations());
        newConstructor.setBody(method.getBody());
        newConstructor.setDocComment(method.getDocComment());
        newConstructor.setFormalCtTypeParameters(method.getFormalCtTypeParameters());
        newConstructor.setModifiers(method.getModifiers());
        newConstructor.setParameters(method.getParameters());
        target.addConstructor(newConstructor);
        return newConstructor;
    }

    public <T> spoon.reflect.declaration.CtConstructor<T> create(spoon.reflect.declaration.CtClass<T> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes) {
        spoon.reflect.declaration.CtConstructor<T> constructor = factory.Core().createConstructor();
        constructor.setModifiers(modifiers);
        constructor.setParameters(parameters);
        constructor.setThrownTypes(thrownTypes);
        target.addConstructor(constructor);
        return constructor;
    }

    public <T> spoon.reflect.declaration.CtConstructor<T> createDefault(spoon.reflect.declaration.CtClass<T> target) {
        spoon.reflect.declaration.CtConstructor<T> constructor = factory.Core().createConstructor();
        constructor.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        target.addConstructor(constructor);
        return constructor;
    }

    public <T> spoon.reflect.declaration.CtConstructor<T> create(spoon.reflect.declaration.CtClass<T> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes, spoon.reflect.code.CtBlock<T> body) {
        spoon.reflect.declaration.CtConstructor<T> constructor = create(target, modifiers, parameters, thrownTypes);
        constructor.setBody(body);
        return constructor;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.declaration.CtConstructor<T> c) {
        return factory.Executable().createReference(c);
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(java.lang.reflect.Constructor<T> constructor) {
        spoon.reflect.reference.CtTypeReference<T> type = factory.Type().createReference(constructor.getDeclaringClass());
        return createReference(type, type.clone(), spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME, factory.Type().createReferences(java.util.Arrays.asList(constructor.getParameterTypes())));
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.reference.CtTypeReference<T> type, spoon.reflect.code.CtExpression<?>... parameters) {
        final spoon.reflect.reference.CtExecutableReference<T> executableReference = factory.Core().createExecutableReference();
        executableReference.setType(type);
        executableReference.setDeclaringType((type == null ? null : type.clone()));
        executableReference.setSimpleName(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME);
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> typeReferences = new java.util.ArrayList<>();
        for (spoon.reflect.code.CtExpression<?> parameter : parameters) {
            typeReferences.add(((parameter.getType()) == null ? null : parameter.getType().clone()));
        }
        executableReference.setParameters(typeReferences);
        return executableReference;
    }
}

