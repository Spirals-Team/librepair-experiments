package spoon.reflect.declaration;


public interface CtNamedElement extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String getSimpleName();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NAME)
    <T extends spoon.reflect.declaration.CtNamedElement> T setSimpleName(java.lang.String simpleName);

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtReference getReference();

    @java.lang.Override
    spoon.reflect.declaration.CtNamedElement clone();
}

