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


public class VariableRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtVariable ctVariable;

    public VariableRuntimeBuilderContext(spoon.reflect.declaration.CtField<?> ctField) {
        super(ctField);
        this.ctVariable = ctField;
    }

    public VariableRuntimeBuilderContext(spoon.reflect.declaration.CtParameter<?> ctParameter) {
        super(ctParameter);
        this.ctVariable = ctParameter;
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        ctVariable.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference) {
        switch (role) {
            case TYPE :
                ctVariable.setType(ctTypeReference);
                return;
        }
        super.addTypeReference(role, ctTypeReference);
    }
}

