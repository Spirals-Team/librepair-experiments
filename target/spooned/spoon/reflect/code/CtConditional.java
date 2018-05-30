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
 * This code element defines conditional expressions using the ? (ternary expressions).
 *
 * Example:
 * <pre>
 *     System.out.println(
 *        1==0 ? "foo" : "bar" // &lt;-- ternary conditional
 *     );
 * </pre>
 */
public interface CtConditional<T> extends spoon.reflect.code.CtExpression<T> {
    /**
     * Gets the "false" expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ELSE)
    spoon.reflect.code.CtExpression<T> getElseExpression();

    /**
     * Gets the "true" expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THEN)
    spoon.reflect.code.CtExpression<T> getThenExpression();

    /**
     * Gets the condition expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition();

    /**
     * Sets the "false" expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ELSE)
    <C extends spoon.reflect.code.CtConditional<T>> C setElseExpression(spoon.reflect.code.CtExpression<T> elseExpression);

    /**
     * Sets the "true" expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THEN)
    <C extends spoon.reflect.code.CtConditional<T>> C setThenExpression(spoon.reflect.code.CtExpression<T> thenExpression);

    /**
     * Sets the condition expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <C extends spoon.reflect.code.CtConditional<T>> C setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> condition);

    @java.lang.Override
    spoon.reflect.code.CtConditional<T> clone();
}

