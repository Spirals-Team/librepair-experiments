package spoon.reflect.reference;


public interface CtPackageReference extends spoon.reflect.reference.CtReference {
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtPackage getDeclaration();

    java.lang.Package getActualPackage();

    boolean isUnnamedPackage();

    @java.lang.Override
    spoon.reflect.reference.CtPackageReference clone();

    @java.lang.Override
    java.lang.String getSimpleName();

    java.lang.String getQualifiedName();
}

