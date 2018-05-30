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
package spoon.reflect.visitor.filter;


/**
 * This filter matches all the {@link CtLambda} elements, which implements defined interface(s)
 */
public class LambdaFilter implements spoon.reflect.visitor.Filter<spoon.reflect.code.CtLambda<?>> {
    private java.util.Set<java.lang.String> qualifiedNamesOfInterfaces = new java.util.HashSet<>();

    /**
     * Use {@link #addImplementingInterface(CtTypeInformation)} to define set of interfaces whose lambdas it is search for
     */
    public LambdaFilter() {
    }

    /**
     * Matches all lambdas implementing `iface`
     * Use {@link #addImplementingInterface(CtTypeInformation)} to define set of interfaces whose lambdas it is search for
     */
    public LambdaFilter(spoon.reflect.declaration.CtInterface<?> iface) {
        addImplementingInterface(iface);
    }

    /**
     * Matches all lambdas implementing `iface`
     * Use {@link #addImplementingInterface(CtTypeInformation)} to define set of interfaces whose lambdas it is search for
     */
    public LambdaFilter(spoon.reflect.reference.CtTypeReference<?> iface) {
        addImplementingInterface(iface);
    }

    /**
     * Allows to search for lambdas implemented by different interfaces.
     *
     * @param typeInfo
     * 		interface whose lambda implementations it is searching for
     */
    public spoon.reflect.visitor.filter.LambdaFilter addImplementingInterface(spoon.reflect.declaration.CtTypeInformation typeInfo) {
        if (typeInfo instanceof spoon.reflect.declaration.CtType) {
            if (typeInfo instanceof spoon.reflect.declaration.CtInterface) {
                qualifiedNamesOfInterfaces.add(typeInfo.getQualifiedName());
            }// else ignore that request, because lambda can implement only interfaces

        }else {
            // do not check if it is interface or not. That check needs CtType in model and it might be not available in some modes
            // it is OK to search for non interface types. It simply founds no lambda implementing that
            qualifiedNamesOfInterfaces.add(typeInfo.getQualifiedName());
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtLambda<?> lambda) {
        return qualifiedNamesOfInterfaces.contains(lambda.getType().getQualifiedName());
    }
}

