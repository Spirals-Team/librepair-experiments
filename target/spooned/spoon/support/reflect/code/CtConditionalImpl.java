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


public class CtConditionalImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtConditional<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ELSE)
    spoon.reflect.code.CtExpression<T> elseExpression;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> condition;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.THEN)
    spoon.reflect.code.CtExpression<T> thenExpression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtConditional(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getElseExpression() {
        return elseExpression;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition() {
        return condition;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getThenExpression() {
        return thenExpression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setElseExpression(spoon.reflect.code.CtExpression<T> elseExpression) {
        if (elseExpression != null) {
            elseExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.ELSE, elseExpression, this.elseExpression);
        this.elseExpression = elseExpression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> condition) {
        if (condition != null) {
            condition.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.CONDITION, condition, this.condition);
        this.condition = condition;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setThenExpression(spoon.reflect.code.CtExpression<T> thenExpression) {
        if (thenExpression != null) {
            thenExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.THEN, thenExpression, this.thenExpression);
        this.thenExpression = thenExpression;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtConditional<T> clone() {
        return ((spoon.reflect.code.CtConditional<T>) (super.clone()));
    }
}

