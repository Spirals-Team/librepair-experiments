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
 * This code element defines an exception variable in a catch.
 *
 * @param <T>
 * 		type of the variable
 */
public interface CtCatchVariable<T> extends spoon.reflect.code.CtCodeElement , spoon.reflect.declaration.CtMultiTypedElement , spoon.reflect.declaration.CtVariable<T> {
    /* (non-Javadoc)

    @see spoon.reflect.declaration.CtNamedElement#getReference()
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtCatchVariableReference<T> getReference();

    @java.lang.Override
    spoon.reflect.code.CtCatchVariable<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);

    /**
     * Returns type reference of the exception variable in a catch.
     * If type is unknown, or any of the types in a multi-catch is unknown, returns null.
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getType();
}

