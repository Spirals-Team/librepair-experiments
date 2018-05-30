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
package spoon.support.visitor;


/**
 * Helper class created from type X or reference to X.
 * It provides access to actual type arguments
 * of any super type of type X adapted to type X.<br>
 * Example:<br>
 * <pre>
 * //reference to `ArrayList` with actual type argument `Integer`
 * CtTypeReference arrayListRef = ... //ArrayList&lt;Integer&gt;
 * //type java.util.List with type parameter `E`
 * CtType list = ... //List&lt;E&gt;
 * //adapting of type parameter `E` to scope of arrayListRef
 * CtTypeReference typeParamE_adaptedTo_arrayListRef = new ClassTypingContext(arrayListRef).adaptType(list.getFormalCtTypeParameters().get(0))
 * //the value of `E` in scope of arrayListRef is `Integer`
 * assertEquals(Integer.class.getName(), typeParamE_adaptedTo_arrayListRef.getQualifiedName());
 * </pre>
 */
public class ClassTypingContext extends spoon.support.visitor.AbstractTypingContext {
    private final spoon.reflect.declaration.CtType<?> scopeType;

    /* super type hierarchy of the enclosing class */
    private spoon.support.visitor.ClassTypingContext enclosingClassTypingContext;

    /* maps qualified name of the type to the actual type arguments of this type in `scope` */
    private java.util.Map<java.lang.String, java.util.List<spoon.reflect.reference.CtTypeReference<?>>> typeToArguments = new java.util.HashMap<>();

    /**
     * remember which super class was last visited.
     * The next super class scanning will start here
     */
    private spoon.reflect.declaration.CtTypeInformation lastResolvedSuperclass;

    /**
     * the set of qualified names of all visited classes and interfaces, which assures that interfaces are visited only once
     */
    private java.util.Set<java.lang.String> visitedSet;

    /**
     *
     *
     * @param typeReference
     * 		{@link CtTypeReference} whose actual type arguments are used for resolving of input type parameters
     */
    public ClassTypingContext(spoon.reflect.reference.CtTypeReference<?> typeReference) {
        scopeType = typeReference.getTypeDeclaration();
        lastResolvedSuperclass = typeReference;
        spoon.reflect.reference.CtTypeReference<?> enclosing = getEnclosingType(typeReference);
        if (enclosing != null) {
            enclosingClassTypingContext = createEnclosingHierarchy(enclosing);
        }
        typeToArguments.put(typeReference.getQualifiedName(), typeReference.getActualTypeArguments());
    }

    /**
     *
     *
     * @param type
     * 		{@link CtType} whose formal type parameters are transformed to {@link CtTypeReference}s,
     * 		which plays role of actual type arguments, used for resolving of input type parameters
     */
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

