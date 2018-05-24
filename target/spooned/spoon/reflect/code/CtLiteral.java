package spoon.reflect.code;


public interface CtLiteral<T> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    T getValue();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <C extends spoon.reflect.code.CtLiteral<T>> C setValue(T value);

    @java.lang.Override
    spoon.reflect.code.CtLiteral<T> clone();
}

