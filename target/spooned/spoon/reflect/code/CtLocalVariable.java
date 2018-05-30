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
 * This code element defines a local variable definition (within an executable
 * body).
 *
 * Example:
 * <pre>
 *     // defines a local variable x
 *     int x = 0;
 * </pre>
 *
 * @param <T>
 * 		type of the variable
 * @see spoon.reflect.declaration.CtExecutable
 */
public interface CtLocalVariable<T> extends spoon.reflect.code.CtRHSReceiver<T> , spoon.reflect.code.CtStatement , spoon.reflect.declaration.CtVariable<T> {
    /* (non-Javadoc)

    @see spoon.reflect.declaration.CtNamedElement#getReference()
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtLocalVariableReference<T> getReference();

    /**
     * Useful proxy to {@link #getDefaultExpression()}.
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtExpression<T> getAssignment();

    @java.lang.Override
    spoon.reflect.code.CtLocalVariable<T> clone();
}

