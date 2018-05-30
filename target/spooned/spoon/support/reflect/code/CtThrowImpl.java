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


public class CtThrowImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtThrow {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<? extends java.lang.Throwable> throwExpression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtThrow(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<? extends java.lang.Throwable> getThrownExpression() {
        return throwExpression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtThrow> T setThrownExpression(spoon.reflect.code.CtExpression<? extends java.lang.Throwable> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.throwExpression);
        this.throwExpression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtThrow clone() {
        return ((spoon.reflect.code.CtThrow) (super.clone()));
    }

    @java.lang.Override
    public java.lang.Void S() {
        return null;
    }

    public spoon.reflect.code.CtCodeElement getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

