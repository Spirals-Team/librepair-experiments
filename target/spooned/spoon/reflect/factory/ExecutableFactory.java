package spoon.reflect.factory;


public class ExecutableFactory extends spoon.reflect.factory.SubFactory {
    public ExecutableFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public spoon.reflect.declaration.CtAnonymousExecutable createAnonymous(spoon.reflect.declaration.CtClass<?> target, spoon.reflect.code.CtBlock<java.lang.Void> body) {
        spoon.reflect.declaration.CtAnonymousExecutable a = factory.Core().createAnonymousExecutable();
        target.addAnonymousExecutable(a);
        a.setBody(body);
        return a;
    }

    public <T> spoon.reflect.declaration.CtParameter<T> createParameter(spoon.reflect.declaration.CtExecutable<?> parent, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name) {
        spoon.reflect.declaration.CtParameter<T> parameter = factory.Core().createParameter();
        parameter.setType(type);
        parameter.setSimpleName(name);
        if (parent != null) {
            parent.addParameter(parameter);
        }
        return parameter;
    }

    public <T> spoon.reflect.reference.CtParameterReference<T> createParameterReference(spoon.reflect.declaration.CtParameter<T> parameter) {
        spoon.reflect.reference.CtParameterReference<T> ref = factory.Core().createParameterReference();
        ref.setSimpleName(parameter.getSimpleName());
        ref.setType(parameter.getType());
        return ref;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.declaration.CtExecutable<T> e) {
        spoon.reflect.reference.CtExecutableReference<T> er = createReferenceInternal(e);
        er.setParent(e);
        return er;
    }

    private <T> spoon.reflect.reference.CtExecutableReference<T> createReferenceInternal(spoon.reflect.declaration.CtExecutable<T> e) {
        spoon.reflect.reference.CtTypeReference<?>[] refs = new spoon.reflect.reference.CtTypeReference[e.getParameters().size()];
        int i = 0;
        for (spoon.reflect.declaration.CtParameter<?> param : e.getParameters()) {
            refs[(i++)] = ((param.getType()) != null) ? param.getType().clone() : factory.Type().OBJECT.clone();
        }
        java.lang.String executableName = e.getSimpleName();
        if (e instanceof spoon.reflect.declaration.CtMethod) {
            boolean isStatic = ((spoon.reflect.declaration.CtMethod) (e)).hasModifier(spoon.reflect.declaration.ModifierKind.STATIC);
            return createReference(((spoon.reflect.declaration.CtMethod<T>) (e)).getDeclaringType().getReference(), isStatic, ((spoon.reflect.declaration.CtMethod<T>) (e)).getType().clone(), executableName, refs);
        }else
            if (e instanceof spoon.reflect.code.CtLambda) {
                spoon.reflect.declaration.CtMethod<T> lambdaMethod = ((spoon.reflect.code.CtLambda) (e)).getOverriddenMethod();
                return createReference(e.getParent(spoon.reflect.declaration.CtType.class).getReference(), (lambdaMethod == null ? null : lambdaMethod.getType().clone()), executableName, refs);
            }else
                if (e instanceof spoon.reflect.declaration.CtAnonymousExecutable) {
                    return createReference(((spoon.reflect.declaration.CtAnonymousExecutable) (e)).getDeclaringType().getReference(), e.getType().clone(), executableName);
                }


        return createReference(((spoon.reflect.declaration.CtConstructor<T>) (e)).getDeclaringType().getReference(), ((spoon.reflect.declaration.CtConstructor<T>) (e)).getType().clone(), spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME, refs);
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.reference.CtTypeReference<?> declaringType, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String methodName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        return createReference(declaringType, false, type, methodName, parameterTypes);
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.reference.CtTypeReference<?> declaringType, boolean isStatic, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String methodName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        return createReference(declaringType, isStatic, type, methodName, java.util.Arrays.asList(parameterTypes));
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.reference.CtTypeReference<?> declaringType, boolean isStatic, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String methodName, java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameterTypes) {
        spoon.reflect.reference.CtExecutableReference<T> methodRef = factory.Core().createExecutableReference();
        methodRef.setStatic(isStatic);
        methodRef.setDeclaringType(declaringType);
        methodRef.setSimpleName(methodName);
        methodRef.setType(type);
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> l = new java.util.ArrayList<>(parameterTypes);
        methodRef.setParameters(l);
        return methodRef;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(spoon.reflect.reference.CtTypeReference<?> declaringType, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String methodName, java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameterTypes) {
        spoon.reflect.reference.CtExecutableReference<T> methodRef = factory.Core().createExecutableReference();
        methodRef.setDeclaringType(declaringType);
        methodRef.setSimpleName(methodName);
        methodRef.setType(type);
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> l = new java.util.ArrayList<>(parameterTypes);
        methodRef.setParameters(l);
        return methodRef;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createReference(java.lang.String signature) {
        spoon.reflect.reference.CtExecutableReference<T> executableRef = factory.Core().createExecutableReference();
        java.lang.String type = signature.substring(0, signature.indexOf(" "));
        java.lang.String declaringType = signature.substring(((signature.indexOf(" ")) + 1), signature.indexOf(spoon.reflect.declaration.CtExecutable.EXECUTABLE_SEPARATOR));
        java.lang.String executableName = signature.substring(((signature.indexOf(spoon.reflect.declaration.CtExecutable.EXECUTABLE_SEPARATOR)) + 1), signature.indexOf("("));
        executableRef.setSimpleName(executableName);
        executableRef.setDeclaringType(factory.Type().createReference(declaringType));
        spoon.reflect.reference.CtTypeReference<T> typeRef = factory.Type().createReference(type);
        executableRef.setType(typeRef);
        java.lang.String parameters = signature.substring(((signature.indexOf("(")) + 1), signature.indexOf(")"));
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> params = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        java.util.StringTokenizer t = new java.util.StringTokenizer(parameters, ",");
        while (t.hasMoreTokens()) {
            java.lang.String paramType = t.nextToken();
            params.add(factory.Type().createReference(paramType));
        } 
        executableRef.setParameters(params);
        return executableRef;
    }
}

