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
 * This code element defines an abstract invocation on a
 * {@link spoon.reflect.declaration.CtExecutable}.
 *
 * @param <T>
 * 		Return type of this invocation
 */
public interface CtAbstractInvocation<T> extends spoon.reflect.declaration.CtElement {
    /**
     * The arguments of the invocation.
     *
     * @return the expressions that define the values of the arguments
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    java.util.List<spoon.reflect.code.CtExpression<?>> getArguments();

    /**
     * Adds an argument expression to the invocation.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C addArgument(spoon.reflect.code.CtExpression<?> argument);

    /**
     * Removes an argument expression from the invocation.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    void removeArgument(spoon.reflect.code.CtExpression<?> argument);

    /**
     * Sets the invocation's arguments.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setArguments(java.util.List<spoon.reflect.code.CtExpression<?>> arguments);

    /**
     * Returns the invoked executable.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> getExecutable();

    /**
     * Sets the invoked executable.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable);
}

