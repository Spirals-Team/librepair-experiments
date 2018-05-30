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


public abstract class CtArrayAccessImpl<T, V extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, V> implements spoon.reflect.code.CtArrayAccess<T, V> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    private spoon.reflect.code.CtExpression<java.lang.Integer> expression;

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Integer> getIndexExpression() {
        return expression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtArrayAccess<T, V>> C setIndexExpression(spoon.reflect.code.CtExpression<java.lang.Integer> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtArrayAccess<T, V> clone() {
        return ((spoon.reflect.code.CtArrayAccess<T, V>) (super.clone()));
    }
}

