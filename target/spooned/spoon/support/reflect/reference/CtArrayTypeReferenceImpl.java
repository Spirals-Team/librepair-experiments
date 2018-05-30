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
package spoon.support.reflect.reference;


public class CtArrayTypeReferenceImpl<T> extends spoon.support.reflect.reference.CtTypeReferenceImpl<T> implements spoon.reflect.reference.CtArrayTypeReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<?> componentType;

    public CtArrayTypeReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtArrayTypeReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getComponentType() {
        if ((componentType) == null) {
            // a sensible default component type to facilitate object creation and testing
            componentType = getFactory().Type().OBJECT;
        }
        return componentType;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getArrayType() {
        return getLastComponentTypeReference(componentType);
    }

    private spoon.reflect.reference.CtTypeReference<?> getLastComponentTypeReference(spoon.reflect.reference.CtTypeReference<?> component) {
        return component instanceof spoon.reflect.reference.CtArrayTypeReference ? getLastComponentTypeReference(((spoon.reflect.reference.CtArrayTypeReference) (component)).getComponentType()) : component;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtArrayTypeReference<T>> C setComponentType(spoon.reflect.reference.CtTypeReference<?> componentType) {
        if (componentType != null) {
            componentType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, componentType, this.componentType);
        this.componentType = componentType;
        return ((C) (this));
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return (getComponentType().getSimpleName()) + "[]";
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        return (getComponentType().getQualifiedName()) + "[]";
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public java.lang.Class<T> getActualClass() {
        java.lang.Class<?> c = getComponentType().getActualClass();
        if (c == null) {
            return null;
        }
        return ((java.lang.Class<T>) (java.lang.reflect.Array.newInstance(c, 0).getClass()));
    }

    @java.lang.Override
    public int getDimensionCount() {
        if ((getComponentType()) instanceof spoon.reflect.reference.CtArrayTypeReference) {
            return (((spoon.reflect.reference.CtArrayTypeReference<?>) (getComponentType())).getDimensionCount()) + 1;
        }
        return 1;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtArrayTypeReference<T> clone() {
        return ((spoon.reflect.reference.CtArrayTypeReference<T>) (super.clone()));
    }
}

