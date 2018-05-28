package spoon.support.reflect.code;


public abstract class CtArrayAccessImpl<T, V extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, V> implements spoon.reflect.code.CtArrayAccess<T, V> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    private spoon.reflect.code.CtExpression<java.lang.Integer> expression;

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Integer> getIndexExpression() {
        return expression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtArrayAccess<T, V>> C setIndexExpression(spoon.reflect.code.CtExpression<java.lang.Integer> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtArrayAccess<T, V> clone() {
        return ((spoon.reflect.code.CtArrayAccess<T, V>) (super.clone()));
    }
}

