package spoon.reflect.declaration;


/**
 * Represents the declaration of a used service in a {@link CtModule}
 *
 * The uses directive specifies a service for which the current module may discover providers via java.util.ServiceLoader.
 * The service must be a class type, an interface type, or an annotation type. It is a compile-time error if a uses directive specifies an enum type as the service.
 * The service may be declared in the current module or in another module. If the service is not declared in the current module, then the service must be accessible to code in the current module, or a compile-time error occurs.
 * It is a compile-time error if more than one uses directive in a module declaration specifies the same service.
 *
 * Example:
 *
 * <pre>
 *     uses java.logging.Logger;
 * </pre>
 */
public interface CtUsedService extends spoon.reflect.declaration.CtModuleDirective {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    spoon.reflect.reference.CtTypeReference getServiceType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    <T extends spoon.reflect.declaration.CtUsedService> T setServiceType(spoon.reflect.reference.CtTypeReference providingType);

    @java.lang.Override
    spoon.reflect.declaration.CtUsedService clone();
}

