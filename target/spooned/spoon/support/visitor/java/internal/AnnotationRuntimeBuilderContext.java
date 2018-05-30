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


public class AnnotationRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation;

    public AnnotationRuntimeBuilderContext(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        super(ctAnnotation);
        this.ctAnnotation = ctAnnotation;
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        this.ctAnnotation.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> typeReference) {
        switch (role) {
            case ANNOTATION_TYPE :
                ctAnnotation.setAnnotationType(((spoon.reflect.reference.CtTypeReference<? extends java.lang.annotation.Annotation>) (typeReference)));
                ctAnnotation.setType(((spoon.reflect.reference.CtTypeReference<java.lang.annotation.Annotation>) (typeReference)));
                return;
        }
        super.addTypeReference(role, typeReference);
    }

    public spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> getCtAnnotation() {
        return this.ctAnnotation;
    }
}

