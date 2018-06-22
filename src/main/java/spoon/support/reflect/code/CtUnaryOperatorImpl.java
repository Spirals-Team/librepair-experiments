/**
 * Copyright (C) 2006-2018 INRIA and contributors
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

import spoon.reflect.annotations.MetamodelPropertyField;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.CtVisitor;

import static spoon.reflect.path.CtRole.EXPRESSION;
import static spoon.reflect.path.CtRole.LABEL;
import static spoon.reflect.path.CtRole.OPERATOR_KIND;

public class CtUnaryOperatorImpl<T> extends CtExpressionImpl<T> implements CtUnaryOperator<T> {
	private static final long serialVersionUID = 1L;

	@MetamodelPropertyField(role = CtRole.OPERATOR_KIND)
	UnaryOperatorKind kind;

	@MetamodelPropertyField(role = CtRole.LABEL)
	String label;

	@MetamodelPropertyField(role = CtRole.EXPRESSION)
	CtExpression<T> operand;

	@Override
	public void accept(CtVisitor visitor) {
		visitor.visitCtUnaryOperator(this);
	}

	@Override
	public CtExpression<T> getOperand() {
		return operand;
	}

	@Override
	public UnaryOperatorKind getKind() {
		return kind;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public <C extends CtStatement> C insertAfter(CtStatement statement) {
		CtStatementImpl.insertAfter(this, statement);
		return (C) this;
	}

	@Override
	public <C extends CtStatement> C insertBefore(CtStatement statement) {
		CtStatementImpl.insertBefore(this, statement);
		return (C) this;
	}

	@Override
	public <C extends CtStatement> C insertAfter(CtStatementList statements) {
		CtStatementImpl.insertAfter(this, statements);
		return (C) this;
	}

	@Override
	public <C extends CtStatement> C insertBefore(CtStatementList statements) {
		CtStatementImpl.insertBefore(this, statements);
		return (C) this;
	}

	@Override
	public <C extends CtUnaryOperator> C setOperand(CtExpression<T> expression) {
		if (expression != null) {
			expression.setParent(this);
		}
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, EXPRESSION, expression, this.operand);
		this.operand = expression;
		return (C) this;
	}

	@Override
	public <C extends CtUnaryOperator> C setKind(UnaryOperatorKind kind) {
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, OPERATOR_KIND, kind, this.kind);
		this.kind = kind;
		return (C) this;
	}

	@Override
	public <C extends CtStatement> C setLabel(String label) {
		getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, LABEL, label, this.label);
		this.label = label;
		return (C) this;
	}

	@Override
	public CtUnaryOperator<T> clone() {
		return (CtUnaryOperator<T>) super.clone();
	}
}
