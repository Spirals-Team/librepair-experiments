package spoon.pattern;


@spoon.support.Experimental
public interface Generator {
    spoon.reflect.factory.Factory getFactory();

    <T extends spoon.reflect.declaration.CtElement> java.util.List<T> generate(java.lang.Class<T> valueType, java.util.Map<java.lang.String, java.lang.Object> params);

    <T extends spoon.reflect.declaration.CtElement> java.util.List<T> generate(java.lang.Class<T> valueType, spoon.support.util.ImmutableMap params);

    <T extends spoon.reflect.declaration.CtTypeMember> java.util.List<T> addToType(java.lang.Class<T> valueType, java.util.Map<java.lang.String, java.lang.Object> params, spoon.reflect.declaration.CtType<?> targetType);

    <T extends spoon.reflect.declaration.CtType<?>> T generateType(java.lang.String typeQualifiedName, java.util.Map<java.lang.String, java.lang.Object> params);
}

