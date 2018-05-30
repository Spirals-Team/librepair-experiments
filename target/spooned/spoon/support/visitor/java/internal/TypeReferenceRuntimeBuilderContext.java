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


public class TypeReferenceRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.reference.CtTypeReference<?> typeReference;

    private java.lang.reflect.Type type;

    private java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeParameter> mapTypeParameters;

    public TypeReferenceRuntimeBuilderContext(java.lang.reflect.Type type, spoon.reflect.reference.CtTypeReference<?> typeReference) {
        super(typeReference);
        this.typeReference = typeReference;
        this.type = type;
        this.mapTypeParameters = new java.util.HashMap<>();
    }

    @java.lang.Override
    public void addPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        typeReference.setPackage(ctPackage.getReference());
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference) {
        switch (role) {
            case DECLARING_TYPE :
                this.typeReference.setDeclaringType(ctTypeReference);
                return;
            case BOUNDING_TYPE :
            case SUPER_TYPE :
                ((spoon.reflect.reference.CtTypeParameterReference) (typeReference)).addBound(ctTypeReference);
                return;
            case TYPE_ARGUMENT :
                typeReference.addActualTypeArgument(ctTypeReference);
                return;
        }
        super.addTypeReference(role, ctTypeReference);
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        typeReference.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef) {
        typeReference.addActualTypeArgument(parameterRef.getReference());
        this.mapTypeParameters.put(parameterRef.getSimpleName(), parameterRef);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string) {
        return (type) == genericDeclaration ? this.mapTypeParameters.get(string) : null;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        if ((o == null) || ((getClass()) != (o.getClass()))) {
            return false;
        }
        final spoon.support.visitor.java.internal.TypeReferenceRuntimeBuilderContext that = ((spoon.support.visitor.java.internal.TypeReferenceRuntimeBuilderContext) (o));
        return java.util.Objects.equals(typeReference, that.typeReference);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(typeReference);
    }
}

