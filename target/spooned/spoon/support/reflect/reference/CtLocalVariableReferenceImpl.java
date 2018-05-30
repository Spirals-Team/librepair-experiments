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


/**
 * An implementation for {@link CtLocalVariableReference}.
 */
public class CtLocalVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtLocalVariableReference<T> {
    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public CtLocalVariableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtLocalVariableReference(this);
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public spoon.reflect.code.CtLocalVariable<T> getDeclaration() {
        // without a factory, we are not able to filter for local variables
        final spoon.reflect.factory.Factory factory = getFactory();
        if (factory == null) {
            return null;
        }
        final java.lang.String simpleName = getSimpleName();
        // handle the CtLocalVariableReference which were created by CtLocalVariable#getReference() and which are not yet part of model, so we cannot found them using standard rules
        if ((parent) instanceof spoon.reflect.code.CtLocalVariable) {
            spoon.reflect.code.CtLocalVariable<T> var = ((spoon.reflect.code.CtLocalVariable<T>) (parent));
            if (simpleName.equals(var.getSimpleName())) {
                return var;
            }
        }
        try {
            // successively iterate through all parents of this reference and
            // return first result (which must be the closest declaration
            // respecting visible scope)
            spoon.reflect.declaration.CtVariable<?> var = map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(simpleName)).first();
            if (var instanceof spoon.reflect.code.CtLocalVariable) {
                return ((spoon.reflect.code.CtLocalVariable<T>) (var));
            }
            if (var != null) {
                // we have found another variable declaration with same simple name, which hides declaration of this local variable reference
                // handle it as not found
                return null;
            }
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            // handle this case as 'not found'
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtLocalVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtLocalVariableReference<T>) (super.clone()));
    }
}

