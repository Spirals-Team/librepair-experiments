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
 * This interface defines a reference to an array.
 */
public interface CtArrayTypeReference<T> extends spoon.reflect.reference.CtTypeReference<T> {
    /**
     * Gets the type of the elements contained in this array.
     * e.g., if you have the array <code>int[][][]</code>,
     * this method returns a type reference for <code>int[][]</code>.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<?> getComponentType();

    /**
     * Gets the type of the array elements at the finest grain.
     * e.g., if you have the array <code>int[][][]</code>,
     * this method returns a type reference to "int".
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getArrayType();

    /**
     * Sets the type of the elements contained in this array.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE)
    <C extends spoon.reflect.reference.CtArrayTypeReference<T>> C setComponentType(spoon.reflect.reference.CtTypeReference<?> componentType);

    /**
     * Returns the number of dimensions of this array type. This corresponds to
     * the number of array types recursively embedded into the current one (see
     * {@link #getComponentType()}).
     */
    @spoon.support.DerivedProperty
    int getDimensionCount();

    /**
     * Returns the simple name of the array type core component type (with no
     * []s). Use toString() to get the full array type including []s.
     */
    java.lang.String getSimpleName();

    @java.lang.Override
    spoon.reflect.reference.CtArrayTypeReference<T> clone();
}

