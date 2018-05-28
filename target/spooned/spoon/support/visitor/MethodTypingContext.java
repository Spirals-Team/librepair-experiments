package spoon.support.visitor;


public class MethodTypingContext extends spoon.support.visitor.AbstractTypingContext {
    private spoon.reflect.declaration.CtFormalTypeDeclarer scopeMethod;

    private java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments;

    private spoon.support.visitor.ClassTypingContext classTypingContext;

    public MethodTypingContext() {
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtFormalTypeDeclarer getAdaptationScope() {
        return scopeMethod;
    }

    public spoon.support.visitor.MethodTypingContext setMethod(spoon.reflect.declaration.CtMethod<?> method) {
        actualTypeArguments = spoon.support.visitor.ClassTypingContext.getTypeReferences(method.getFormalCtTypeParameters());
        if ((classTypingContext) != null) {
            spoon.reflect.declaration.CtType<?> declType = method.getDeclaringType();
            if (declType == null) {
                throw new spoon.SpoonException("Cannot use method without declaring type as scope of method typing context");
            }
            if ((classTypingContext.getAdaptationScope()) != declType) {
                if ((classTypingContext.isSubtypeOf(declType.getReference())) == false) {
                    throw new spoon.SpoonException("Cannot create MethodTypingContext for method declared in different ClassTypingContext");
                }
                spoon.reflect.factory.Factory factory = method.getFactory();
                spoon.reflect.declaration.CtMethod<?> adaptedMethod = factory.Core().createMethod();
                adaptedMethod.setParent(classTypingContext.getAdaptationScope());
                adaptedMethod.setModifiers(method.getModifiers());
                adaptedMethod.setSimpleName(method.getSimpleName());
                for (spoon.reflect.declaration.CtTypeParameter typeParam : method.getFormalCtTypeParameters()) {
                    spoon.reflect.declaration.CtTypeParameter newTypeParam = typeParam.clone();
                    newTypeParam.setSuperclass(adaptTypeForNewMethod(typeParam.getSuperclass()));
                    adaptedMethod.addFormalCtTypeParameter(newTypeParam);
                }
                scopeMethod = adaptedMethod;
                for (spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> thrownType : method.getThrownTypes()) {
                    adaptedMethod.addThrownType(((spoon.reflect.reference.CtTypeReference<java.lang.Throwable>) (adaptType(thrownType.clone()))));
                }
                adaptedMethod.setType(((spoon.reflect.reference.CtTypeReference) (adaptType(method.getType()))));
                java.util.List<spoon.reflect.declaration.CtParameter<?>> adaptedParams = new java.util.ArrayList<>(method.getParameters().size());
                for (spoon.reflect.declaration.CtParameter<?> parameter : method.getParameters()) {
                    adaptedParams.add(factory.Executable().createParameter(null, adaptType(parameter.getType()), parameter.getSimpleName()));
                }
                adaptedMethod.setParameters(adaptedParams);
                method = adaptedMethod;
            }
        }
        scopeMethod = method;
        return this;
    }

    public spoon.support.visitor.MethodTypingContext setConstructor(spoon.reflect.declaration.CtConstructor<?> constructor) {
        actualTypeArguments = spoon.support.visitor.ClassTypingContext.getTypeReferences(constructor.getFormalCtTypeParameters());
        checkSameTypingContext(classTypingContext, constructor);
        scopeMethod = constructor;
        return this;
    }

    @java.lang.Override
    public spoon.support.visitor.ClassTypingContext getEnclosingGenericTypeAdapter() {
        if (((classTypingContext) == null) && ((scopeMethod) != null)) {
            classTypingContext = new spoon.support.visitor.ClassTypingContext(getScopeMethodDeclaringType());
        }
        return classTypingContext;
    }

    public spoon.support.visitor.MethodTypingContext setClassTypingContext(spoon.support.visitor.ClassTypingContext classTypingContext) {
        checkSameTypingContext(classTypingContext, scopeMethod);
        this.classTypingContext = classTypingContext;
        return this;
    }

    public spoon.support.visitor.MethodTypingContext setInvocation(spoon.reflect.code.CtInvocation<?> invocation) {
        if ((classTypingContext) == null) {
            spoon.reflect.code.CtExpression<?> target = invocation.getTarget();
            if (target != null) {
                spoon.reflect.reference.CtTypeReference<?> targetTypeRef = target.getType();
                if (targetTypeRef != null) {
                    classTypingContext = new spoon.support.visitor.ClassTypingContext(targetTypeRef);
                }
            }
        }
        setExecutableReference(invocation.getExecutable());
        return this;
    }

    public spoon.support.visitor.MethodTypingContext setExecutableReference(spoon.reflect.reference.CtExecutableReference<?> execRef) {
        if ((classTypingContext) == null) {
            spoon.reflect.reference.CtTypeReference<?> declaringTypeRef = execRef.getDeclaringType();
            if (declaringTypeRef != null) {
                classTypingContext = new spoon.support.visitor.ClassTypingContext(declaringTypeRef);
            }
        }
        spoon.reflect.declaration.CtExecutable<?> exec = execRef.getExecutableDeclaration();
        if (exec == null) {
            throw new spoon.SpoonException("Cannot create MethodTypingContext from CtExecutable of CtExecutableReference is null");
        }
        if (exec instanceof spoon.reflect.declaration.CtMethod<?>) {
            setMethod(((spoon.reflect.declaration.CtMethod<?>) (exec)));
        }else
            if (exec instanceof spoon.reflect.declaration.CtConstructor<?>) {
                setConstructor(((spoon.reflect.declaration.CtConstructor<?>) (exec)));
            }else {
                throw new spoon.SpoonException(("Cannot create MethodTypingContext from " + (exec.getClass().getName())));
            }

        this.actualTypeArguments = execRef.getActualTypeArguments();
        return this;
    }

    @java.lang.Override
    protected spoon.reflect.reference.CtTypeReference<?> adaptTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParam) {
        spoon.reflect.declaration.CtFormalTypeDeclarer typeParamDeclarer = typeParam.getTypeParameterDeclarer();
        if (typeParamDeclarer instanceof spoon.reflect.declaration.CtType<?>) {
            return getEnclosingGenericTypeAdapter().adaptType(typeParam);
        }
        if (typeParamDeclarer instanceof spoon.reflect.declaration.CtMethod<?>) {
            if (((scopeMethod) instanceof spoon.reflect.declaration.CtMethod<?>) == false) {
                return null;
            }
        }else
            if (typeParamDeclarer instanceof spoon.reflect.declaration.CtConstructor<?>) {
                if (((scopeMethod) instanceof spoon.reflect.declaration.CtConstructor<?>) == false) {
                    return null;
                }
            }else {
                throw new spoon.SpoonException("Unexpected type parameter declarer");
            }

        if ((hasSameMethodFormalTypeParameters(typeParamDeclarer)) == false) {
            return null;
        }
        int typeParamPosition = typeParamDeclarer.getFormalCtTypeParameters().indexOf(typeParam);
        return actualTypeArguments.get(typeParamPosition);
    }

