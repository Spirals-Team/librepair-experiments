package spoon.support.reflect.code;


public class CtConditionalImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtConditional<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ELSE)
    spoon.reflect.code.CtExpression<T> elseExpression;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> condition;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.THEN)
    spoon.reflect.code.CtExpression<T> thenExpression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtConditional(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getElseExpression() {
        return elseExpression;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition() {
        return condition;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getThenExpression() {
        return thenExpression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setElseExpression(spoon.reflect.code.CtExpression<T> elseExpression) {
        if (elseExpression != null) {
            elseExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.ELSE, elseExpression, this.elseExpression);
        this.elseExpression = elseExpression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> condition) {
        if (condition != null) {
            condition.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.CONDITION, condition, this.condition);
        this.condition = condition;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtConditional<T>> C setThenExpression(spoon.reflect.code.CtExpression<T> thenExpression) {
        if (thenExpression != null) {
            thenExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.THEN, thenExpression, this.thenExpression);
        this.thenExpression = thenExpression;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtConditional<T> clone() {
        return ((spoon.reflect.code.CtConditional<T>) (super.clone()));
    }
}

