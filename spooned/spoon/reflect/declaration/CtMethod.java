package spoon.reflect.declaration;


public interface CtMethod<T> extends spoon.reflect.declaration.CtExecutable<T> , spoon.reflect.declaration.CtFormalTypeDeclarer , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeMember {
    boolean isOverriding(spoon.reflect.declaration.CtMethod<?> superMethod);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_DEFAULT)
    boolean isDefaultMethod();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_DEFAULT)
    <C extends spoon.reflect.declaration.CtMethod<T>> C setDefaultMethod(boolean defaultMethod);

    @java.lang.Override
    spoon.reflect.declaration.CtMethod<T> clone();

    java.util.Collection<spoon.reflect.declaration.CtMethod<?>> getTopDefinitions();

    spoon.reflect.declaration.CtMethod<?> copyMethod();
}

