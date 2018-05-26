package spoon.reflect.declaration;


public interface CtPackageExport extends spoon.reflect.declaration.CtModuleDirective {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    <T extends spoon.reflect.declaration.CtPackageExport> T setOpenedPackage(boolean openedPackage);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    boolean isOpenedPackage();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    spoon.reflect.reference.CtPackageReference getPackageReference();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    <T extends spoon.reflect.declaration.CtPackageExport> T setPackageReference(spoon.reflect.reference.CtPackageReference packageReference);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODULE_REF)
    java.util.List<spoon.reflect.reference.CtModuleReference> getTargetExport();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_REF)
    <T extends spoon.reflect.declaration.CtPackageExport> T setTargetExport(java.util.List<spoon.reflect.reference.CtModuleReference> targetExport);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_REF)
    <T extends spoon.reflect.declaration.CtPackageExport> T addTargetExport(spoon.reflect.reference.CtModuleReference targetExport);

    @java.lang.Override
    spoon.reflect.declaration.CtPackageExport clone();
}

