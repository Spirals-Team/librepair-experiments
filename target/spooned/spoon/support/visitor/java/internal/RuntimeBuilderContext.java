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


public interface RuntimeBuilderContext {
    void addPackage(spoon.reflect.declaration.CtPackage ctPackage);

    void addType(spoon.reflect.declaration.CtType<?> aType);

    void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation);

    void addConstructor(spoon.reflect.declaration.CtConstructor<?> ctConstructor);

    void addMethod(spoon.reflect.declaration.CtMethod<?> ctMethod);

    void addField(spoon.reflect.declaration.CtField<?> ctField);

    void addEnumValue(spoon.reflect.declaration.CtEnumValue<?> ctEnumValue);

    void addParameter(spoon.reflect.declaration.CtParameter ctParameter);

    void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference);

    void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef);

    spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string);
}

