package spoon.reflect.declaration;


public interface CtImport extends spoon.reflect.declaration.CtElement {
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtImportKind getImportKind();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IMPORT_REFERENCE)
    spoon.reflect.reference.CtReference getReference();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IMPORT_REFERENCE)
    <T extends spoon.reflect.declaration.CtImport> T setReference(spoon.reflect.reference.CtReference reference);

    @java.lang.Override
    spoon.reflect.declaration.CtImport clone();
}

