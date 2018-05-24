package spoon.reflect.declaration;


public interface CtVariable<T> extends spoon.reflect.declaration.CtModifiable , spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtTypedElement<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    spoon.reflect.code.CtExpression<T> getDefaultExpression();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtVariableReference<T> getReference();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);
}

