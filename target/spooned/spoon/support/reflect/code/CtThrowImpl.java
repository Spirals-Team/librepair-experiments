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

