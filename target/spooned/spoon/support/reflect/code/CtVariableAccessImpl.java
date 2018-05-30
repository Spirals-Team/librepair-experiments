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
package spoon.support.reflect.code;


public abstract class CtVariableAccessImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtVariableAccess<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VARIABLE)
    spoon.reflect.reference.CtVariableReference<T> variable;

    @java.lang.Override
    public spoon.reflect.reference.CtVariableReference<T> getVariable() {
        if (((variable) == null) && ((getFactory()) != null)) {
            variable = getFactory().Core().createLocalVariableReference();
            variable.setParent(this);
        }
        return ((spoon.reflect.reference.CtVariableReference<T>) (variable));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtVariableAccess<T>> C setVariable(spoon.reflect.reference.CtVariableReference<T> variable) {
        if (variable != null) {
            variable.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.VARIABLE, variable, this.variable);
        this.variable = variable;
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return getVariable().getType();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        if (type != null) {
            getVariable().setType(type);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtVariableAccess<T> clone() {
        return ((spoon.reflect.code.CtVariableAccess<T>) (super.clone()));
    }
}

