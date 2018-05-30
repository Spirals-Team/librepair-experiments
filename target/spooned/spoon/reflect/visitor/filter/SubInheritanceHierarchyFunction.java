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
 * Expects a {@link CtTypeInformation} as input
 * and produces all sub classes and sub interfaces recursively.<br>
 * The output is produced in arbitrary order.
 */
public class SubInheritanceHierarchyFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingInterfaces = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean failOnClassNotFound = false;

    /**
     * The mapping function created using this constructor
     * will visit each super class and super interface
     * following super hierarchy. It can happen
     * that some interfaces will be visited more then once
     * if they are in super inheritance hierarchy more then once.<br>
     * Use second constructor if you want to visit each interface only once.
     */
    public SubInheritanceHierarchyFunction() {
    }

    /**
     *
     *
     * @param includingSelf
     * 		if true then input element is sent to output too. By default it is false.
     */
    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    /**
     *
     *
     * @param includingInterfaces
     * 		if false then interfaces are not visited - only super classes. By default it is true.
     */
    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    /**
     *
     *
     * @param failOnClassNotFound
     * 		sets whether processing should throw an exception if class is missing in noClassPath mode
     */
    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.support.visitor.SubInheritanceHierarchyResolver fnc = new spoon.support.visitor.SubInheritanceHierarchyResolver(((spoon.reflect.declaration.CtElement) (input)).getFactory().getModel().getRootPackage()).failOnClassNotFound(failOnClassNotFound).includingInterfaces(includingInterfaces);
        if (includingSelf) {
            if (input instanceof spoon.reflect.reference.CtTypeReference) {
                outputConsumer.accept(((spoon.reflect.reference.CtTypeReference<?>) (input)).getTypeDeclaration());
            }else {
                outputConsumer.accept(((spoon.reflect.declaration.CtType<?>) (input)));
            }
        }
        fnc.addSuperType(input);
        fnc.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType typeInfo) {
                outputConsumer.accept(typeInfo);
                if (query.isTerminated()) {
                    // Cannot terminate, because it's support was removed.
                    // I think there are cases where it might be useful.
                    // fnc.terminate();
                }
            }
        });
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

