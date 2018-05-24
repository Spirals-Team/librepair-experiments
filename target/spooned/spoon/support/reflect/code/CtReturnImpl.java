package spoon.support.reflect.code;


public class CtReturnImpl<R> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtReturn<R> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<R> returnedExpression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtReturn(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<R> getReturnedExpression() {
        return returnedExpression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtReturn<R>> T setReturnedExpression(spoon.reflect.code.CtExpression<R> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.returnedExpression);
        this.returnedExpression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtReturn<R> clone() {
        return ((spoon.reflect.code.CtReturn<R>) (super.clone()));
    }

    @java.lang.Override
    public java.lang.Void S() {
        return null;
    }

    public spoon.reflect.code.CtCodeElement getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

