package spoon.reflect.code;


public interface CtAssert<T> extends spoon.reflect.code.CtStatement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getAssertExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <A extends spoon.reflect.code.CtAssert<T>> A setAssertExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> asserted);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> getExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <A extends spoon.reflect.code.CtAssert<T>> A setExpression(spoon.reflect.code.CtExpression<T> expression);

    @java.lang.Override
    spoon.reflect.code.CtAssert<T> clone();
}

