package spoon.reflect.code;


public interface CtAssignment<T, A extends T> extends spoon.reflect.code.CtExpression<T> , spoon.reflect.code.CtRHSReceiver<A> , spoon.reflect.code.CtStatement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ASSIGNED)
    spoon.reflect.code.CtExpression<T> getAssigned();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ASSIGNED)
    <C extends spoon.reflect.code.CtAssignment<T, A>> C setAssigned(spoon.reflect.code.CtExpression<T> assigned);

    @java.lang.Override
    spoon.reflect.code.CtAssignment<T, A> clone();
}

