package spoon.reflect.code;


public interface CtExpression<T> extends spoon.reflect.code.CtCodeElement , spoon.reflect.declaration.CtTypedElement<T> , spoon.template.TemplateParameter<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CAST)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTypeCasts();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CAST)
    <C extends spoon.reflect.code.CtExpression<T>> C setTypeCasts(java.util.List<spoon.reflect.reference.CtTypeReference<?>> types);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CAST)
    <C extends spoon.reflect.code.CtExpression<T>> C addTypeCast(spoon.reflect.reference.CtTypeReference<?> type);

    @java.lang.Override
    spoon.reflect.code.CtExpression<T> clone();
}

