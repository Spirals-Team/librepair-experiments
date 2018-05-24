package spoon.reflect.declaration;


public interface CtEnum<T extends java.lang.Enum<?>> extends spoon.reflect.declaration.CtClass<T> {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <C extends spoon.reflect.declaration.CtEnum<T>> C addEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    boolean removeEnumValue(spoon.reflect.declaration.CtEnumValue<?> enumValue);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    spoon.reflect.declaration.CtEnumValue<?> getEnumValue(java.lang.String name);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    java.util.List<spoon.reflect.declaration.CtEnumValue<?>> getEnumValues();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <C extends spoon.reflect.declaration.CtEnum<T>> C setEnumValues(java.util.List<spoon.reflect.declaration.CtEnumValue<?>> enumValues);

    @java.lang.Override
    spoon.reflect.declaration.CtEnum<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);
}

