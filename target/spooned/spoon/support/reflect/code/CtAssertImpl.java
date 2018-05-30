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


public class CtAssertImpl<T> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtAssert<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> asserted;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> value;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtAssert(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getAssertExpression() {
        return asserted;
    }

    @java.lang.Override
    public <A extends spoon.reflect.code.CtAssert<T>> A setAssertExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> asserted) {
        if (asserted != null) {
            asserted.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.CONDITION, asserted, this.asserted);
        this.asserted = asserted;
        return ((A) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getExpression() {
        return value;
    }

    @java.lang.Override
    public <A extends spoon.reflect.code.CtAssert<T>> A setExpression(spoon.reflect.code.CtExpression<T> value) {
        if (value != null) {
            value.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, value, this.value);
        this.value = value;
        return ((A) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtAssert<T> clone() {
        return ((spoon.reflect.code.CtAssert<T>) (super.clone()));
    }
}

