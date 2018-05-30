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
package spoon.reflect.declaration;


/**
 * This element defines a declaration of a type parameter (aka generics).
 * For example, in class A&lt;E&gt; { ... }, the "E" is modeled as an instance of CtTypeParameter.
 */
public interface CtTypeParameter extends spoon.reflect.declaration.CtType<java.lang.Object> {
    /**
     * override the return type
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeParameterReference getReference();

    /**
     *
     *
     * @return the {@link CtFormalTypeDeclarer}, which declares this {@link CtTypeParameter}
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtFormalTypeDeclarer getTypeParameterDeclarer();

    // override the return type
    @java.lang.Override
    spoon.reflect.declaration.CtTypeParameter clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <S, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setTypeMembers(java.util.List<spoon.reflect.declaration.CtTypeMember> members);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setFields(java.util.List<spoon.reflect.declaration.CtField<?>> fields);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <M, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addMethod(spoon.reflect.declaration.CtMethod<M> method);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<java.lang.Object>> C setNestedTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <N, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addNestedType(spoon.reflect.declaration.CtType<N> nestedType);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <F, C extends spoon.reflect.declaration.CtType<java.lang.Object>> C addFieldAtTop(spoon.reflect.declaration.CtField<F> field);
}

