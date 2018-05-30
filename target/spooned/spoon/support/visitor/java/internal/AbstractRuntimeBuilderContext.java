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
package spoon.support.visitor.java.internal;


abstract class AbstractRuntimeBuilderContext implements spoon.support.visitor.java.internal.RuntimeBuilderContext {
    protected AbstractRuntimeBuilderContext(spoon.reflect.declaration.CtShadowable element) {
        element.setShadow(true);
    }

    @java.lang.Override
    public void addPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addType(spoon.reflect.declaration.CtType<?> aType) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addConstructor(spoon.reflect.declaration.CtConstructor<?> ctConstructor) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addMethod(spoon.reflect.declaration.CtMethod<?> ctMethod) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addField(spoon.reflect.declaration.CtField<?> ctField) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addEnumValue(spoon.reflect.declaration.CtEnumValue<?> ctEnumValue) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addParameter(spoon.reflect.declaration.CtParameter ctParameter) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference) {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string) {
        return null;
    }
}

