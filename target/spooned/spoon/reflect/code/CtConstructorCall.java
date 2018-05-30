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
 * This code element represents a constructor call.
 *
 * Example:<pre>
 *     new Object();
 * </pre>
 *
 * To build a constructor call, see {@link DefaultCoreFactory#createConstructorCall()}.
 *
 * @param <T>
 * 		created type
 */
public interface CtConstructorCall<T> extends spoon.reflect.code.CtAbstractInvocation<T> , spoon.reflect.code.CtStatement , spoon.reflect.code.CtTargetedExpression<T, spoon.reflect.code.CtExpression<?>> , spoon.reflect.reference.CtActualTypeContainer {
    /**
     * Delegate to the executable reference of the constructor call.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments();

    /**
     * Delegate to the executable reference of the constructor call.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments);

    /**
     * Delegate to the executable reference of the constructor call.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument);

    @java.lang.Override
    spoon.reflect.code.CtConstructorCall<T> clone();

    @java.lang.Override
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> getType();
}

