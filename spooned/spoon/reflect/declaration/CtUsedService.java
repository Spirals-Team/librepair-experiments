package spoon.reflect.declaration;


public interface CtUsedService extends spoon.reflect.declaration.CtModuleDirective {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    spoon.reflect.reference.CtTypeReference getServiceType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    <T extends spoon.reflect.declaration.CtUsedService> T setServiceType(spoon.reflect.reference.CtTypeReference providingType);

    @java.lang.Override
    spoon.reflect.declaration.CtUsedService clone();
}

