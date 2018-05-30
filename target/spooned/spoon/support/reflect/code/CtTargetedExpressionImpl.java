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


public abstract class CtTargetedExpressionImpl<E, T extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtExpressionImpl<E> implements spoon.reflect.code.CtTargetedExpression<E, T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TARGET)
    T target;

    @java.lang.Override
    public T getTarget() {
        return target;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtTargetedExpression<E, T>> C setTarget(T target) {
        if (target != null) {
            target.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TARGET, target, this.target);
        this.target = target;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtTargetedExpression<E, T> clone() {
        return ((spoon.reflect.code.CtTargetedExpression<E, T>) (super.clone()));
    }
}

