package spoon.support.visitor;


public class ClassTypingContext extends spoon.support.visitor.AbstractTypingContext {
    private final spoon.reflect.declaration.CtType<?> scopeType;

    private spoon.support.visitor.ClassTypingContext enclosingClassTypingContext;

    private java.util.Map<java.lang.String, java.util.List<spoon.reflect.reference.CtTypeReference<?>>> typeToArguments = new java.util.HashMap<>();

    private spoon.reflect.declaration.CtTypeInformation lastResolvedSuperclass;

    private java.util.Set<java.lang.String> visitedSet;

    public ClassTypingContext(spoon.reflect.reference.CtTypeReference<?> typeReference) {
        scopeType = typeReference.getTypeDeclaration();
        lastResolvedSuperclass = typeReference;
        spoon.reflect.reference.CtTypeReference<?> enclosing = getEnclosingType(typeReference);
        if (enclosing != null) {
            enclosingClassTypingContext = createEnclosingHierarchy(enclosing);
        }
        typeToArguments.put(typeReference.getQualifiedName(), typeReference.getActualTypeArguments());
    }

    public ClassTypingContext(spoon.reflect.declaration.CtType<?> type) {
        scopeType = type;
        lastResolvedSuperclass = type;
        spoon.reflect.declaration.CtType<?> enclosing = getEnclosingType(type);
        if (enclosing != null) {
            enclosingClassTypingContext = createEnclosingHierarchy(enclosing);
        }
        typeToArguments.put(type.getQualifiedName(), spoon.support.visitor.ClassTypingContext.getTypeReferences(type.getFormalCtTypeParameters()));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<?> getAdaptationScope() {
        return scopeType;
    }

    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> superTypeRef) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> adaptedArgs = resolveActualTypeArgumentsOf(superTypeRef);
        if (adaptedArgs == null) {
            return false;
        }
        if ((isSubTypeByActualTypeArguments(superTypeRef, adaptedArgs)) == false) {
            return false;
        }
        spoon.reflect.reference.CtTypeReference<?> enclosingTypeRef = getEnclosingType(superTypeRef);
        if (enclosingTypeRef != null) {
            if ((enclosingClassTypingContext) == null) {
                return false;
            }
            return enclosingClassTypingContext.isSubtypeOf(enclosingTypeRef);
        }
        return true;
    }

    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> resolveActualTypeArgumentsOf(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        final java.lang.String typeQualifiedName = typeRef.getQualifiedName();
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> args = typeToArguments.get(typeQualifiedName);
        if (args != null) {
            return args;
        }
        spoon.reflect.reference.CtTypeReference<?> enclosingTypeRef = getEnclosingType(typeRef);
        if (enclosingTypeRef != null) {
            if ((enclosingClassTypingContext) == null) {
                return null;
            }
            if ((enclosingClassTypingContext.resolveActualTypeArgumentsOf(enclosingTypeRef)) == null) {
                return null;
            }
        }
        if ((lastResolvedSuperclass) == null) {
            return null;
        }
        final spoon.support.visitor.ClassTypingContext.HierarchyListener listener = new spoon.support.visitor.ClassTypingContext.HierarchyListener(getVisitedSet());
        getVisitedSet().remove(lastResolvedSuperclass.getQualifiedName());
        ((spoon.reflect.declaration.CtElement) (lastResolvedSuperclass)).map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(false).returnTypeReferences(true).setListener(listener)).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtTypeReference<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtTypeReference<?> typeRef) {
                java.lang.String superTypeQualifiedName = typeRef.getQualifiedName();
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = typeRef.getActualTypeArguments();
                if (actualTypeArguments.isEmpty()) {
                    java.util.List<spoon.reflect.declaration.CtTypeParameter> typeParams;
                    try {
                        spoon.reflect.declaration.CtType<?> type = typeRef.getTypeDeclaration();
                        typeParams = type.getFormalCtTypeParameters();
                    } catch (final spoon.support.SpoonClassNotFoundException e) {
                        if (typeRef.getFactory().getEnvironment().getNoClasspath()) {
                            typeParams = java.util.Collections.emptyList();
                        }else {
                            throw e;
                        }
                    }
                    if ((typeParams.size()) > 0) {
                        actualTypeArguments = new java.util.ArrayList<>(typeParams.size());
                        for (spoon.reflect.declaration.CtTypeParameter typeParam : typeParams) {
                            actualTypeArguments.add(typeParam.getTypeErasure());
                        }
                    }
                }
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> superTypeActualTypeArgumentsResolvedFromSubType = resolveTypeParameters(actualTypeArguments);
                typeToArguments.put(superTypeQualifiedName, superTypeActualTypeArgumentsResolvedFromSubType);
                if (typeQualifiedName.equals(superTypeQualifiedName)) {
                    listener.foundArguments = superTypeActualTypeArgumentsResolvedFromSubType;
                }
            }
        });
        if ((listener.foundArguments) == null) {
            lastResolvedSuperclass = null;
        }
        return listener.foundArguments;
    }

    public boolean isOverriding(spoon.reflect.declaration.CtMethod<?> thisMethod, spoon.reflect.declaration.CtMethod<?> thatMethod) {
        if (thisMethod == thatMethod) {
            return true;
        }
        spoon.reflect.declaration.CtType<?> thatDeclType = thatMethod.getDeclaringType();
        spoon.reflect.declaration.CtType<?> thisDeclType = getAdaptationScope();
        if (thatDeclType != thisDeclType) {
            if ((isSubtypeOf(thatDeclType.getReference())) == false) {
                return false;
            }
        }
        return isSubSignature(thisMethod, thatMethod);
    }

    public boolean isSubSignature(spoon.reflect.declaration.CtMethod<?> thisMethod, spoon.reflect.declaration.CtMethod<?> thatMethod) {
        return isSameSignature(thisMethod, thatMethod, true);
    }

    public boolean isSameSignature(spoon.reflect.declaration.CtExecutable<?> thisExecutable, spoon.reflect.declaration.CtMethod<?> thatExecutable) {
        if (((thatExecutable instanceof spoon.reflect.declaration.CtMethod) || (thatExecutable instanceof spoon.reflect.declaration.CtConstructor)) == false) {
            return false;
        }
        return isSameSignature(thisExecutable, thatExecutable, false);
    }

    @java.lang.Override
    public spoon.support.visitor.ClassTypingContext getEnclosingGenericTypeAdapter() {
        return enclosingClassTypingContext;
    }

    protected spoon.support.visitor.ClassTypingContext createEnclosingHierarchy(spoon.reflect.declaration.CtType<?> enclosingType) {
        return new spoon.support.visitor.ClassTypingContext(enclosingType);
    }

    protected spoon.support.visitor.ClassTypingContext createEnclosingHierarchy(spoon.reflect.reference.CtTypeReference<?> enclosingTypeRef) {
        return new spoon.support.visitor.ClassTypingContext(enclosingTypeRef);
    }

    static java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTypeReferences(java.util.List<? extends spoon.reflect.declaration.CtType<?>> types) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> refs = new java.util.ArrayList<>(types.size());
        for (spoon.reflect.declaration.CtType<?> type : types) {
            refs.add(type.getReference());
        }
        return refs;
    }

    private spoon.reflect.declaration.CtType<?> getEnclosingType(spoon.reflect.declaration.CtType<?> type) {
        if (type.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC)) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> declType = type.getDeclaringType();
        if (declType == null) {
            return null;
        }
        if (declType.isInterface()) {
            return null;
        }
        return declType;
    }

    private spoon.reflect.reference.CtTypeReference<?> getEnclosingType(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        spoon.reflect.declaration.CtType<?> type = typeRef.getTypeDeclaration();
        if (type.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC)) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> declType = type.getDeclaringType();
        if (declType == null) {
            return null;
        }
        if (declType.isInterface()) {
            return null;
        }
        return typeRef.getDeclaringType();
    }

    @java.lang.Override
    protected spoon.reflect.reference.CtTypeReference<?> adaptTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParam) {
        if (typeParam == null) {
            throw new spoon.SpoonException("You cannot adapt a null type parameter.");
        }
        spoon.reflect.declaration.CtFormalTypeDeclarer declarer = typeParam.getTypeParameterDeclarer();
        if ((declarer instanceof spoon.reflect.declaration.CtType<?>) == false) {
            return null;
        }
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = resolveActualTypeArgumentsOf(((spoon.reflect.declaration.CtType<?>) (declarer)).getReference());
        if (actualTypeArguments == null) {
            if ((enclosingClassTypingContext) != null) {
                return enclosingClassTypingContext.adaptType(typeParam);
            }
            return null;
        }
        return spoon.support.visitor.ClassTypingContext.getValue(actualTypeArguments, typeParam, declarer);
    }

    private java.util.Set<java.lang.String> getVisitedSet() {
        if ((visitedSet) == null) {
            visitedSet = new java.util.HashSet<>();
        }
        return visitedSet;
    }

    private class HierarchyListener extends spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.DistinctTypeListener {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> foundArguments;

        HierarchyListener(java.util.Set<java.lang.String> visitedSet) {
            super(visitedSet);
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            if (isClass) {
                if ((foundArguments) != null) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                lastResolvedSuperclass = typeRef;
            }
            spoon.reflect.visitor.chain.ScanningMode mode = super.enter(typeRef);
            if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
                return mode;
            }
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }
    }

    private java.util.List<spoon.reflect.reference.CtTypeReference<?>> resolveTypeParameters(java.util.List<spoon.reflect.reference.CtTypeReference<?>> typeRefs) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> result = new java.util.ArrayList<>(typeRefs.size());
        for (spoon.reflect.reference.CtTypeReference<?> typeRef : typeRefs) {
            if (typeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
                spoon.reflect.reference.CtTypeParameterReference typeParamRef = ((spoon.reflect.reference.CtTypeParameterReference) (typeRef));
                spoon.reflect.declaration.CtTypeParameter typeParam = typeParamRef.getDeclaration();
                if (typeParam == null) {
                    throw new spoon.SpoonException((("The typeParam " + (typeRef.getQualifiedName())) + " declaration cannot be resolved"));
                }
                spoon.reflect.declaration.CtFormalTypeDeclarer declarer = typeParam.getTypeParameterDeclarer();
                typeRef = resolveTypeParameter(declarer, typeParamRef, typeParam, typeRef);
            }
            result.add(typeRef);
        }
        return result;
    }

    private spoon.reflect.reference.CtTypeReference<?> resolveTypeParameter(spoon.reflect.declaration.CtFormalTypeDeclarer declarer, spoon.reflect.reference.CtTypeParameterReference typeParamRef, spoon.reflect.declaration.CtTypeParameter typeParam, spoon.reflect.reference.CtTypeReference<?> typeRef) {
        if ((declarer instanceof spoon.reflect.declaration.CtType<?>) == false) {
            return typeRef;
        }
        spoon.reflect.declaration.CtType<?> typeDeclarer = ((spoon.reflect.declaration.CtType<?>) (declarer));
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = getActualTypeArguments(typeDeclarer.getQualifiedName());
        if (actualTypeArguments == null) {
            return typeRef;
        }
        if ((actualTypeArguments.size()) != (typeDeclarer.getFormalCtTypeParameters().size())) {
            if ((actualTypeArguments.isEmpty()) == false) {
                throw new spoon.SpoonException(((("Unexpected actual type arguments " + actualTypeArguments) + " on ") + typeDeclarer));
            }
            actualTypeArguments = spoon.support.visitor.ClassTypingContext.getTypeReferences(typeDeclarer.getFormalCtTypeParameters());
            typeToArguments.put(typeDeclarer.getQualifiedName(), actualTypeArguments);
        }
        return spoon.support.visitor.ClassTypingContext.getValue(actualTypeArguments, typeParam, declarer);
    }

    private java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments(java.lang.String qualifiedName) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = typeToArguments.get(qualifiedName);
        if (actualTypeArguments != null) {
            return actualTypeArguments;
        }
        if ((enclosingClassTypingContext) != null) {
            return enclosingClassTypingContext.getActualTypeArguments(qualifiedName);
        }
        return null;
    }

    private static spoon.reflect.reference.CtTypeReference<?> getValue(java.util.List<spoon.reflect.reference.CtTypeReference<?>> arguments, spoon.reflect.declaration.CtTypeParameter typeParam, spoon.reflect.declaration.CtFormalTypeDeclarer declarer) {
        if ((declarer.getFormalCtTypeParameters().size()) != (arguments.size())) {
            throw new spoon.SpoonException("Unexpected count of actual type arguments");
        }
        int typeParamIdx = declarer.getFormalCtTypeParameters().indexOf(typeParam);
        return arguments.get(typeParamIdx);
    }

    static <T, U extends java.util.List<T>> T substituteBy(spoon.reflect.declaration.CtTypeParameter typeParameter, spoon.reflect.declaration.CtFormalTypeDeclarer declarer, U values) {
        java.util.List<spoon.reflect.declaration.CtTypeParameter> typeParams = declarer.getFormalCtTypeParameters();
        int position = typeParams.indexOf(typeParameter);
        if (position == (-1)) {
            throw new spoon.SpoonException(((("Type parameter <" + (typeParameter.getSimpleName())) + " not found in scope ") + (declarer.getShortRepresentation())));
        }
        if ((values.size()) != (typeParams.size())) {
            throw new spoon.SpoonException("Unexpected count of parameters");
        }
        return values.get(position);
    }

    private boolean isSubTypeByActualTypeArguments(spoon.reflect.reference.CtTypeReference<?> superTypeRef, java.util.List<spoon.reflect.reference.CtTypeReference<?>> expectedSuperTypeArguments) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> superTypeArgs = superTypeRef.getActualTypeArguments();
        if (superTypeArgs.isEmpty()) {
            return true;
        }
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> subTypeArgs = expectedSuperTypeArguments;
        if (subTypeArgs.isEmpty()) {
            return true;
        }
        if ((subTypeArgs.size()) != (superTypeArgs.size())) {
            return false;
        }
        for (int i = 0; i < (subTypeArgs.size()); i++) {
            spoon.reflect.reference.CtTypeReference<?> superArg = superTypeArgs.get(i);
            spoon.reflect.reference.CtTypeReference<?> subArg = subTypeArgs.get(i);
            if ((isSubTypeArg(subArg, superArg)) == false) {
                return false;
            }
        }
        return true;
    }

    private boolean isSubTypeArg(spoon.reflect.reference.CtTypeReference<?> subArg, spoon.reflect.reference.CtTypeReference<?> superArg) {
        if (superArg instanceof spoon.reflect.reference.CtWildcardReference) {
            spoon.reflect.reference.CtWildcardReference wr = ((spoon.reflect.reference.CtWildcardReference) (superArg));
            spoon.reflect.reference.CtTypeReference<?> superBound = wr.getBoundingType();
            if (superBound.equals(wr.getFactory().Type().OBJECT)) {
                return wr.isUpper();
            }
            if (subArg instanceof spoon.reflect.reference.CtWildcardReference) {
                spoon.reflect.reference.CtWildcardReference subWr = ((spoon.reflect.reference.CtWildcardReference) (subArg));
                spoon.reflect.reference.CtTypeReference<?> subBound = subWr.getBoundingType();
                if (subBound.equals(wr.getFactory().Type().OBJECT)) {
                    return false;
                }
                if ((wr.isUpper()) != (subWr.isUpper())) {
                    return false;
                }
                if (wr.isUpper()) {
                    return subBound.isSubtypeOf(superBound);
                }
                return superBound.isSubtypeOf(subBound);
            }
            if (wr.isUpper()) {
                return subArg.isSubtypeOf(superBound);
            }else {
                return superBound.isSubtypeOf(subArg);
            }
        }
        return subArg.equals(superArg);
    }

    private boolean isSameSignature(spoon.reflect.declaration.CtExecutable<?> thisMethod, spoon.reflect.declaration.CtExecutable<?> thatMethod, boolean canTypeErasure) {
        if (thisMethod == thatMethod) {
            return true;
        }
        spoon.support.visitor.ClassTypingContext.ExecutableContext mtc = new spoon.support.visitor.ClassTypingContext.ExecutableContext();
        mtc.setClassTypingContext(this);
        if (thisMethod instanceof spoon.reflect.declaration.CtMethod) {
            if (thatMethod instanceof spoon.reflect.declaration.CtMethod) {
                mtc.setMethod(((spoon.reflect.declaration.CtMethod<?>) (thisMethod)));
            }else {
                return false;
            }
        }else
            if (thisMethod instanceof spoon.reflect.declaration.CtConstructor) {
                if (thatMethod instanceof spoon.reflect.declaration.CtConstructor) {
                    mtc.setConstructor(((spoon.reflect.declaration.CtConstructor<?>) (thisMethod)));
                }else {
                    return false;
                }
            }else {
                return false;
            }

        return mtc.isSameSignatureLikeScopeMethod(thatMethod, canTypeErasure);
    }

    private static class ExecutableContext extends spoon.support.visitor.MethodTypingContext {
        private boolean isSameSignatureLikeScopeMethod(spoon.reflect.declaration.CtExecutable<?> thatExecutable, boolean canTypeErasure) {
            spoon.reflect.declaration.CtFormalTypeDeclarer thatDeclarer = ((spoon.reflect.declaration.CtFormalTypeDeclarer) (thatExecutable));
            spoon.reflect.declaration.CtFormalTypeDeclarer thisDeclarer = getAdaptationScope();
            spoon.reflect.declaration.CtExecutable<?> thisExecutable = ((spoon.reflect.declaration.CtExecutable<?>) (thisDeclarer));
            if ((thatExecutable.getSimpleName().equals(thisExecutable.getSimpleName())) == false) {
                return false;
            }
            if ((thisExecutable.getParameters().size()) != (thatExecutable.getParameters().size())) {
                return false;
            }
            java.util.List<spoon.reflect.declaration.CtTypeParameter> thisTypeParameters = thisDeclarer.getFormalCtTypeParameters();
            java.util.List<spoon.reflect.declaration.CtTypeParameter> thatTypeParameters = thatDeclarer.getFormalCtTypeParameters();
            boolean useTypeErasure = false;
            if ((thisTypeParameters.size()) == (thatTypeParameters.size())) {
                if ((hasSameMethodFormalTypeParameters(((spoon.reflect.declaration.CtFormalTypeDeclarer) (thatExecutable)))) == false) {
                    return false;
                }
            }else {
                if (canTypeErasure == false) {
                    return false;
                }
                if ((thisTypeParameters.isEmpty()) == false) {
                    return false;
                }
                useTypeErasure = true;
            }
            java.util.List<spoon.reflect.reference.CtTypeReference<?>> thisParameterTypes = spoon.support.visitor.ClassTypingContext.ExecutableContext.getParameterTypes(thisExecutable.getParameters());
            java.util.List<spoon.reflect.reference.CtTypeReference<?>> thatParameterTypes = spoon.support.visitor.ClassTypingContext.ExecutableContext.getParameterTypes(thatExecutable.getParameters());
            for (int i = 0; i < (thisParameterTypes.size()); i++) {
                spoon.reflect.reference.CtTypeReference<?> thisType = thisParameterTypes.get(i);
                spoon.reflect.reference.CtTypeReference<?> thatType = thatParameterTypes.get(i);
                if (useTypeErasure) {
                    if (thatType instanceof spoon.reflect.reference.CtTypeParameterReference) {
                        thatType = ((spoon.reflect.reference.CtTypeParameterReference) (thatType)).getTypeErasure();
                    }
                }else {
                    thatType = adaptType(thatType);
                }
                if (thatType == null) {
                    return false;
                }
                if ((thisType.getActualTypeArguments().isEmpty()) && ((thatType.getActualTypeArguments().size()) == 1)) {
                    spoon.reflect.reference.CtTypeReference actualTA = thatType.getActualTypeArguments().get(0);
                    if (actualTA instanceof spoon.reflect.reference.CtWildcardReference) {
                        spoon.reflect.reference.CtWildcardReference wildcardReference = ((spoon.reflect.reference.CtWildcardReference) (actualTA));
                        if (wildcardReference.isDefaultBoundingType()) {
                            thatType.setActualTypeArguments(java.util.Collections.EMPTY_LIST);
                        }
                    }
                }
                if ((thisType.equals(thatType)) == false) {
                    return false;
                }
            }
            return true;
        }

        private static java.util.List<spoon.reflect.reference.CtTypeReference<?>> getParameterTypes(java.util.List<spoon.reflect.declaration.CtParameter<?>> params) {
            java.util.List<spoon.reflect.reference.CtTypeReference<?>> types = new java.util.ArrayList<>(params.size());
            for (spoon.reflect.declaration.CtParameter<?> param : params) {
                types.add(param.getType());
            }
            return types;
        }
    }
}

