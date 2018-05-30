package spoon.reflect.declaration;


public interface CtPackage extends spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtShadowable {
    java.lang.String PACKAGE_SEPARATOR = ".";

    char PACKAGE_SEPARATOR_CHAR = '.';

    java.lang.String TOP_LEVEL_PACKAGE_NAME = "unnamed package";

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtModule getDeclaringModule();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtPackage getDeclaringPackage();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    spoon.reflect.declaration.CtPackage getPackage(java.lang.String name);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    java.util.Set<spoon.reflect.declaration.CtPackage> getPackages();

    java.lang.String getQualifiedName();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtPackageReference getReference();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtType<?>> T getType(java.lang.String simpleName);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    java.util.Set<spoon.reflect.declaration.CtType<?>> getTypes();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtPackage> T addType(spoon.reflect.declaration.CtType<?> type);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    void removeType(spoon.reflect.declaration.CtType<?> type);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    <T extends spoon.reflect.declaration.CtPackage> T setPackages(java.util.Set<spoon.reflect.declaration.CtPackage> pack);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    <T extends spoon.reflect.declaration.CtPackage> T addPackage(spoon.reflect.declaration.CtPackage pack);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    boolean removePackage(spoon.reflect.declaration.CtPackage pack);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtPackage> T setTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> types);

    @java.lang.Override
    spoon.reflect.declaration.CtPackage clone();

    boolean isUnnamedPackage();
}

