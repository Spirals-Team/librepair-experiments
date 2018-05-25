package spoon.reflect.code;


public interface CtArrayAccess<T, E extends spoon.reflect.code.CtExpression<?>> extends spoon.reflect.code.CtTargetedExpression<T, E> {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtArrayAccess<T, E>> C setIndexExpression(spoon.reflect.code.CtExpression<java.lang.Integer> expression);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Integer> getIndexExpression();

    @java.lang.Override
    spoon.reflect.code.CtArrayAccess<T, E> clone();
}

