package spoon.support.reflect.code;


public class CtWhileImpl extends spoon.support.reflect.code.CtLoopImpl implements spoon.reflect.code.CtWhile {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> expression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtWhile(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getLoopingExpression() {
        return expression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtWhile> T setLoopingExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtWhile clone() {
        return ((spoon.reflect.code.CtWhile) (super.clone()));
    }
}

