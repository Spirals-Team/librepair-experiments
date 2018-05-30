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
 * This element defines an annotation method declared in an annotation type.
 */
public interface CtAnnotationMethod<T> extends spoon.reflect.declaration.CtMethod<T> {
    /**
     * Gets the default expression assigned to the annotation method.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    spoon.reflect.code.CtExpression<T> getDefaultExpression();

    /**
     * Sets the default expression assigned to the annotation method.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    <C extends spoon.reflect.declaration.CtAnnotationMethod<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);

    @java.lang.Override
    spoon.reflect.declaration.CtAnnotationMethod<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.code.CtBodyHolder> T1 setBody(spoon.reflect.code.CtStatement body);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.declaration.CtExecutable<T>> T1 setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.declaration.CtExecutable<T>> T1 setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);
}

