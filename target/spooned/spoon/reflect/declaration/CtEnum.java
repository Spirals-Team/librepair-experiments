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
 * This element represents an enumeration declaration.
 *
 * Example:
 * <pre>
 *    enum Boolean { TRUE, FALSE }
 * </pre>
 */
public interface CtEnum<T extends java.lang.Enum<?>> extends spoon.reflect.declaration.CtClass<T> {
    /**
     * Adds an enum value.
     *
     * @param enumValue
     * 		An enum value.
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <C extends spoon.reflect.declaration.CtEnum<T>> C addEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue);

    /**
     * Removes en enum value.
     *
     * @param enumValue
     * 		An enum value.
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    boolean removeEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue);

    /**
     * Gets an enum value by its name.
     *
     * @param name
     * 		Name of the enum value.
     * @return An enum value.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    spoon.reflect.declaration.CtEnumValue<?> getEnumValue(java.lang.String name);

    /**
     * Gets all enum values of the enumeration.
     *
     * @return All enum values.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    java.util.List<spoon.reflect.declaration.CtEnumValue<?>> getEnumValues();

    /**
     * Sets all enum values of the enum.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <C extends spoon.reflect.declaration.CtEnum<T>> C setEnumValues(java.util.List<spoon.reflect.declaration.CtEnumValue<?>> enumValues);

    @java.lang.Override
    spoon.reflect.declaration.CtEnum<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);
}