    public boolean hasSameMethodFormalTypeParameters(spoon.reflect.declaration.CtFormalTypeDeclarer typeParamDeclarer) {
        java.util.List<spoon.reflect.declaration.CtTypeParameter> thisTypeParameters = scopeMethod.getFormalCtTypeParameters();
        java.util.List<spoon.reflect.declaration.CtTypeParameter> thatTypeParameters = typeParamDeclarer.getFormalCtTypeParameters();
        if ((thisTypeParameters.size()) != (thatTypeParameters.size())) {
            return false;
        }
        for (int i = 0; i < (thisTypeParameters.size()); i++) {
            if ((isSameMethodFormalTypeParameter(thisTypeParameters.get(i), thatTypeParameters.get(i))) == false) {
                return false;
            }
        }
        return true;
    }

    private boolean isSameMethodFormalTypeParameter(spoon.reflect.declaration.CtTypeParameter scopeParam, spoon.reflect.declaration.CtTypeParameter superParam) {
        spoon.reflect.reference.CtTypeReference<?> scopeBound = spoon.support.visitor.MethodTypingContext.getBound(scopeParam);
        spoon.reflect.reference.CtTypeReference<?> superBound = spoon.support.visitor.MethodTypingContext.getBound(superParam);
        int idxOfScopeBoundTypeParam = getIndexOfTypeParam(scopeParam.getTypeParameterDeclarer(), scopeBound);
        if (idxOfScopeBoundTypeParam >= 0) {
            int idxOfSuperBoundTypeParam = getIndexOfTypeParam(superParam.getTypeParameterDeclarer(), superBound);
            if (idxOfSuperBoundTypeParam >= 0) {
                return idxOfScopeBoundTypeParam == idxOfSuperBoundTypeParam;
            }
        }
        if ((scopeBound.getActualTypeArguments().size()) != (superBound.getActualTypeArguments().size())) {
            return false;
        }
        for (int i = 0; i < (scopeBound.getActualTypeArguments().size()); i++) {
            spoon.reflect.reference.CtTypeReference scopeBoundATArg = scopeBound.getActualTypeArguments().get(i);
            spoon.reflect.reference.CtTypeReference superBoundATArg = superBound.getActualTypeArguments().get(i);
            if ((scopeBoundATArg instanceof spoon.reflect.reference.CtWildcardReference) && (superBoundATArg instanceof spoon.reflect.reference.CtWildcardReference)) {
                spoon.reflect.reference.CtWildcardReference scopeBoundWildcard = ((spoon.reflect.reference.CtWildcardReference) (scopeBoundATArg));
                if (scopeBoundWildcard.getBoundingType().equals(scopeMethod.getFactory().Type().getDefaultBoundingType())) {
                    return true;
                }
            }
        }
        spoon.reflect.reference.CtTypeReference<?> superBoundAdapted = adaptType(superBound);
        if (superBoundAdapted == null) {
            return false;
        }
        return scopeBound.getQualifiedName().equals(superBoundAdapted.getQualifiedName());
    }

