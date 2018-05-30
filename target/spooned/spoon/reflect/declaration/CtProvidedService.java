/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.declaration;


/**
 * Represents a provided service in a {@link CtModule}.
 * The provides directive specifies a service for which the with clause specifies one or more service providers to java.util.ServiceLoader.
 * The service must be a class type, an interface type, or an annotation type. It is a compile-time error if a provides directive specifies an enum type as the service.
 *
 * The service may be declared in the current module or in another module. If the service is not declared in the current module, then the service must be accessible to code in the current module, or a compile-time error occurs.
 * Every service provider must be a class type or an interface type, that is public, and that is top level or nested static, or a compile-time error occurs.
 * Every service provider must be declared in the current module, or a compile-time error occurs.
 *
 * It is a compile-time error if more than one provides directive in a module declaration specifies the same service.
 * It is a compile-time error if the with clause of a given provides directive specifies the same service provider more than once.
 *
 * Example:
 * <pre>
 *     provides com.example.foo.spi.Itf with com.example.foo.Impl;
 * </pre>
 */
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

