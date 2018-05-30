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
package spoon.reflect.code;


/**
 * This abstract code element defines a typed expression.
 *
 * @param <T>
 * 		the "return type" of this expression
 */
public interface CtExpression<T> extends spoon.reflect.code.CtCodeElement , spoon.reflect.declaration.CtTypedElement<T> , spoon.template.TemplateParameter<T> {
    /**
     * Returns the type casts if any.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CAST)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTypeCasts();

    /**
     * Sets the type casts.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CAST)
    <C extends spoon.reflect.code.CtExpression<T>> C setTypeCasts(java.util.List<spoon.reflect.reference.CtTypeReference<?>> types);

    /**
     * Adds a type cast.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CAST)
    <C extends spoon.reflect.code.CtExpression<T>> C addTypeCast(spoon.reflect.reference.CtTypeReference<?> type);

    @java.lang.Override
    spoon.reflect.code.CtExpression<T> clone();
}

