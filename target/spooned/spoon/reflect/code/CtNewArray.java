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
 * This code element defines the inline creation of a new array.
 *
 * Example:
 * <pre>
 *     // inline creation of array content
 *     int[] x = new int[] { 0, 1, 42}
 * </pre>
 *
 * @param <T>
 * 		type of this array (should be a array...)
 */
public interface CtNewArray<T> extends spoon.reflect.code.CtExpression<T> {
    /**
     * Gets the expressions that define the array's dimensions.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DIMENSION)
    java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> getDimensionExpressions();

    /**
     * Sets the expressions that define the array's dimensions.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C setDimensionExpressions(java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> dimensions);

    /**
     * Adds a dimension expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C addDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension);

    /**
     * Removes a dimension expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DIMENSION)
    boolean removeDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension);

    /**
     * Gets the initialization expressions.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    java.util.List<spoon.reflect.code.CtExpression<?>> getElements();

    /**
     * Sets the initialization expressions.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C setElements(java.util.List<spoon.reflect.code.CtExpression<?>> expression);

    /**
     * Adds an element.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtNewArray<T>> C addElement(spoon.reflect.code.CtExpression<?> expression);

    /**
     * Removes an element.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    boolean removeElement(spoon.reflect.code.CtExpression<?> expression);

    @java.lang.Override
    spoon.reflect.code.CtNewArray<T> clone();
}

