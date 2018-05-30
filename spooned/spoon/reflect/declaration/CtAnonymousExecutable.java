package spoon.reflect.declaration;


public interface CtAnonymousExecutable extends spoon.reflect.declaration.CtExecutable<java.lang.Void> , spoon.reflect.declaration.CtTypeMember {
    @java.lang.Override
    spoon.reflect.declaration.CtAnonymousExecutable clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T addParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);
}