    private int getIndexOfTypeParam(spoon.reflect.declaration.CtFormalTypeDeclarer declarer, spoon.reflect.reference.CtTypeReference<?> typeRef) {
        if (typeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
            spoon.reflect.declaration.CtTypeParameter typeParam = ((spoon.reflect.reference.CtTypeParameterReference) (typeRef)).getDeclaration();
            if (typeParam != null) {
                if (declarer == (typeParam.getTypeParameterDeclarer())) {
                    return declarer.getFormalCtTypeParameters().indexOf(typeParam);
                }
            }
        }
        return -1;
    }

    private static spoon.reflect.reference.CtTypeReference<?> getBound(spoon.reflect.declaration.CtTypeParameter typeParam) {
        spoon.reflect.reference.CtTypeReference<?> bound = typeParam.getSuperclass();
        if (bound == null) {
            bound = typeParam.getFactory().Type().OBJECT;
        }
        return bound;
    }

    private spoon.reflect.declaration.CtType<?> getScopeMethodDeclaringType() {
        if ((scopeMethod) != null) {
            return scopeMethod.getDeclaringType();
        }
        throw new spoon.SpoonException("scopeMethod is not assigned");
    }

    private spoon.reflect.reference.CtTypeReference<?> adaptTypeForNewMethod(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        if (typeRef == null) {
            return null;
        }
        if (typeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
            spoon.reflect.reference.CtTypeParameterReference typeParamRef = ((spoon.reflect.reference.CtTypeParameterReference) (typeRef));
            spoon.reflect.declaration.CtTypeParameter typeParam = typeParamRef.getDeclaration();
            if (typeParam == null) {
                throw new spoon.SpoonException("Declaration of the CtTypeParameter should not be null.");
            }
            if ((typeParam.getTypeParameterDeclarer()) instanceof spoon.reflect.declaration.CtExecutable) {
                return typeRef.clone();
            }
        }
        return classTypingContext.adaptType(typeRef);
    }

    private void checkSameTypingContext(spoon.support.visitor.ClassTypingContext ctc, spoon.reflect.declaration.CtFormalTypeDeclarer executable) {
        if ((ctc != null) && (executable != null)) {
            spoon.reflect.declaration.CtType<?> scope = executable.getDeclaringType();
            if (scope == null) {
                throw new spoon.SpoonException("Cannot use executable without declaring type as scope of method typing context");
            }
            if (scope != (ctc.getAdaptationScope())) {
                throw new spoon.SpoonException("Declaring type of executable is not same like scope of classTypingContext provided for method typing context");
            }
        }
    }
}

