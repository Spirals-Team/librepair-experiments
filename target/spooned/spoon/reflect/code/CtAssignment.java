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
 * This code element defines an assignment.
 *
 * Example:
 * <pre>
 *     int x;
 *     x = 4; // &lt;-- an assignment
 * </pre>
 *
 * @param <T>
 * 		type of assigned expression
 * @param <A>
 * 		type of expression to assign, it should extends &lt;T&gt;
 */
public interface CtAssignment<T, A extends T> extends spoon.reflect.code.CtExpression<T> , spoon.reflect.code.CtRHSReceiver<A> , spoon.reflect.code.CtStatement {
    /**
     * Returns the assigned expression on the left-hand side (where the value is stored,
     * e.g. in a variable, in an array, in a field ...).
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ASSIGNED)
    spoon.reflect.code.CtExpression<T> getAssigned();

    /**
     * Sets the assigned expression (left hand side - LHS).
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ASSIGNED)
    <C extends spoon.reflect.code.CtAssignment<T, A>> C setAssigned(spoon.reflect.code.CtExpression<T> assigned);

    @java.lang.Override
    spoon.reflect.code.CtAssignment<T, A> clone();
}

