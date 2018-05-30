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
 * This interface defines an element that accepts modifiers.
 */
public interface CtModifiable extends spoon.reflect.declaration.CtElement {
    /**
     * Returns the modifiers of this element, excluding annotations. Implicit
     * modifiers, such as the {@code public} and {@code static} modifiers of
     * interface members, are included.
     *
     * @return the modifiers of this declaration in undefined order; an empty
     * set if there are none
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    /**
     * Tells if this element contains the given modifier.
     *
     * @param modifier
     * 		to search
     * @return {@code true} if this element contain the modifier
     */
    boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier);

    /**
     * Sets the modifiers.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers);

    /**
     * add a modifier
     *
     * @param modifier
     * 		
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T addModifier(spoon.reflect.declaration.ModifierKind modifier);

    /**
     * remove a modifier
     *
     * @param modifier
     * 		
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T removeModifier(spoon.reflect.declaration.ModifierKind modifier);

    /**
     * Sets the visibility of this modifiable element (replaces old visibility).
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T setVisibility(spoon.reflect.declaration.ModifierKind visibility);

    /**
     * Gets the visibility of this modifiable element.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.ModifierKind getVisibility();

    java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers();

    <T extends spoon.reflect.declaration.CtModifiable> T setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers);

    /**
     * Returns true if it contains a public modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isPublic();

    /**
     * Returns true if it contains a final modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isFinal();

    /**
     * Returns true if it contains a static modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isStatic();

    /**
     * Returns true if it contains a protected modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isProtected();

    /**
     * Returns true if it contains a private modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isPrivate();

    /**
     * Returns true if it contains an abstract modifier (see {@link #hasModifier(ModifierKind)})
     */
    @spoon.support.DerivedProperty
    boolean isAbstract();
}

