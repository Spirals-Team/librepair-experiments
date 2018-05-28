package spoon.reflect.declaration;


public interface CtConstructor<T> extends spoon.reflect.declaration.CtExecutable<T> , spoon.reflect.declaration.CtFormalTypeDeclarer , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeMember {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String getSimpleName();

    @java.lang.Override
    spoon.reflect.declaration.CtConstructor<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type);
}

