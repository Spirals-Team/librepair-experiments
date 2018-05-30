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
 * {@link spoon.reflect.declaration.CtField}.
 */
public interface CtFieldReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    /**
     * Gets the runtime member that corresponds to a field reference if any.
     *
     * @return the member (null if not found)
     */
    java.lang.reflect.Member getActualField();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtField<T> getDeclaration();

    /**
     * Returns the {@link CtField} that corresponds to the reference
     * even if its declaring type isn't in the Spoon source path  (in this case,
     * the Spoon elements are built with runtime reflection)
     *
     * @return the field declaration that corresponds to the reference.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtField<T> getFieldDeclaration();

    /**
     * Gets the type in which the field is declared.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getDeclaringType();

    /**
     * Gets the qualified name of the field.
     */
    java.lang.String getQualifiedName();

    /**
     * Tells if the referenced field is final.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_FINAL)
    boolean isFinal();

    /**
     * Tells if the referenced field is static.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean isStatic();

    /**
     * Sets the type in which the field is declared.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType);

    /**
     * Forces a reference to a final element.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_FINAL)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setFinal(boolean b);

    /**
     * Forces a reference to a static element.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setStatic(boolean b);

    @java.lang.Override
    spoon.reflect.reference.CtFieldReference<T> clone();
}

