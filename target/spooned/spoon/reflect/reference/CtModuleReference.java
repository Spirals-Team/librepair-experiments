package spoon.reflect.reference;


public interface CtModuleReference extends spoon.reflect.reference.CtReference {
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtModule getDeclaration();

    @java.lang.Override
    spoon.reflect.reference.CtModuleReference clone();
}

