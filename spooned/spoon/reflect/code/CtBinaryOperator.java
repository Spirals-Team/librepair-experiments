package spoon.reflect.code;


public interface CtBinaryOperator<T> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.LEFT_OPERAND)
    spoon.reflect.code.CtExpression<?> getLeftHandOperand();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.RIGHT_OPERAND)
    spoon.reflect.code.CtExpression<?> getRightHandOperand();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.LEFT_OPERAND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setLeftHandOperand(spoon.reflect.code.CtExpression<?> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.RIGHT_OPERAND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setRightHandOperand(spoon.reflect.code.CtExpression<?> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    <C extends spoon.reflect.code.CtBinaryOperator<T>> C setKind(spoon.reflect.code.BinaryOperatorKind kind);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind getKind();

    @java.lang.Override
    spoon.reflect.code.CtBinaryOperator<T> clone();
}

