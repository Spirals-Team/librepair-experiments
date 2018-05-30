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
 * This element defines a field declaration.
 */
public interface CtField<T> extends spoon.reflect.code.CtRHSReceiver<T> , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeMember , spoon.reflect.declaration.CtVariable<T> {
    /**
     * The separator for a string representation of a field.
     */
    java.lang.String FIELD_SEPARATOR = "#";

    /* (non-Javadoc)

    @see spoon.reflect.declaration.CtNamedElement#getReference()
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtFieldReference<T> getReference();

    /**
     * Useful proxy to {@link #getDefaultExpression()}.
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtExpression<T> getAssignment();

    @java.lang.Override
    spoon.reflect.declaration.CtField<T> clone();
}

