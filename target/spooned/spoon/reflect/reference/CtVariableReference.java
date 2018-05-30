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
 * {@link spoon.reflect.declaration.CtVariable} or sub-type.
 */
public interface CtVariableReference<T> extends spoon.reflect.reference.CtReference {
    /**
     * Gets the type of the variable.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> getType();

    /**
     * Sets the type of the variable.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE)
    <C extends spoon.reflect.reference.CtVariableReference<T>> C setType(spoon.reflect.reference.CtTypeReference<T> type);

    /**
     * Tries to get the declaration of the reference.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtVariable<T> getDeclaration();

    /**
     * Gets modifiers of the reference.
     */
    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    @java.lang.Override
    spoon.reflect.reference.CtVariableReference<T> clone();
}

