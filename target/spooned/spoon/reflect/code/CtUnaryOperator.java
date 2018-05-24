package spoon.reflect.code;


public interface CtUnaryOperator<T> extends spoon.reflect.code.CtExpression<T> , spoon.reflect.code.CtStatement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> getOperand();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtUnaryOperator> C setOperand(spoon.reflect.code.CtExpression<T> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    <C extends spoon.reflect.code.CtUnaryOperator> C setKind(spoon.reflect.code.UnaryOperatorKind kind);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.UnaryOperatorKind getKind();

    @java.lang.Override
    spoon.reflect.code.CtUnaryOperator<T> clone();
}

