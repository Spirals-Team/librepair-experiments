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


public class CtCatchVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtCatchVariableReference<T> {
    private static final long serialVersionUID = 1L;

    public CtCatchVariableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCatchVariableReference(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatchVariable<T> getDeclaration() {
        spoon.reflect.declaration.CtElement element = this;
        java.lang.String name = getSimpleName();
        spoon.reflect.code.CtCatchVariable var;
        try {
            do {
                spoon.reflect.code.CtCatch catchBlock = element.getParent(spoon.reflect.code.CtCatch.class);
                if (catchBlock == null) {
                    return null;
                }
                var = catchBlock.getParameter();
                element = catchBlock;
            } while (!(name.equals(var.getSimpleName())) );
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
        return var;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtCatchVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtCatchVariableReference<T>) (super.clone()));
    }
}

