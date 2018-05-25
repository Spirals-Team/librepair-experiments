package spoon.reflect.declaration;


public interface CtModule extends spoon.reflect.declaration.CtNamedElement {
    java.lang.String TOP_LEVEL_MODULE_NAME = "unnamed module";

    @spoon.support.DerivedProperty
    boolean isUnnamedModule();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    boolean isOpenModule();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModule> T setIsOpenModule(boolean openModule);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    <T extends spoon.reflect.declaration.CtModule> T setModuleDirectives(java.util.List<spoon.reflect.declaration.CtModuleDirective> moduleDirectives);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    <T extends spoon.reflect.declaration.CtModule> T addModuleDirective(spoon.reflect.declaration.CtModuleDirective moduleDirective);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    <T extends spoon.reflect.declaration.CtModule> T addModuleDirectiveAt(int position, spoon.reflect.declaration.CtModuleDirective moduleDirective);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    java.util.List<spoon.reflect.declaration.CtModuleDirective> getModuleDirectives();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    <T extends spoon.reflect.declaration.CtModule> T removeModuleDirective(spoon.reflect.declaration.CtModuleDirective moduleDirective);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    @spoon.support.DerivedProperty
    java.util.List<spoon.reflect.declaration.CtUsedService> getUsedServices();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T setUsedServices(java.util.List<spoon.reflect.declaration.CtUsedService> usedServices);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T addUsedService(spoon.reflect.declaration.CtUsedService usedService);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T removeUsedService(spoon.reflect.declaration.CtUsedService usedService);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPORTED_PACKAGE)
    @spoon.support.DerivedProperty
    java.util.List<spoon.reflect.declaration.CtPackageExport> getExportedPackages();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPORTED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T setExportedPackages(java.util.List<spoon.reflect.declaration.CtPackageExport> exportedPackages);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPORTED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T addExportedPackage(spoon.reflect.declaration.CtPackageExport exportedPackage);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPORTED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T removeExportedPackage(spoon.reflect.declaration.CtPackageExport exportedPackage);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    @spoon.support.DerivedProperty
    java.util.List<spoon.reflect.declaration.CtPackageExport> getOpenedPackages();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T setOpenedPackages(java.util.List<spoon.reflect.declaration.CtPackageExport> openedPackages);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T addOpenedPackage(spoon.reflect.declaration.CtPackageExport openedPackage);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T removeOpenedPackage(spoon.reflect.declaration.CtPackageExport openedPackage);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.REQUIRED_MODULE)
    @spoon.support.DerivedProperty
    java.util.List<spoon.reflect.declaration.CtModuleRequirement> getRequiredModules();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.REQUIRED_MODULE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T setRequiredModules(java.util.List<spoon.reflect.declaration.CtModuleRequirement> requiredModules);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.REQUIRED_MODULE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T addRequiredModule(spoon.reflect.declaration.CtModuleRequirement requiredModule);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.REQUIRED_MODULE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T removeRequiredModule(spoon.reflect.declaration.CtModuleRequirement requiredModule);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PROVIDED_SERVICE)
    @spoon.support.DerivedProperty
    java.util.List<spoon.reflect.declaration.CtProvidedService> getProvidedServices();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PROVIDED_SERVICE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T setProvidedServices(java.util.List<spoon.reflect.declaration.CtProvidedService> providedServices);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PROVIDED_SERVICE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T addProvidedService(spoon.reflect.declaration.CtProvidedService providedService);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PROVIDED_SERVICE)
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.declaration.CtModule> T removeProvidedService(spoon.reflect.declaration.CtProvidedService providedService);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    spoon.reflect.declaration.CtPackage getRootPackage();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    <T extends spoon.reflect.declaration.CtModule> T setRootPackage(spoon.reflect.declaration.CtPackage rootPackage);

    @spoon.support.DerivedProperty
    @java.lang.Override
    spoon.reflect.reference.CtModuleReference getReference();

    @java.lang.Override
    spoon.reflect.declaration.CtModule clone();
}

