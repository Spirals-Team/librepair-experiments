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
package spoon.support.reflect.declaration;


/**
 * The implementation for {@link spoon.reflect.declaration.CtType}.
 */
public abstract class CtTypeImpl<T> extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtType<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    java.util.List<spoon.reflect.declaration.CtTypeParameter> formalCtTypeParameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.INTERFACE)
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces = spoon.support.reflect.declaration.CtElementImpl.emptySet();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    @spoon.reflect.annotations.MetamodelPropertyField(role = { spoon.reflect.path.CtRole.TYPE_MEMBER, spoon.reflect.path.CtRole.FIELD, spoon.reflect.path.CtRole.CONSTRUCTOR, spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, spoon.reflect.path.CtRole.METHOD, spoon.reflect.path.CtRole.NESTED_TYPE })
    java.util.List<spoon.reflect.declaration.CtTypeMember> typeMembers = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    public CtTypeImpl() {
        super();
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtTypeMember> getTypeMembers() {
        return java.util.Collections.unmodifiableList(typeMembers);
    }

    /**
     * Adds a type member.
     * If it has a position, adds it at the right place according to the position (sourceStart).
     * If it is implicit, adds it at the beginning.
     * Otherwise, adds it at the end.
     */
    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C addTypeMember(spoon.reflect.declaration.CtTypeMember member) {
        if (member == null) {
            return ((C) (this));
        }
        java.util.Comparator c = new spoon.support.comparator.CtLineElementComparator();
        if (member.isImplicit()) {
            return addTypeMemberAt(0, member);
        }
        // by default, inserting at the end
        int insertionPosition = typeMembers.size();
        // we search for an insertion position only if this one has one position
        if (member.getPosition().isValidPosition()) {
            for (int i = (typeMembers.size()) - 1; i >= 0; i--) {
                spoon.reflect.declaration.CtTypeMember m = this.typeMembers.get(i);
                if ((m.isImplicit()) || ((m.getPosition().isValidPosition()) && ((c.compare(member, m)) > 0))) {
                    break;
                }
                insertionPosition--;
            }
        }
        return addTypeMemberAt(insertionPosition, member);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C addTypeMemberAt(int position, spoon.reflect.declaration.CtTypeMember member) {
        if (member == null) {
            return ((C) (this));
        }
        if ((this.typeMembers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeMember>emptyList())) {
            this.typeMembers = new java.util.ArrayList<>();
        }
        if (!(this.typeMembers.stream().anyMatch(( m) -> m == member))) {
            member.setParent(this);
            spoon.reflect.path.CtRole role;
            if (member instanceof spoon.reflect.declaration.CtMethod) {
                role = spoon.reflect.path.CtRole.METHOD;
            }else
                if (member instanceof spoon.reflect.declaration.CtConstructor) {
                    role = spoon.reflect.path.CtRole.CONSTRUCTOR;
                }else
                    if (member instanceof spoon.reflect.declaration.CtField) {
                        role = spoon.reflect.path.CtRole.FIELD;
                    }else
                        if (member instanceof spoon.reflect.declaration.CtAnonymousExecutable) {
                            role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE;
                        }else {
                            role = spoon.reflect.path.CtRole.NESTED_TYPE;
                        }



            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, role, this.typeMembers, position, member);
            if (position < (typeMembers.size())) {
                this.typeMembers.add(position, member);
            }else {
                this.typeMembers.add(member);
            }
        }
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeTypeMember(spoon.reflect.declaration.CtTypeMember member) {
        spoon.reflect.path.CtRole role;
        if (member instanceof spoon.reflect.declaration.CtMethod) {
            role = spoon.reflect.path.CtRole.METHOD;
        }else
            if (member instanceof spoon.reflect.declaration.CtConstructor) {
                role = spoon.reflect.path.CtRole.CONSTRUCTOR;
            }else
                if (member instanceof spoon.reflect.declaration.CtField) {
                    role = spoon.reflect.path.CtRole.FIELD;
                }else
                    if (member instanceof spoon.reflect.declaration.CtAnonymousExecutable) {
                        role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE;
                    }else {
                        role = spoon.reflect.path.CtRole.NESTED_TYPE;
                    }



        if ((typeMembers.size()) == 1) {
            if (typeMembers.contains(member)) {
                getFactory().getEnvironment().getModelChangeListener().onListDelete(this, role, this.typeMembers, this.typeMembers.indexOf(member), member);
                typeMembers = spoon.support.reflect.declaration.CtElementImpl.emptyList();
                return true;
            }else {
                return false;
            }
        }
        if (typeMembers.contains(member)) {
            getFactory().getEnvironment().getModelChangeListener().onListDelete(this, role, this.typeMembers, this.typeMembers.indexOf(member), member);
            return typeMembers.remove(member);
        }
        return false;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setTypeMembers(java.util.List<spoon.reflect.declaration.CtTypeMember> members) {
        for (spoon.reflect.declaration.CtTypeMember typeMember : new java.util.ArrayList<>(typeMembers)) {
            removeTypeMember(typeMember);
        }
        if ((members == null) || (members.isEmpty())) {
            this.typeMembers = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        typeMembers.clear();
        for (spoon.reflect.declaration.CtTypeMember typeMember : members) {
            addTypeMember(typeMember);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <F, C extends spoon.reflect.declaration.CtType<T>> C addFieldAtTop(spoon.reflect.declaration.CtField<F> field) {
        return addTypeMemberAt(0, field);
    }

    @java.lang.Override
    public <F, C extends spoon.reflect.declaration.CtType<T>> C addField(spoon.reflect.declaration.CtField<F> field) {
        return addTypeMember(field);
    }

    @java.lang.Override
    public <F, C extends spoon.reflect.declaration.CtType<T>> C addField(int index, spoon.reflect.declaration.CtField<F> field) {
        return addTypeMemberAt(index, field);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setFields(java.util.List<spoon.reflect.declaration.CtField<?>> fields) {
        java.util.List<spoon.reflect.declaration.CtField<?>> oldFields = getFields();
        if ((fields == null) || (fields.isEmpty())) {
            this.typeMembers.removeAll(oldFields);
            return ((C) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.FIELD, this.typeMembers, new java.util.ArrayList<>(oldFields));
        typeMembers.removeAll(oldFields);
        for (spoon.reflect.declaration.CtField<?> field : fields) {
            addField(field);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <F> boolean removeField(spoon.reflect.declaration.CtField<F> field) {
        return removeTypeMember(field);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtField<?> getField(java.lang.String name) {
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if ((typeMember instanceof spoon.reflect.declaration.CtField) && (((spoon.reflect.declaration.CtField) (typeMember)).getSimpleName().equals(name))) {
                return ((spoon.reflect.declaration.CtField<?>) (typeMember));
            }
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<?> getDeclaredField(java.lang.String name) {
        spoon.reflect.declaration.CtField<?> field = getField(name);
        return field != null ? getFactory().Field().createReference(field) : null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<?> getDeclaredOrInheritedField(java.lang.String fieldName) {
        spoon.reflect.declaration.CtField<?> field = map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtField.class)).select(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, fieldName)).first();
        return field == null ? null : field.getReference();
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtField<?>> getFields() {
        java.util.List<spoon.reflect.declaration.CtField<?>> fields = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                fields.add(((spoon.reflect.declaration.CtField<?>) (typeMember)));
            }
        }
        return java.util.Collections.unmodifiableList(fields);
    }

    @java.lang.Override
    public <N, C extends spoon.reflect.declaration.CtType<T>> C addNestedType(spoon.reflect.declaration.CtType<N> nestedType) {
        return addTypeMember(nestedType);
    }

    @java.lang.Override
    public <N> boolean removeNestedType(spoon.reflect.declaration.CtType<N> nestedType) {
        return removeTypeMember(nestedType);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setNestedTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes) {
        java.util.Set<spoon.reflect.declaration.CtType<?>> oldNestedTypes = getNestedTypes();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.NESTED_TYPE, typeMembers, oldNestedTypes);
        if ((nestedTypes == null) || (nestedTypes.isEmpty())) {
            this.typeMembers.removeAll(oldNestedTypes);
            return ((C) (this));
        }
        typeMembers.removeAll(oldNestedTypes);
        for (spoon.reflect.declaration.CtType<?> nestedType : nestedTypes) {
            addNestedType(nestedType);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getUsedTypes(boolean includeSamePackage) {
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> typeRefs = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        for (spoon.reflect.reference.CtTypeReference<?> typeRef : spoon.reflect.visitor.Query.getReferences(this, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class))) {
            if ((isValidTypeReference(typeRef)) && (shouldIncludeSamePackage(includeSamePackage, typeRef))) {
                typeRefs.add(typeRef);
            }
        }
        return typeRefs;
    }

    private boolean shouldIncludeSamePackage(boolean includeSamePackage, spoon.reflect.reference.CtTypeReference<?> typeRef) {
        return includeSamePackage || (((getPackage()) != null) && (!(spoon.support.reflect.declaration.CtTypeImpl.getPackageReference(typeRef).equals(getPackage().getReference()))));
    }

    private boolean isValidTypeReference(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        return !((((isFromJavaLang(typeRef)) || (typeRef.isPrimitive())) || (typeRef instanceof spoon.reflect.reference.CtArrayTypeReference)) || (spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME.equals(typeRef.toString())));
    }

    private boolean isFromJavaLang(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        return ((typeRef.getPackage()) != null) && ("java.lang".equals(typeRef.getPackage().toString()));
    }

    /**
     * Return the package reference for the corresponding type reference. For
     * inner type, return the package reference of the top-most enclosing type.
     * This helper method is meant to deal with package references that are
     * <code>null</code> for inner types.
     *
     * @param tref
     * 		the type reference
     * @return the corresponding package reference
     * @see CtTypeReference#getPackage()
     * @since 4.0
     */
    private static spoon.reflect.reference.CtPackageReference getPackageReference(spoon.reflect.reference.CtTypeReference<?> tref) {
        spoon.reflect.reference.CtPackageReference pref = tref.getPackage();
        while (pref == null) {
            tref = tref.getDeclaringType();
            pref = tref.getPackage();
        } 
        return pref;
    }

    @java.lang.Override
    public java.lang.Class<T> getActualClass() {
        return getFactory().Type().createReference(this).getActualClass();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<?> getDeclaringType() {
        try {
            return getParent(spoon.reflect.declaration.CtType.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException ex) {
            return null;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <T> spoon.reflect.declaration.CtType<T> getTopLevelType() {
        spoon.reflect.declaration.CtType<?> top = this;
        while (true) {
            spoon.reflect.declaration.CtType<?> nextTop = top.getDeclaringType();
            if (nextTop == null) {
                return ((spoon.reflect.declaration.CtType<T>) (top));
            }
            top = nextTop;
        } 
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <N extends spoon.reflect.declaration.CtType<?>> N getNestedType(final java.lang.String name) {
        class NestedTypeScanner extends spoon.reflect.visitor.EarlyTerminatingScanner<spoon.reflect.declaration.CtType<?>> {
            private boolean checkType(spoon.reflect.declaration.CtType<?> type) {
                if ((type.getSimpleName().equals(name)) && (spoon.support.reflect.declaration.CtTypeImpl.this.equals(type.getDeclaringType()))) {
                    setResult(type);
                    terminate();
                    return true;
                }
                return false;
            }

            @java.lang.Override
            public <U> void visitCtClass(spoon.reflect.declaration.CtClass<U> ctClass) {
                if (!(checkType(ctClass))) {
                    final java.util.List<spoon.reflect.declaration.CtTypeMember> typeMembers = new java.util.ArrayList<>();
                    for (spoon.reflect.declaration.CtTypeMember typeMember : ctClass.getTypeMembers()) {
                        if (((typeMember instanceof spoon.reflect.declaration.CtType) || (typeMember instanceof spoon.reflect.declaration.CtConstructor)) || (typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                            typeMembers.add(typeMember);
                        }
                    }
                    scan(typeMembers);
                }
            }

            @java.lang.Override
            public <U> void visitCtInterface(spoon.reflect.declaration.CtInterface<U> intrface) {
                if (!(checkType(intrface))) {
                    final java.util.List<spoon.reflect.declaration.CtTypeMember> typeMembers = new java.util.ArrayList<>();
                    for (spoon.reflect.declaration.CtTypeMember typeMember : intrface.getTypeMembers()) {
                        if ((typeMember instanceof spoon.reflect.declaration.CtType) || (typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                            typeMembers.add(typeMember);
                        }
                    }
                    scan(typeMembers);
                }
            }

            @java.lang.Override
            public <U extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<U> ctEnum) {
                if (!(checkType(ctEnum))) {
                    final java.util.List<spoon.reflect.declaration.CtTypeMember> typeMembers = new java.util.ArrayList<>();
                    for (spoon.reflect.declaration.CtTypeMember typeMember : ctEnum.getTypeMembers()) {
                        if (((typeMember instanceof spoon.reflect.declaration.CtType) || (typeMember instanceof spoon.reflect.declaration.CtConstructor)) || (typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                            typeMembers.add(typeMember);
                        }
                    }
                    scan(typeMembers);
                }
            }

            @java.lang.Override
            public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
                if (!(checkType(annotationType))) {
                    scan(annotationType.getNestedTypes());
                }
            }
        }
        NestedTypeScanner scanner = new NestedTypeScanner();
        scanner.scan(this);
        return ((N) (scanner.getResult()));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtType<?>> getNestedTypes() {
        java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                nestedTypes.add(((spoon.reflect.declaration.CtType<?>) (typeMember)));
            }
        }
        return java.util.Collections.unmodifiableSet(nestedTypes);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getPackage() {
        if ((parent) instanceof spoon.reflect.declaration.CtPackage) {
            return ((spoon.reflect.declaration.CtPackage) (getParent()));
        }else
            if ((parent) instanceof spoon.reflect.declaration.CtType) {
                return ((spoon.reflect.declaration.CtType<?>) (parent)).getPackage();
            }else {
                return null;
            }

    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getReference() {
        return getFactory().Type().createReference(this);
    }

    @java.lang.Override
    public boolean isTopLevel() {
        return ((getDeclaringType()) == null) && ((getPackage()) != null);
    }

    @java.lang.Override
    public void compileAndReplaceSnippets() {
        spoon.support.compiler.SnippetCompilationHelper.compileAndReplaceSnippetsIn(this);
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        return modifierHandler.getModifiers();
    }

    @java.lang.Override
    public boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier) {
        return getModifiers().contains(modifier);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers) {
        modifierHandler.setModifiers(modifiers);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C addModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.addModifier(modifier);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C removeModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.removeModifier(modifier);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C setVisibility(spoon.reflect.declaration.ModifierKind visibility) {
        modifierHandler.setVisibility(visibility);
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.ModifierKind getVisibility() {
        return modifierHandler.getVisibility();
    }

    @java.lang.Override
    public java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers() {
        return this.modifierHandler.getExtendedModifiers();
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers) {
        this.modifierHandler.setExtendedModifiers(extendedModifiers);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean isPrimitive() {
        return false;
    }

    @java.lang.Override
    public boolean isAnonymous() {
        return false;
    }

    @java.lang.Override
    public boolean isLocalType() {
        return (isParentInitialized()) && ((getParent()) instanceof spoon.reflect.code.CtBlock);
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        // overridden in subclasses.
        return null;
    }

    @java.lang.Override
    public boolean isClass() {
        return false;
    }

    @java.lang.Override
    public boolean isInterface() {
        return false;
    }

    @java.lang.Override
    public boolean isAnnotationType() {
        return false;
    }

    @java.lang.Override
    public boolean isEnum() {
        return false;
    }

    @java.lang.Override
    public boolean isGenerics() {
        return false;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtFieldReference<?>> getAllFields() {
        final java.util.List<spoon.reflect.reference.CtFieldReference<?>> fields = new java.util.ArrayList<>();
        map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtField.class)).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtField<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtField<?> field) {
                fields.add(field.getReference());
            }
        });
        return fields;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFields() {
        if (typeMembers.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        final java.util.List<spoon.reflect.reference.CtFieldReference<?>> fields = new java.util.ArrayList<>(typeMembers.size());
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                fields.add(((spoon.reflect.declaration.CtField) (typeMember)).getReference());
            }
        }
        return fields;
    }

    @java.lang.Override
    public <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method) {
        if (method != null) {
            for (spoon.reflect.declaration.CtTypeMember typeMember : new java.util.ArrayList<>(typeMembers)) {
                if (!(typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                    continue;
                }
                spoon.reflect.declaration.CtMethod<?> m = ((spoon.reflect.declaration.CtMethod<?>) (typeMember));
                if (m.getSignature().equals(method.getSignature())) {
                    // replace old method by new one (based on signature and not equality)
                    // we have to do it by hand
                    removeTypeMember(m);
                }else {
                    // checking contract signature implies equal
                    if ((!(factory.getEnvironment().checksAreSkipped())) && (m.equals(method))) {
                        throw new java.lang.AssertionError("violation of core contract! different signature but same equal");
                    }
                }
            }
        }
        return addTypeMember(method);
    }

    @java.lang.Override
    public <M> boolean removeMethod(spoon.reflect.declaration.CtMethod<M> method) {
        return removeTypeMember(method);
    }

    @java.lang.Override
    public <S, C extends spoon.reflect.declaration.CtType<T>> C addSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac) {
        if (interfac == null) {
            return ((C) (this));
        }
        if ((interfaces) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptySet())) {
            interfaces = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        }
        interfac.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.INTERFACE, this.interfaces, interfac);
        interfaces.add(interfac);
        return ((C) (this));
    }

    @java.lang.Override
    public <S> boolean removeSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac) {
        getFactory().getEnvironment().getModelChangeListener().onSetDelete(this, spoon.reflect.path.CtRole.INTERFACE, interfaces, interfac);
        // contains() not needed. see comment in removeMethod()
        if ((interfaces) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptySet())) {
            return false;
        }// contains() not needed. see comment in removeMethod()
        else
            if ((interfaces.size()) == 1) {
                if (interfaces.contains(interfac)) {
                    interfaces = spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptySet();
                    return true;
                }else {
                    return false;
                }
            }else {
                return (interfaces.contains(interfac)) && (interfaces.remove(interfac));
            }

    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters() {
        return formalCtTypeParameters;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, formalCtTypeParameters, new java.util.ArrayList<>(formalCtTypeParameters));
        if ((formalTypeParameters == null) || (formalTypeParameters.isEmpty())) {
            this.formalCtTypeParameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            this.formalCtTypeParameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        this.formalCtTypeParameters.clear();
        for (spoon.reflect.declaration.CtTypeParameter formalTypeParameter : formalTypeParameters) {
            addFormalCtTypeParameter(formalTypeParameter);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C addFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter) {
        if (formalTypeParameter == null) {
            return ((C) (this));
        }
        if ((formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            formalCtTypeParameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        formalTypeParameter.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, this.formalCtTypeParameters, formalTypeParameter);
        formalCtTypeParameters.add(formalTypeParameter);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter) {
        if ((formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, formalCtTypeParameters, formalCtTypeParameters.indexOf(formalTypeParameter), formalTypeParameter);
        return formalCtTypeParameters.remove(formalTypeParameter);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (!(typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                continue;
            }
            spoon.reflect.declaration.CtMethod<R> m = ((spoon.reflect.declaration.CtMethod<R>) (typeMember));
            if (m.getSimpleName().equals(name)) {
                if (!(m.getType().equals(returnType))) {
                    continue;
                }
                boolean cont = (m.getParameters().size()) == (parameterTypes.length);
                for (int i = 0; (cont && (i < (m.getParameters().size()))) && (i < (parameterTypes.length)); i++) {
                    if (!(m.getParameters().get(i).getType().getQualifiedName().equals(parameterTypes[i].getQualifiedName()))) {
                        cont = false;
                    }
                }
                if (cont) {
                    return m;
                }
            }
        }
        return null;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        if (name == null) {
            return null;
        }
        for (spoon.reflect.declaration.CtMethod<?> candidate : getMethodsByName(name)) {
            boolean cont = (candidate.getParameters().size()) == (parameterTypes.length);
            for (int i = 0; (cont && (i < (candidate.getParameters().size()))) && (i < (parameterTypes.length)); i++) {
                final spoon.reflect.reference.CtTypeReference<?> ctParameterType = candidate.getParameters().get(i).getType();
                final spoon.reflect.reference.CtTypeReference<?> parameterType = parameterTypes[i];
                if (parameterType instanceof spoon.reflect.reference.CtArrayTypeReference) {
                    if (ctParameterType instanceof spoon.reflect.reference.CtArrayTypeReference) {
                        if (!(isSameParameter(candidate, ((spoon.reflect.reference.CtArrayTypeReference) (ctParameterType)).getComponentType(), ((spoon.reflect.reference.CtArrayTypeReference) (parameterType)).getComponentType()))) {
                            cont = false;
                        }else {
                            if (!((((spoon.reflect.reference.CtArrayTypeReference) (ctParameterType)).getDimensionCount()) == (((spoon.reflect.reference.CtArrayTypeReference) (parameterType)).getDimensionCount()))) {
                                cont = false;
                            }
                        }
                    }else {
                        cont = false;
                    }
                }else
                    if (!(isSameParameter(candidate, ctParameterType, parameterType))) {
                        cont = false;
                    }

            }
            if (cont) {
                return ((spoon.reflect.declaration.CtMethod<R>) (candidate));
            }
        }
        return null;
    }

    private boolean isSameParameter(spoon.reflect.declaration.CtMethod<?> method, spoon.reflect.reference.CtTypeReference<?> ctParameterType, spoon.reflect.reference.CtTypeReference<?> expectedType) {
        if (expectedType instanceof spoon.reflect.reference.CtTypeParameterReference) {
            /* the expectedType is a generic parameter whose declaration should be searched in scope of method
            (not in scope of it's parent, where it can found another/wrong type parameter declaration of same name.
             */
            spoon.reflect.reference.CtTypeParameterReference tpr = ((spoon.reflect.reference.CtTypeParameterReference) (expectedType));
            expectedType = tpr.clone();
            expectedType.setParent(method);
            if ((expectedType.getDeclaration()) == null) {
                return false;
            }
        }
        // expectedType type is generic, ctParameterType is real type
        // ctParameterType is generic, expectedType type is real type
        // both are real types
        if ((expectedType instanceof spoon.reflect.reference.CtTypeParameterReference) && (ctParameterType instanceof spoon.reflect.reference.CtTypeParameterReference)) {
            // both types are generic
            if (!(ctParameterType.equals(expectedType))) {
                return false;
            }
        }// expectedType type is generic, ctParameterType is real type
        // ctParameterType is generic, expectedType type is real type
        // both are real types
        else
            if (expectedType instanceof spoon.reflect.reference.CtTypeParameterReference) {
                if (!(expectedType.getTypeErasure().getQualifiedName().equals(ctParameterType.getQualifiedName()))) {
                    return false;
                }
            }else
                if (ctParameterType instanceof spoon.reflect.reference.CtTypeParameterReference) {
                    spoon.reflect.declaration.CtTypeParameter declaration = ((spoon.reflect.declaration.CtTypeParameter) (ctParameterType.getDeclaration()));
                    if ((declaration != null) && ((declaration.getSuperclass()) instanceof spoon.reflect.reference.CtIntersectionTypeReference)) {
                        for (spoon.reflect.reference.CtTypeReference<?> ctTypeReference : declaration.getSuperclass().asCtIntersectionTypeReference().getBounds()) {
                            if (ctTypeReference.equals(expectedType)) {
                                return true;
                            }
                        }
                    }else
                        if ((declaration != null) && ((declaration.getSuperclass()) != null)) {
                            return declaration.getSuperclass().equals(expectedType);
                        }else {
                            return getFactory().Type().objectType().equals(expectedType);
                        }

                }else
                    if (!(expectedType.getQualifiedName().equals(ctParameterType.getQualifiedName()))) {
                        return false;
                    }



        return true;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethods() {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods = new spoon.support.util.SignatureBasedSortedSet<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtMethod) {
                methods.add(((spoon.reflect.declaration.CtMethod<?>) (typeMember)));
            }
        }
        return java.util.Collections.unmodifiableSet(methods);
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethodsAnnotatedWith(spoon.reflect.reference.CtTypeReference<?>... annotationTypes) {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> result = new spoon.support.util.SignatureBasedSortedSet<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (!(typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                continue;
            }
            spoon.reflect.declaration.CtMethod<?> m = ((spoon.reflect.declaration.CtMethod<?>) (typeMember));
            for (spoon.reflect.declaration.CtAnnotation<?> a : m.getAnnotations()) {
                if (java.util.Arrays.asList(annotationTypes).contains(a.getAnnotationType())) {
                    result.add(m);
                }
            }
        }
        return result;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtMethod<?>> getMethodsByName(java.lang.String name) {
        java.util.List<spoon.reflect.declaration.CtMethod<?>> result = new java.util.ArrayList<>(1);
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (!(typeMember instanceof spoon.reflect.declaration.CtMethod)) {
                continue;
            }
            spoon.reflect.declaration.CtMethod<?> m = ((spoon.reflect.declaration.CtMethod<?>) (typeMember));
            if (name.equals(m.getSimpleName())) {
                result.add(m);
            }
        }
        return result;
    }

    @java.lang.Override
    public boolean hasMethod(spoon.reflect.declaration.CtMethod<?> method) {
        if (method == null) {
            return false;
        }
        final java.lang.String over = method.getSignature();
        for (spoon.reflect.declaration.CtMethod<?> m : getMethods()) {
            if (m.getSignature().equals(over)) {
                return true;
            }
        }
        // Checking whether a super class has the method.
        final spoon.reflect.reference.CtTypeReference<?> superCl = getSuperclass();
        try {
            if ((superCl != null) && (superCl.getTypeDeclaration().hasMethod(method))) {
                return true;
            }
        } catch (spoon.SpoonException ex) {
            // No matter, trying something else.
        }
        // Finally, checking whether an interface has the method.
        for (spoon.reflect.reference.CtTypeReference<?> interf : getSuperInterfaces()) {
            try {
                if (interf.getTypeDeclaration().hasMethod(method)) {
                    return true;
                }
            } catch (spoon.SpoonException ex) {
                // No matter, trying something else.
            }
        }
        return false;
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        if (isTopLevel()) {
            if (((getPackage()) != null) && (!(getPackage().isUnnamedPackage()))) {
                return ((getPackage().getQualifiedName()) + ".") + (getSimpleName());
            }else {
                return getSimpleName();
            }
        }else
            if ((getDeclaringType()) != null) {
                return ((getDeclaringType().getQualifiedName()) + (spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR)) + (getSimpleName());
            }else {
                return getSimpleName();
            }

    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces() {
        return interfaces;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods) {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> allMethods = getMethods();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.METHOD, this.typeMembers, new java.util.ArrayList(allMethods));
        typeMembers.removeAll(allMethods);
        if ((methods == null) || (methods.isEmpty())) {
            return ((C) (this));
        }
        for (spoon.reflect.declaration.CtMethod<?> meth : methods) {
            addMethod(meth);
        }
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        // overridden in subclasses.
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces) {
        if ((interfaces == null) || (interfaces.isEmpty())) {
            this.interfaces = spoon.support.reflect.declaration.CtElementImpl.emptySet();
            return ((C) (this));
        }
        if ((this.interfaces) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptySet())) {
            this.interfaces = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(this, spoon.reflect.path.CtRole.INTERFACE, this.interfaces, new java.util.HashSet<>(this.interfaces));
        this.interfaces.clear();
        for (spoon.reflect.reference.CtTypeReference<?> anInterface : interfaces) {
            addSuperInterface(anInterface);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        if (getMethods().isEmpty()) {
            return java.util.Collections.emptyList();
        }
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>(getMethods().size());
        for (spoon.reflect.declaration.CtExecutable<?> m : getMethods()) {
            l.add(m.getReference());
        }
        return java.util.Collections.unmodifiableList(l);
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables() {
        java.util.Set<spoon.reflect.reference.CtExecutableReference<?>> l = new spoon.support.util.SignatureBasedSortedSet();
        for (spoon.reflect.declaration.CtMethod<?> m : getAllMethods()) {
            l.add(((spoon.reflect.reference.CtExecutableReference<?>) (m.getReference())));
        }
        return l;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getAllMethods() {
        final java.util.Set<spoon.reflect.declaration.CtMethod<?>> l = new java.util.HashSet<>();
        final spoon.support.visitor.ClassTypingContext ctc = new spoon.support.visitor.ClassTypingContext(this);
        map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtMethod.class)).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtMethod<?> currentMethod) {
                for (spoon.reflect.declaration.CtMethod<?> alreadyVisitedMethod : l) {
                    if (ctc.isSameSignature(currentMethod, alreadyVisitedMethod)) {
                        return;
                    }
                }
                l.add(currentMethod);
            }
        });
        return java.util.Collections.unmodifiableSet(l);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getTypeErasure() {
        return getReference();
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow;

    @java.lang.Override
    public boolean isShadow() {
        return isShadow;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_SHADOW, isShadow, this.isShadow);
        this.isShadow = isShadow;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<T> clone() {
        return ((spoon.reflect.declaration.CtType<T>) (super.clone()));
    }

    @java.lang.Override
    public boolean isPublic() {
        return this.modifierHandler.isPublic();
    }

    @java.lang.Override
    public boolean isPrivate() {
        return this.modifierHandler.isPrivate();
    }

    @java.lang.Override
    public boolean isProtected() {
        return this.modifierHandler.isProtected();
    }

    @java.lang.Override
    public boolean isFinal() {
        return this.modifierHandler.isFinal();
    }

    @java.lang.Override
    public boolean isStatic() {
        return this.modifierHandler.isStatic();
    }

    @java.lang.Override
    public boolean isAbstract() {
        return this.modifierHandler.isAbstract();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<?> copyType() {
        return spoon.refactoring.Refactoring.copyType(this);
    }
}

