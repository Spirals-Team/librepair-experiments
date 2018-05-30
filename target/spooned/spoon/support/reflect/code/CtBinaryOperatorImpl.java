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


public class CtBinaryOperatorImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtBinaryOperator<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind kind;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.LEFT_OPERAND)
    spoon.reflect.code.CtExpression<?> leftHandOperand;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.RIGHT_OPERAND)
    spoon.reflect.code.CtExpression<?> rightHandOperand;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtBinaryOperator(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<?> getLeftHandOperand() {
        return leftHandOperand;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<?> getRightHandOperand() {
        return rightHandOperand;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtBinaryOperator<T>> C setLeftHandOperand(spoon.reflect.code.CtExpression<?> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.LEFT_OPERAND, expression, this.leftHandOperand);
        leftHandOperand = expression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtBinaryOperator<T>> C setRightHandOperand(spoon.reflect.code.CtExpression<?> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.RIGHT_OPERAND, expression, this.rightHandOperand);
        rightHandOperand = expression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtBinaryOperator<T>> C setKind(spoon.reflect.code.BinaryOperatorKind kind) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.OPERATOR_KIND, kind, this.kind);
        this.kind = kind;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.BinaryOperatorKind getKind() {
        return kind;
    }

    @java.lang.Override
    public spoon.reflect.code.CtBinaryOperator<T> clone() {
        return ((spoon.reflect.code.CtBinaryOperator<T>) (super.clone()));
    }
}

