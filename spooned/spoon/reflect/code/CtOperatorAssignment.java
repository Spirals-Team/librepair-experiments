package spoon.reflect.code;


public interface CtOperatorAssignment<T, A extends T> extends spoon.reflect.code.CtAssignment<T, A> {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    <C extends spoon.reflect.code.CtOperatorAssignment<T, A>> C setKind(spoon.reflect.code.BinaryOperatorKind kind);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind getKind();

    @java.lang.Override
    spoon.reflect.code.CtOperatorAssignment<T, A> clone();
}

