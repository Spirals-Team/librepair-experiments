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
 * This element defines an executable parameter declaration.
 *
 * @see CtExecutable
 */
public interface CtParameter<T> extends spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtVariable<T> {
    /**
     * Gets the executable that is the parent declaration of this parameter
     * declaration.
     *
     * (Overriding the return type)
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtExecutable<?> getParent();

    /**
     * Returns <tt>true</tt> if this parameter accepts a variable number of
     * arguments (must be the last parameter of
     * {@link CtExecutable#getParameters()}).
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_VARARGS)
    boolean isVarArgs();

    /**
     * Sets this parameter to have varargs.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_VARARGS)
    <C extends spoon.reflect.declaration.CtParameter<T>> C setVarArgs(boolean varArgs);

    /**
     * overriding the return type
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtParameterReference<T> getReference();

    @java.lang.Override
    spoon.reflect.declaration.CtParameter<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);
}

