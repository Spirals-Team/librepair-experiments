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


public class CtIfImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtIf {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> condition;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ELSE)
    spoon.reflect.code.CtStatement elseStatement;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.THEN)
    spoon.reflect.code.CtStatement thenStatement;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtIf(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition() {
        return condition;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <S extends spoon.reflect.code.CtStatement> S getElseStatement() {
        return ((S) (elseStatement));
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <S extends spoon.reflect.code.CtStatement> S getThenStatement() {
        return ((S) (thenStatement));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtIf> T setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> condition) {
        if (condition != null) {
            condition.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.CONDITION, condition, this.condition);
        this.condition = condition;
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtIf> T setElseStatement(spoon.reflect.code.CtStatement elseStatement) {
        if (elseStatement != null) {
            elseStatement.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.ELSE, elseStatement, this.elseStatement);
        this.elseStatement = elseStatement;
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtIf> T setThenStatement(spoon.reflect.code.CtStatement thenStatement) {
        // then branch might be null: `if (condition) ;`
        if (thenStatement != null) {
            thenStatement.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.THEN, thenStatement, this.thenStatement);
        this.thenStatement = thenStatement;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtIf clone() {
        return ((spoon.reflect.code.CtIf) (super.clone()));
    }

    @java.lang.Override
    public java.lang.Void S() {
        return null;
    }

    public spoon.reflect.code.CtCodeElement getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

