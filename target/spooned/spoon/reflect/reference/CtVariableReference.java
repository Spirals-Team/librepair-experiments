package spoon.reflect.reference;


public interface CtVariableReference<T> extends spoon.reflect.reference.CtReference {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> getType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE)
    <C extends spoon.reflect.reference.CtVariableReference<T>> C setType(spoon.reflect.reference.CtTypeReference<T> type);

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtVariable<T> getDeclaration();

    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    @java.lang.Override
    spoon.reflect.reference.CtVariableReference<T> clone();
}

