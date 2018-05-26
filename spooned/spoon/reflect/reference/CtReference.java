package spoon.reflect.reference;


public interface CtReference extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String getSimpleName();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NAME)
    <T extends spoon.reflect.reference.CtReference> T setSimpleName(java.lang.String simpleName);

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtElement getDeclaration();

    @java.lang.Override
    spoon.reflect.reference.CtReference clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <E extends spoon.reflect.declaration.CtElement> E setComments(java.util.List<spoon.reflect.code.CtComment> comments);
}

