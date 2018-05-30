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
package spoon.reflect.reference;


/**
 * This interface defines a reference to a
 * {@link spoon.reflect.declaration.CtExecutable}. It can be a
 * {@link spoon.reflect.declaration.CtMethod} or a
 * {@link spoon.reflect.declaration.CtConstructor}.
 */
public interface CtExecutableReference<T> extends spoon.reflect.reference.CtActualTypeContainer , spoon.reflect.reference.CtReference {
    java.lang.String CONSTRUCTOR_NAME = "<init>";

    java.lang.String LAMBDA_NAME_PREFIX = "lambda$";

    java.lang.String UNKNOWN_TYPE = "<unknown>";

    /**
     * Tells if this is a reference to a constructor.
     */
    boolean isConstructor();

    /**
     * Gets the runtime method that corresponds to an executable reference if
     * any.
     *
     * @return the method (null if not found)
     */
    java.lang.reflect.Method getActualMethod();

    /**
     * Gets the runtime constructor that corresponds to an executable reference
     * if any.
     *
     * @return the constructor (null if not found)
     */
    java.lang.reflect.Constructor<?> getActualConstructor();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtExecutable<T> getDeclaration();

    /**
     * Returns a subtype {@link CtExecutable} that corresponds to the reference
     * even if its declaring type isn't in the Spoon source path  (in this case,
     * the Spoon elements are built with runtime reflection).
     *
     * @return the executable declaration that corresponds to the reference.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtExecutable<T> getExecutableDeclaration();

    /**
     * Gets the reference to the type that declares this executable.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getDeclaringType();

    /**
     * For methods, gets the return type of the executable (may be null in noclasspath mode).
     * For constructors, gets the constructor class (which is also the return type of the contructor calls).
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> getType();

    /**
     * Gets parameters of the executable.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ARGUMENT_TYPE)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getParameters();

    /**
     * Sets parameters of the executable.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT_TYPE)
    <C extends spoon.reflect.reference.CtExecutableReference<T>> C setParameters(java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters);

    /**
     * Returns <code>true</code> if this executable overrides the given
     * executable.
     */
    boolean isOverriding(spoon.reflect.reference.CtExecutableReference<?> executable);

    /**
     * Returns the method overridden by this one, if exists (null otherwise).
     * The returned method is searched in the superclass hierarchy
     * (and not in the super-interfaces).
     * The returned method can be an abstract method from an abstract class, a super implementation, or even a method from Object.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtExecutableReference<?> getOverridingExecutable();

    /**
     * Gets an overriding executable for this executable from a given subtype,
     * if exists.
     *
     * @param <S>
     * 		subtype of T
     * @param subType
     * 		starting bottom type to find an overriding executable
     * 		(subtypes are not tested)
     * @return the first found (most concrete) executable that overrides this
     * executable (null if none found)
     */
    <S extends T> spoon.reflect.reference.CtExecutableReference<S> getOverridingExecutable(spoon.reflect.reference.CtTypeReference<?> subType);

    /**
     * Tells if the referenced executable is static.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean isStatic();

    /**
     * Sets the declaring type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    <C extends spoon.reflect.reference.CtExecutableReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType);

    /**
     * Sets this executable reference to be static or not.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    <C extends spoon.reflect.reference.CtExecutableReference<T>> C setStatic(boolean b);

    /**
     * Sets the type of the variable.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE)
    <C extends spoon.reflect.reference.CtExecutableReference<T>> C setType(spoon.reflect.reference.CtTypeReference<T> type);

    /**
     * Tells if the referenced executable is final.
     */
    boolean isFinal();

    /**
     * Gets the signature of this method or constructor, as explained in {@link spoon.reflect.declaration.CtMethod#getSignature()}.
     */
    java.lang.String getSignature();

    @java.lang.Override
    spoon.reflect.reference.CtExecutableReference<T> clone();
}

