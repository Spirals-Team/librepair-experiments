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
 * This code element represents the creation of a anonymous class.
 *
 * Example:
 * <pre>
 *    // an anonymous class creation
 *    Runnable r = new Runnable() {
 *     	&#64;Override
 *     	public void run() {
 *     	  System.out.println("foo");
 *     	}
 *    };
 * </pre>
 *
 * @param <T>
 * 		created type
 */
public interface CtNewClass<T> extends spoon.reflect.code.CtConstructorCall<T> {
    /**
     * Delegate to the executable reference of the new class.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments();

    /**
     * Delegate to the executable reference of the new class.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments);

    /**
     * Delegate to the executable reference of the new class.
     *
     * @see CtExecutableReference#getActualTypeArguments()
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument);

    /**
     * Gets the created class.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    spoon.reflect.declaration.CtClass<?> getAnonymousClass();

    /**
     * Sets the created class.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N extends spoon.reflect.code.CtNewClass> N setAnonymousClass(spoon.reflect.declaration.CtClass<?> anonymousClass);

    @java.lang.Override
    spoon.reflect.code.CtNewClass<T> clone();
}

