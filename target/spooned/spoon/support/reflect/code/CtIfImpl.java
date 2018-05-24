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

