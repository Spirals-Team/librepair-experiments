package spoon.reflect.code;


public interface CtAbstractInvocation<T> extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    java.util.List<spoon.reflect.code.CtExpression<?>> getArguments();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C addArgument(spoon.reflect.code.CtExpression<?> argument);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    void removeArgument(spoon.reflect.code.CtExpression<?> argument);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ARGUMENT)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setArguments(java.util.List<spoon.reflect.code.CtExpression<?>> arguments);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> getExecutable();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    <C extends spoon.reflect.code.CtAbstractInvocation<T>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable);
}

