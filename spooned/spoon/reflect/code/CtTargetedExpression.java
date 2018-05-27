package spoon.reflect.code;


public interface CtTargetedExpression<T, E extends spoon.reflect.code.CtExpression<?>> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TARGET)
    E getTarget();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TARGET)
    <C extends spoon.reflect.code.CtTargetedExpression<T, E>> C setTarget(E target);

    @java.lang.Override
    spoon.reflect.code.CtTargetedExpression<T, E> clone();
}

