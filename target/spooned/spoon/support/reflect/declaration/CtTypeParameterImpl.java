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


public class CtTypeParameterImpl extends spoon.support.reflect.declaration.CtTypeImpl<java.lang.Object> implements spoon.reflect.declaration.CtTypeParameter {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    spoon.reflect.reference.CtTypeReference<?> superClass;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTypeParameter(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        return superClass;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        if (superClass != null) {
            superClass.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SUPER_TYPE, superClass, this.superClass);
        this.superClass = superClass;
        return ((C) (this));
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        return simpleName;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeParameterReference getReference() {
        return getFactory().Type().createReference(this);
    }

    @java.lang.Override
    public boolean isGenerics() {
        return true;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter clone() {
        return ((spoon.reflect.declaration.CtTypeParameter) (super.clone()));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtFormalTypeDeclarer getTypeParameterDeclarer() {
        try {
            return getParent(spoon.reflect.declaration.CtFormalTypeDeclarer.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <F, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addFieldAtTop(spoon.reflect.declaration.CtField<F> field) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <F, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addField(spoon.reflect.declaration.CtField<F> field) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <F, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addField(int index, spoon.reflect.declaration.CtField<F> field) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setFields(java.util.List<spoon.reflect.declaration.CtField<?>> fields) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public <F> boolean removeField(spoon.reflect.declaration.CtField<F> field) {
        // unsettable property
        return false;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtField<?> getField(java.lang.String name) {
        return null;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtField<?>> getFields() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <N, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addNestedType(spoon.reflect.declaration.CtType<N> nestedType) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <N> boolean removeNestedType(spoon.reflect.declaration.CtType<N> nestedType) {
        // unsettable property
        return false;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setNestedTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    public <N extends spoon.reflect.declaration.CtType<?>> N getNestedType(java.lang.String name) {
        return null;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.declaration.CtType<?>> getNestedTypes() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.declaration.CtPackage getPackage() {
        return null;
    }

    @java.lang.Override
    public boolean isTopLevel() {
        return false;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier) {
        return false;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtModifiable> C setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtModifiable> C addModifier(spoon.reflect.declaration.ModifierKind modifier) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtModifiable> C removeModifier(spoon.reflect.declaration.ModifierKind modifier) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtModifiable> C setVisibility(spoon.reflect.declaration.ModifierKind visibility) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.ModifierKind getVisibility() {
        return null;
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
        return false;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtFieldReference<?>> getAllFields() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFields() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> superTypeRef) {
        if (superTypeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
            // the type is type parameter too. Use appropriate sub type checking algorithm
            spoon.reflect.declaration.CtTypeParameter superTypeParam = ((spoon.reflect.declaration.CtTypeParameter) (superTypeRef.getDeclaration()));
            return spoon.support.reflect.declaration.CtTypeParameterImpl.isSubtypeOf(getFactory().Type().createTypeAdapter(getTypeParameterDeclarer()), this, superTypeParam);
        }
        // type is normal type
        return getTypeErasure().isSubtypeOf(superTypeRef);
    }

    private static boolean isSubtypeOf(spoon.support.visitor.GenericTypeAdapter typeAdapter, spoon.reflect.declaration.CtTypeParameter subTypeParam, spoon.reflect.declaration.CtTypeParameter superTypeParam) {
        while (subTypeParam != null) {
            if (spoon.support.reflect.declaration.CtTypeParameterImpl.isSameInSameScope(subTypeParam, typeAdapter.adaptType(superTypeParam))) {
                // both type params are same
                return true;
            }
            spoon.reflect.reference.CtTypeReference<?> superTypeOfSubTypeParam = subTypeParam.getSuperclass();
            if (superTypeOfSubTypeParam == null) {
                // there is no super type defined, so they are different type parameters
                return false;
            }
            if (superTypeOfSubTypeParam instanceof spoon.reflect.reference.CtTypeParameterReference) {
                subTypeParam = ((spoon.reflect.reference.CtTypeParameterReference) (superTypeOfSubTypeParam)).getDeclaration();
            }else {
                // the super type is not type parameter. Normal type cannot be a super type of generic parameter
                return false;
            }
        } 
        return false;
    }

    /**
     * Note: This method expects that both arguments are already adapted to the same scope
     *
     * @param typeParam
     * 		a type param 1
     * @param typeRef
     * 		a reference to some type 2
     * @return true if typeParam and typeRef represents same type parameter.
     */
    private static boolean isSameInSameScope(spoon.reflect.declaration.CtTypeParameter typeParam, spoon.reflect.reference.CtTypeReference<?> typeRef) {
        if (typeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
            return typeParam.getSimpleName().equals(((spoon.reflect.reference.CtTypeParameterReference) (typeRef)).getSimpleName());
        }
        return false;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getTypeErasure() {
        spoon.reflect.reference.CtTypeReference<?> boundType = spoon.support.reflect.declaration.CtTypeParameterImpl.getBound(this);
        return boundType.getTypeErasure();
    }

    private static spoon.reflect.reference.CtTypeReference<?> getBound(spoon.reflect.declaration.CtTypeParameter typeParam) {
        spoon.reflect.reference.CtTypeReference<?> bound = typeParam.getSuperclass();
        if (bound == null) {
            bound = typeParam.getFactory().Type().OBJECT;
        }
        return bound;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <M, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addMethod(spoon.reflect.declaration.CtMethod<M> method) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <M> boolean removeMethod(spoon.reflect.declaration.CtMethod<M> method) {
        // unsettable property
        return false;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <S, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <S> boolean removeSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac) {
        // unsettable property
        return false;
    }

    @java.lang.Override
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        return null;
    }

    @java.lang.Override
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        return null;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethods() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethodsAnnotatedWith(spoon.reflect.reference.CtTypeReference<?>... annotationTypes) {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtMethod<?>> getMethodsByName(java.lang.String name) {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getAllMethods() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters) {
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtTypeMember> getTypeMembers() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setTypeMembers(java.util.List<spoon.reflect.declaration.CtTypeMember> members) {
        return ((C) (this));
    }
}

