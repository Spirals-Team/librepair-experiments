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
 * This element defines an annotation type.
 */
public interface CtAnnotationType<T extends java.lang.annotation.Annotation> extends spoon.reflect.declaration.CtType<T> {
    /**
     * Gets the methods of this annotation type which are necessarily {@link CtAnnotationMethod}.
     */
    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.declaration.CtAnnotationMethod<?>> getAnnotationMethods();

    /**
     * {@inheritDoc}
     * The method passed as parameter must be a {@link CtAnnotationMethod}.
     */
    @java.lang.Override
    <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method);

    /**
     * {@inheritDoc}
     * The methods passed as parameter must be typed by {@link CtAnnotationMethod}.
     */
    @java.lang.Override
    <C extends spoon.reflect.declaration.CtType<T>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods);

    @java.lang.Override
    spoon.reflect.declaration.CtAnnotationType<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);
}

