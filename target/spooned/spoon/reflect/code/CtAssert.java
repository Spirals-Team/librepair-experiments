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
 * This code element defines an assert clause.
 * Example: <pre>assert 1+1==2</pre>
 */
public interface CtAssert<T> extends spoon.reflect.code.CtStatement {
    /**
     * Gets the assert expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getAssertExpression();

    /**
     * Sets the assert expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <A extends spoon.reflect.code.CtAssert<T>> A setAssertExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> asserted);

    /**
     * Gets the expression of the assertion if defined.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> getExpression();

    /**
     * Sets the expression of the assertion.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <A extends spoon.reflect.code.CtAssert<T>> A setExpression(spoon.reflect.code.CtExpression<T> expression);

    @java.lang.Override
    spoon.reflect.code.CtAssert<T> clone();
}

