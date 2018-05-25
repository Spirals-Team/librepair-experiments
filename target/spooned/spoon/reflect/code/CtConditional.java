package spoon.reflect.code;


public interface CtConditional<T> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ELSE)
    spoon.reflect.code.CtExpression<T> getElseExpression();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THEN)
    spoon.reflect.code.CtExpression<T> getThenExpression();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ELSE)
    <C extends spoon.reflect.code.CtConditional<T>> C setElseExpression(spoon.reflect.code.CtExpression<T> elseExpression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THEN)
    <C extends spoon.reflect.code.CtConditional<T>> C setThenExpression(spoon.reflect.code.CtExpression<T> thenExpression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <C extends spoon.reflect.code.CtConditional<T>> C setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> condition);

    @java.lang.Override
    spoon.reflect.code.CtConditional<T> clone();
}

