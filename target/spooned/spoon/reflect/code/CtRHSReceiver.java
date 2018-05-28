package spoon.reflect.code;


public interface CtRHSReceiver<A> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ASSIGNMENT)
    spoon.reflect.code.CtExpression<A> getAssignment();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ASSIGNMENT)
    <T extends spoon.reflect.code.CtRHSReceiver<A>> T setAssignment(spoon.reflect.code.CtExpression<A> assignment);
}

