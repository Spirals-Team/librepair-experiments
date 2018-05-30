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
 * This interface defines a binary operator.
 *
 * Example:
 * <pre>
 *     // 3+4 is the binary expression
 *     int x = 3 + 4;
 * </pre>
 *
 * @param <T>
 * 		Type of this expression
 */
public interface CtBinaryOperator<T> extends spoon.reflect.code.CtExpression<T> {
    /**
     * Returns the left-hand operand.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.LEFT_OPERAND)
    spoon.reflect.code.CtExpression<?> getLeftHandOperand();

    /**
     * Returns the right-hand operand.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.RIGHT_OPERAND)
    spoon.reflect.code.CtExpression<?> getRightHandOperand();

    /**
     * Sets the left-hand operand.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.LEFT_OPERAND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setLeftHandOperand(spoon.reflect.code.CtExpression<?> expression);

    /**
     * Sets the right-hand operand.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.RIGHT_OPERAND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setRightHandOperand(spoon.reflect.code.CtExpression<?> expression);

    /**
     * Sets the kind of this binary operator.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setKind(spoon.reflect.code.BinaryOperatorKind kind);

    /**
     * Gets the kind of this binary operator.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind getKind();

    @java.lang.Override
    spoon.reflect.code.CtBinaryOperator<T> clone();
}

