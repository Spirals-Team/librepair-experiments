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


public class CtParameterReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtParameterReference<T> {
    private static final long serialVersionUID = 1L;

    public CtParameterReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtParameterReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<?> getDeclaringExecutable() {
        spoon.reflect.declaration.CtParameter<T> declaration = getDeclaration();
        if (declaration == null) {
            return null;
        }
        return declaration.getParent().getReference();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtParameter<T> getDeclaration() {
        final spoon.reflect.declaration.CtParameter<T> ctParameter = lookupDynamically();
        if (ctParameter != null) {
            return ctParameter;
        }
        return null;
    }

    private spoon.reflect.declaration.CtParameter<T> lookupDynamically() {
        spoon.reflect.declaration.CtElement element = this;
        spoon.reflect.declaration.CtParameter optional = null;
        java.lang.String name = getSimpleName();
        try {
            do {
                spoon.reflect.declaration.CtExecutable executable = element.getParent(spoon.reflect.declaration.CtExecutable.class);
                if (executable == null) {
                    return null;
                }
                for (spoon.reflect.declaration.CtParameter parameter : ((java.util.List<spoon.reflect.declaration.CtParameter>) (executable.getParameters()))) {
                    if (name.equals(parameter.getSimpleName())) {
                        optional = parameter;
                    }
                }
                element = executable;
            } while (optional == null );
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
        return optional;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtParameterReference<T> clone() {
        return ((spoon.reflect.reference.CtParameterReference<T>) (super.clone()));
    }
}

