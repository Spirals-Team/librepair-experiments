package spoon.reflect.code;


public interface CtTypeAccess<A> extends spoon.reflect.code.CtExpression<java.lang.Void> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    spoon.reflect.reference.CtTypeReference<A> getAccessedType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    <C extends spoon.reflect.code.CtTypeAccess<A>> C setAccessedType(spoon.reflect.reference.CtTypeReference<A> accessedType);

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<java.lang.Void> getType();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type);

    @java.lang.Override
    spoon.reflect.code.CtTypeAccess<A> clone();
}

