package spoon.reflect.declaration;


public interface CtProvidedService extends spoon.reflect.declaration.CtModuleDirective {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    spoon.reflect.reference.CtTypeReference getServiceType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    <T extends spoon.reflect.declaration.CtProvidedService> T setServiceType(spoon.reflect.reference.CtTypeReference providingType);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE)
    java.util.List<spoon.reflect.reference.CtTypeReference> getImplementationTypes();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE)
    <T extends spoon.reflect.declaration.CtProvidedService> T setImplementationTypes(java.util.List<spoon.reflect.reference.CtTypeReference> usedTypes);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE)
    <T extends spoon.reflect.declaration.CtProvidedService> T addImplementationType(spoon.reflect.reference.CtTypeReference usedType);

    @java.lang.Override
    spoon.reflect.declaration.CtProvidedService clone();
}

