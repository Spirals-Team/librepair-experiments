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
 * This interface defines a reference to an intersection type in generics or in casts.
 */
public interface CtIntersectionTypeReference<T> extends spoon.reflect.reference.CtTypeReference<T> {
    /**
     * Gets the bounds of the intersection type. Note that the first bound correspond to the current intersection type.
     * <pre>
     *     T extends Interface1 &amp; Interface2 // CtTypeParameterReference#getBoundingType == Interface1 and getBounds().get(0) == Interface1
     * </pre>
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BOUND)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getBounds();

    /**
     * Sets the bounds of the intersection type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    <C extends spoon.reflect.reference.CtIntersectionTypeReference> C setBounds(java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds);

    /**
     * Adds a bound.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    <C extends spoon.reflect.reference.CtIntersectionTypeReference> C addBound(spoon.reflect.reference.CtTypeReference<?> bound);

    /**
     * Removes a bound.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    boolean removeBound(spoon.reflect.reference.CtTypeReference<?> bound);

    @java.lang.Override
    spoon.reflect.reference.CtIntersectionTypeReference<T> clone();
}

