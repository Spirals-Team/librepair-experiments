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
package spoon.support.reflect.declaration;


public class CtEnumImpl<T extends java.lang.Enum<?>> extends spoon.support.reflect.declaration.CtClassImpl<T> implements spoon.reflect.declaration.CtEnum<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VALUE)
    private java.util.List<spoon.reflect.declaration.CtEnumValue<?>> enumValues = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VALUE)
    private spoon.reflect.declaration.CtMethod<T[]> valuesMethod;

    private spoon.reflect.declaration.CtMethod<T> valueOfMethod;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtEnum(this);
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtMethod<?>> getAllMethods() {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> allMethods = new spoon.support.util.SignatureBasedSortedSet();
        allMethods.addAll(getMethods());
        allMethods.addAll(getFactory().Type().get(java.lang.Enum.class).getMethods());
        allMethods.add(valuesMethod());
        allMethods.add(valueOfMethod());
        return allMethods;
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        return getReference().isSubtypeOf(type);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtEnum<T>> C addEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue) {
        if (enumValue == null) {
            return ((C) (this));
        }
        if ((enumValues) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtEnumValue<?>>emptyList())) {
            enumValues = new java.util.ArrayList<>();
        }
        if (!(enumValues.contains(enumValue))) {
            enumValue.setParent(this);
            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.VALUE, this.enumValues, enumValue);
            enumValues.add(enumValue);
        }
        // enum value already exists.
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue) {
        if ((enumValues) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtEnumValue<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.VALUE, enumValues, enumValues.indexOf(enumValue), enumValue);
        return enumValues.remove(enumValue);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtEnumValue<?> getEnumValue(java.lang.String name) {
        for (spoon.reflect.declaration.CtEnumValue<?> enumValue : enumValues) {
            if (enumValue.getSimpleName().equals(name)) {
                return enumValue;
            }
        }
        return null;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtEnumValue<?>> getEnumValues() {
        return java.util.Collections.unmodifiableList(enumValues);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtEnum<T>> C setEnumValues(java.util.List<spoon.reflect.declaration.CtEnumValue<?>> enumValues) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.VALUE, this.enumValues, new java.util.ArrayList<>(enumValues));
        if ((enumValues == null) || (enumValues.isEmpty())) {
            this.enumValues = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        this.enumValues.clear();
        for (spoon.reflect.declaration.CtEnumValue<?> enumValue : enumValues) {
            addEnumValue(enumValue);
        }
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtField<?>> getFields() {
        java.util.List<spoon.reflect.declaration.CtField<?>> result = new java.util.ArrayList<>();
        result.addAll(getEnumValues());
        result.addAll(super.getFields());
        return java.util.Collections.unmodifiableList(result);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtField<?> getField(java.lang.String name) {
        final spoon.reflect.declaration.CtField<?> field = super.getField(name);
        if (field == null) {
            return getEnumValue(name);
        }
        return field;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtEnum<T> clone() {
        return ((spoon.reflect.declaration.CtEnum<T>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        return getFactory().Type().createReference(java.lang.Enum.class);
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        return ((C) (this));
    }

    private spoon.reflect.declaration.CtMethod valuesMethod() {
        if ((valuesMethod) == null) {
            valuesMethod = getFactory().Core().createMethod();
            valuesMethod.setParent(this);
            valuesMethod.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
            valuesMethod.addModifier(spoon.reflect.declaration.ModifierKind.STATIC);
            valuesMethod.setSimpleName("values");
            valuesMethod.setImplicit(true);
            valuesMethod.setType(factory.Type().createArrayReference(getReference()));
        }
        return valuesMethod;
    }

    private spoon.reflect.declaration.CtMethod valueOfMethod() {
        if ((valueOfMethod) == null) {
            valueOfMethod = getFactory().Core().createMethod();
            valueOfMethod.setParent(this);
            valueOfMethod.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
            valueOfMethod.addModifier(spoon.reflect.declaration.ModifierKind.STATIC);
            valueOfMethod.setSimpleName("valueOf");
            valueOfMethod.setImplicit(true);
            valueOfMethod.addThrownType(getFactory().Type().createReference(java.lang.IllegalArgumentException.class));
            valueOfMethod.setType(getReference());
            factory.Method().createParameter(valuesMethod, factory.Type().STRING, "name");
        }
        return valueOfMethod;
    }

    @java.lang.Override
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        if (("values".equals(name)) && ((parameterTypes.length) == 0)) {
            return valuesMethod();
        }else
            if ((("valueOf".equals(name)) && ((parameterTypes.length) == 1)) && (parameterTypes[0].equals(factory.Type().STRING))) {
                return valueOfMethod();
            }else {
                return super.getMethod(name, parameterTypes);
            }

    }

    @java.lang.Override
    public <R> spoon.reflect.declaration.CtMethod<R> getMethod(spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        if ((("values".equals(name)) && ((parameterTypes.length) == 0)) && (returnType.equals(getReference()))) {
            return valuesMethod();
        }else
            if (((("valueOf".equals(name)) && ((parameterTypes.length) == 1)) && (parameterTypes[0].equals(factory.Type().STRING))) && (returnType.equals(factory.Type().createArrayReference(getReference())))) {
                return valueOfMethod();
            }else {
                return super.getMethod(returnType, name, parameterTypes);
            }

    }

    @java.lang.Override
    public boolean isClass() {
        return false;
    }

    @java.lang.Override
    public boolean isEnum() {
        return true;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters) {
        return ((C) (this));
    }
}