    /**
     * detects if `superTypeRef` is a super type of the type or type reference,
     * which was send to constructor of this instance.
     * It takes into account the actual type arguments of this type and `superTypeRef`
     *
     * So for example:<br>
     * <pre>
     * CtTypeReference listInteger = ...//List&lt;Integer&gt;
     * CtTypeReference listString = ...//List&lt;Integer&gt;
     * assertFalse(new ClassTypingContext(listInteger).isSubtypeOf(listString))
     * CtTypeReference listExtendsNumber = ...//List&lt;? extends Number&gt;
     * assertTrue(new ClassTypingContext(listInteger).isSubtypeOf(listExtendsNumber))
     * </pre>
     *
     * @param superTypeRef
     * 		the reference
     * @return true if this type (including actual type arguments) is a sub type of superTypeRef
     */
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> superTypeRef) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> adaptedArgs = resolveActualTypeArgumentsOf(superTypeRef);
        if (adaptedArgs == null) {
            // the superTypeRef was not found in super type hierarchy
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

    /**
     * resolve actual type argument values of the provided type reference
     *
     * @param typeRef
     * 		the reference to the type
     * 		whose actual type argument values has to be resolved in scope of `scope` type
     * @return actual type arguments of `typeRef` in scope of `scope` element or null if typeRef is not a super type of `scope`
     */
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> resolveActualTypeArgumentsOf(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        final java.lang.String typeQualifiedName = typeRef.getQualifiedName();
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> args = typeToArguments.get(typeQualifiedName);
        if (args != null) {
            // the actual type arguments of `type` are already resolved
            return args;
        }
        // resolve hierarchy of enclosing class first.
        spoon.reflect.reference.CtTypeReference<?> enclosingTypeRef = getEnclosingType(typeRef);
        if (enclosingTypeRef != null) {
            if ((enclosingClassTypingContext) == null) {
                return null;
            }
            // `type` is inner class. Resolve it's enclosing class arguments first
            if ((enclosingClassTypingContext.resolveActualTypeArgumentsOf(enclosingTypeRef)) == null) {
                return null;
            }
        }
        /* the `type` is either top level, static or resolved inner class.
        So it has no parent actual type arguments or they are resolved now
         */
        /* detect where to start/continue with resolving of super classes and super interfaces
        to found actual type arguments of input `type`
         */
        if ((lastResolvedSuperclass) == null) {
            /* whole super inheritance hierarchy was already resolved for this level.
            It means that `type` is not a super type of `scope` on the level `level`
             */
            return null;
        }
        final spoon.support.visitor.ClassTypingContext.HierarchyListener listener = new spoon.support.visitor.ClassTypingContext.HierarchyListener(getVisitedSet());
        /* remove last resolved class from the list of visited,
        because it would avoid visiting it's super hierarchy
         */
        getVisitedSet().remove(lastResolvedSuperclass.getQualifiedName());
        /* visit super inheritance class hierarchy of lastResolve type of level of `type` to found it's actual type arguments. */
        ((spoon.reflect.declaration.CtElement) (lastResolvedSuperclass)).map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(false).returnTypeReferences(true).setListener(listener)).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtTypeReference<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtTypeReference<?> typeRef) {
                /* typeRef is a reference from sub type to super type.
                It contains actual type arguments in scope of sub type,
                which are going to be substituted as arguments to formal type parameters of super type
                 */
                java.lang.String superTypeQualifiedName = typeRef.getQualifiedName();
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = typeRef.getActualTypeArguments();
                if (actualTypeArguments.isEmpty()) {
                    // may be they are not set - check whether type declares some generic parameters
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
                        // yes, there are generic type parameters. Reference should use actualTypeArguments computed from their bounds
                        actualTypeArguments = new java.util.ArrayList<>(typeParams.size());
                        for (spoon.reflect.declaration.CtTypeParameter typeParam : typeParams) {
                            actualTypeArguments.add(typeParam.getTypeErasure());
                        }
                    }
                }
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> superTypeActualTypeArgumentsResolvedFromSubType = resolveTypeParameters(actualTypeArguments);
                // Remember actual type arguments of `type`
                typeToArguments.put(superTypeQualifiedName, superTypeActualTypeArgumentsResolvedFromSubType);
                if (typeQualifiedName.equals(superTypeQualifiedName)) {
                    /* we have found actual type arguments of input `type`
                    We can finish. But only after all interfaces of last visited class are processed too
                     */
                    listener.foundArguments = superTypeActualTypeArgumentsResolvedFromSubType;
                }
            }
        });
        if ((listener.foundArguments) == null) {
            /* superclass was not found. We have scanned whole hierarchy */
            lastResolvedSuperclass = null;
        }
        return listener.foundArguments;
    }

    /**
     * thisMethod overrides thatMethod if
     * 1) thisMethod class is a subclass of thatMethod class
     * 2) thisMethod is a subsignature of thatMethod
     *
     * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.8.1
     *
     * @param thisMethod
     * 		- the scope method
     * @param thatMethod
     * 		- to be checked method
     * @return true if thisMethod overrides thatMethod
     */
    public boolean isOverriding(spoon.reflect.declaration.CtMethod<?> thisMethod, spoon.reflect.declaration.CtMethod<?> thatMethod) {
        if (thisMethod == thatMethod) {
            // method overrides itself in spoon model
            return true;
        }
        spoon.reflect.declaration.CtType<?> thatDeclType = thatMethod.getDeclaringType();
        spoon.reflect.declaration.CtType<?> thisDeclType = getAdaptationScope();
        if (thatDeclType != thisDeclType) {
            if ((isSubtypeOf(thatDeclType.getReference())) == false) {
                // the declaringType of that method must be superType of this scope type
                return false;
            }
        }
        // TODO check method visibility following https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.8.1
        return isSubSignature(thisMethod, thatMethod);
    }

    /**
     * isSubsignature is defined as an oriented relation between two methods as defined in
     * See https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.2
     *
     * thisMethod is subsignature of thatMethod if either
     * A) thisMethod is same signature like thatMethod
     * B) thisMethod is same signature like type erasure of thatMethod
     *
     * @param thisMethod
     * 		- the scope method to be checked with
     * @param thatMethod
     * 		- the checked method
     * @return true if thisMethod is subsignature of thatMethod
     */
    public boolean isSubSignature(spoon.reflect.declaration.CtMethod<?> thisMethod, spoon.reflect.declaration.CtMethod<?> thatMethod) {
        return isSameSignature(thisMethod, thatMethod, true);
    }

    /**
     * Two methods are considered as having the same signature if they have the same name and argument types
     * See https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.2
     *
     * @param thisExecutable
     * 		- the scope method to be checked with
     * @param thatExecutable
     * 		- the checked method
     * @return true if this method and thatMethod has same signature
     */
    public boolean isSameSignature(spoon.reflect.declaration.CtExecutable<?> thisExecutable, spoon.reflect.declaration.CtMethod<?> thatExecutable) {
        if (((thatExecutable instanceof spoon.reflect.declaration.CtMethod) || (thatExecutable instanceof spoon.reflect.declaration.CtConstructor)) == false) {
            // only method or constructor can have same signature
            return false;
        }
        return isSameSignature(thisExecutable, thatExecutable, false);
    }

    @java.lang.Override
    public spoon.support.visitor.ClassTypingContext getEnclosingGenericTypeAdapter() {
        return enclosingClassTypingContext;
    }

    /**
     * might be used to create custom chain of super type hierarchies
     */
    protected spoon.support.visitor.ClassTypingContext createEnclosingHierarchy(spoon.reflect.declaration.CtType<?> enclosingType) {
        return new spoon.support.visitor.ClassTypingContext(enclosingType);
    }

    /**
     * might be used to create custom chain of super type hierarchies
     */
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

    /**
     *
     *
     * @param type
     * 		the potential inner class, whose enclosing type should be returned
     * @return enclosing type of a `type` is an inner type or null if `type` is explicitly or implicitly static or top level type
     */
    private spoon.reflect.declaration.CtType<?> getEnclosingType(spoon.reflect.declaration.CtType<?> type) {
        if (type.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC)) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> declType = type.getDeclaringType();
        if (declType == null) {
            return null;
        }
        if (declType.isInterface()) {
            // nested types of interfaces are static
            return null;
        }
        return declType;
    }

    /**
     *
     *
     * @param typeRef
     * 		the potential inner class, whose enclosing type should be returned
     * @return enclosing type of a `type` is an inner type or null if `type` is explicitly or implicitly static or top level type
     */
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
            // nested types of interfaces are static
            return null;
        }
        return typeRef.getDeclaringType();
    }

    /**
     * adapts `typeParam` to the {@link CtTypeReference}
     * of scope of this {@link ClassTypingContext}
     * In can be {@link CtTypeParameterReference} again - depending actual type arguments of this {@link ClassTypingContext}.
     *
     * @param typeParam
     * 		to be resolved {@link CtTypeParameter}
     * @return {@link CtTypeReference} or {@link CtTypeParameterReference} adapted to scope of this {@link ClassTypingContext}
     * or null if `typeParam` cannot be adapted to target `scope`
     */
    @java.lang.Override
    protected spoon.reflect.reference.CtTypeReference<?> adaptTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParam) {
        if (typeParam == null) {
            throw new spoon.SpoonException("You cannot adapt a null type parameter.");
        }
        spoon.reflect.declaration.CtFormalTypeDeclarer declarer = typeParam.getTypeParameterDeclarer();
        if ((declarer instanceof spoon.reflect.declaration.CtType<?>) == false) {
            return null;
        }
        // get the actual type argument values for the declarer of `typeParam`
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = resolveActualTypeArgumentsOf(((spoon.reflect.declaration.CtType<?>) (declarer)).getReference());
        if (actualTypeArguments == null) {
            if ((enclosingClassTypingContext) != null) {
                // try to adapt parameter using enclosing class typing context
                return enclosingClassTypingContext.adaptType(typeParam);
            }
            return null;
        }
        return spoon.support.visitor.ClassTypingContext.getValue(actualTypeArguments, typeParam, declarer);
    }

    /**
     * Create visitedSet lazily
     */
    private java.util.Set<java.lang.String> getVisitedSet() {
        if ((visitedSet) == null) {
            visitedSet = new java.util.HashSet<>();
        }
        return visitedSet;
    }

    /**
     * the listener which assures that
     * - each interface of super inheritance hierarchy is visited only once
     * - the scanning of super inheritance hierarchy early stops when we have found
     */
    private class HierarchyListener extends spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.DistinctTypeListener {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> foundArguments;

        HierarchyListener(java.util.Set<java.lang.String> visitedSet) {
            super(visitedSet);
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            if (isClass) {
                /* test foundArguments and skip all before call of super.enter,
                which would add that not visited type into visitedSet
                 */
                if ((foundArguments) != null) {
                    // we have found result then we can finish before entering super class. All interfaces of found type should be still visited
                    // skip before super class (and it's interfaces) of found type is visited
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                /* we are visiting class (not interface)
                Remember that, so we can continue at this place if needed.
                If we enter class, then this listener assures that that class and all it's not yet visited interfaces are visited
                 */
                lastResolvedSuperclass = typeRef;
            }
            spoon.reflect.visitor.chain.ScanningMode mode = super.enter(typeRef);
            if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
                // this interface was already visited. Do not visit it again
                return mode;
            }
            // this type was not visited yet. Visit it normally
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }
    }

    /**
     * resolve typeRefs declared in scope of declarer using actual type arguments registered in typeScopeToActualTypeArguments
     *
     * @param typeRefs
     * 		to be resolved type references
     * @return resolved type references - one for each `typeRefs`
     * @throws SpoonException
     * 		if they cannot be resolved. It should not normally happen. If it happens then spoon AST model is probably not consistent.
     */
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
            /* The declarer is probably out of the scope of this ClassTypingContext.
            For example outer class or method declares type parameter,
            which is then used as argument in inner class, whose ClassTypingContext we have now
            See GenericsTest#testCannotAdaptTypeOfNonTypeScope.

            Use that outer type parameter reference directly without adaptation
             */
            return typeRef;
        }
        spoon.reflect.declaration.CtType<?> typeDeclarer = ((spoon.reflect.declaration.CtType<?>) (declarer));
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = getActualTypeArguments(typeDeclarer.getQualifiedName());
        if (actualTypeArguments == null) {
            /* The declarer is probably out of the scope of this ClassTypingContext.
            For example outer class or method declares type parameter,
            which is then used as argument in inner class, whose ClassTypingContext we have now
            See GenericsTest#testCannotAdaptTypeOfNonTypeScope.

            Use that outer type parameter reference directly without adaptation
             */
            return typeRef;
        }
        if ((actualTypeArguments.size()) != (typeDeclarer.getFormalCtTypeParameters().size())) {
            if ((actualTypeArguments.isEmpty()) == false) {
                throw new spoon.SpoonException(((("Unexpected actual type arguments " + actualTypeArguments) + " on ") + typeDeclarer));
            }
            /* the scope type was delivered as type reference without appropriate type arguments.
            Use references to formal type parameters
             */
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

    /**
     * Substitutes the typeParameter by its value
     *
     * @param typeParameter
     * 		- to be substituted parameter
     * @param declarer
     * 		- the declarer of typeParameter
     * @param values
     * 		- the list of parameter values
     * @return the value from values on the same position as typeParameter in declarer.getFormalCtTypeParameters()
     */
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

    /**
     *
     *
     * @return true if actualType arguments of `scope` are fitting as a subtype of superTypeArgs
     */
    private boolean isSubTypeByActualTypeArguments(spoon.reflect.reference.CtTypeReference<?> superTypeRef, java.util.List<spoon.reflect.reference.CtTypeReference<?>> expectedSuperTypeArguments) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> superTypeArgs = superTypeRef.getActualTypeArguments();
        if (superTypeArgs.isEmpty()) {
            // the raw type or not a generic type. Arguments are ignored in sub type detection
            return true;
        }
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> subTypeArgs = expectedSuperTypeArguments;
        if (subTypeArgs.isEmpty()) {
            // the raw type or not a generic type
            return true;
        }
        if ((subTypeArgs.size()) != (superTypeArgs.size())) {
            // the number of arguments is not same - it should not happen ...
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

    /**
     *
     *
     * @return true if actualType argument `subArg` is fitting as a subtype of actual type argument `superArg`
     */
    private boolean isSubTypeArg(spoon.reflect.reference.CtTypeReference<?> subArg, spoon.reflect.reference.CtTypeReference<?> superArg) {
        if (superArg instanceof spoon.reflect.reference.CtWildcardReference) {
            spoon.reflect.reference.CtWildcardReference wr = ((spoon.reflect.reference.CtWildcardReference) (superArg));
            spoon.reflect.reference.CtTypeReference<?> superBound = wr.getBoundingType();
            if (superBound.equals(wr.getFactory().Type().OBJECT)) {
                // everything extends from object, nothing is super of Object
                return wr.isUpper();
            }
            if (subArg instanceof spoon.reflect.reference.CtWildcardReference) {
                spoon.reflect.reference.CtWildcardReference subWr = ((spoon.reflect.reference.CtWildcardReference) (subArg));
                spoon.reflect.reference.CtTypeReference<?> subBound = subWr.getBoundingType();
                if (subBound.equals(wr.getFactory().Type().OBJECT)) {
                    // nothing is super of object
                    return false;
                }
                if ((wr.isUpper()) != (subWr.isUpper())) {
                    // one is "super" second is "extends"
                    return false;
                }
                if (wr.isUpper()) {
                    // both are extends
                    return subBound.isSubtypeOf(superBound);
                }
                // both are super
                return superBound.isSubtypeOf(subBound);
            }
            if (wr.isUpper()) {
                return subArg.isSubtypeOf(superBound);
            }else {
                return superBound.isSubtypeOf(subArg);
            }
        }
        // superArg is not a wildcard. Only same type is matching
        return subArg.equals(superArg);
    }

    private boolean isSameSignature(spoon.reflect.declaration.CtExecutable<?> thisMethod, spoon.reflect.declaration.CtExecutable<?> thatMethod, boolean canTypeErasure) {
        if (thisMethod == thatMethod) {
            return true;
        }
        spoon.support.visitor.ClassTypingContext.ExecutableContext mtc = new spoon.support.visitor.ClassTypingContext.ExecutableContext();
        mtc.setClassTypingContext(this);
        // only method or constructor can compare signatures
        if (thisMethod instanceof spoon.reflect.declaration.CtMethod) {
            if (thatMethod instanceof spoon.reflect.declaration.CtMethod) {
                mtc.setMethod(((spoon.reflect.declaration.CtMethod<?>) (thisMethod)));
            }else {
                return false;
            }
        }// only method or constructor can compare signatures
        else
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
            // https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.2
            spoon.reflect.declaration.CtFormalTypeDeclarer thatDeclarer = ((spoon.reflect.declaration.CtFormalTypeDeclarer) (thatExecutable));
            spoon.reflect.declaration.CtFormalTypeDeclarer thisDeclarer = getAdaptationScope();
            spoon.reflect.declaration.CtExecutable<?> thisExecutable = ((spoon.reflect.declaration.CtExecutable<?>) (thisDeclarer));
            if ((thatExecutable.getSimpleName().equals(thisExecutable.getSimpleName())) == false) {
                return false;
            }
            if ((thisExecutable.getParameters().size()) != (thatExecutable.getParameters().size())) {
                // the executables has different count of parameters they cannot have same signature
                return false;
            }
            java.util.List<spoon.reflect.declaration.CtTypeParameter> thisTypeParameters = thisDeclarer.getFormalCtTypeParameters();
            java.util.List<spoon.reflect.declaration.CtTypeParameter> thatTypeParameters = thatDeclarer.getFormalCtTypeParameters();
            boolean useTypeErasure = false;
            if ((thisTypeParameters.size()) == (thatTypeParameters.size())) {
                // the methods has same count of formal parameters
                // check that formal type parameters are same
                if ((hasSameMethodFormalTypeParameters(((spoon.reflect.declaration.CtFormalTypeDeclarer) (thatExecutable)))) == false) {
                    return false;
                }
            }else {
                // the methods has different count of formal type parameters.
                if (canTypeErasure == false) {
                    // type erasure is not allowed. So non-generic methods cannot match with generic methods
                    return false;
                }
                // non-generic method can override a generic one if type erasure is allowed
                if ((thisTypeParameters.isEmpty()) == false) {
                    // scope methods has some parameters. It is generic too, it is not a subsignature of that method
                    return false;
                }
                // scope method has zero formal type parameters. It is not generic.
                useTypeErasure = true;
            }
            java.util.List<spoon.reflect.reference.CtTypeReference<?>> thisParameterTypes = spoon.support.visitor.ClassTypingContext.ExecutableContext.getParameterTypes(thisExecutable.getParameters());
            java.util.List<spoon.reflect.reference.CtTypeReference<?>> thatParameterTypes = spoon.support.visitor.ClassTypingContext.ExecutableContext.getParameterTypes(thatExecutable.getParameters());
            // check that parameters are same after adapting to the same scope
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
                    // the type cannot be adapted.
                    return false;
                }
                // we can be in a case where thisType is CtType and thatType is CtType<?>
                // the types are not equals but it's overridden
                // in that specific case we simply remove the list of actualTypeArguments from thatType
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

