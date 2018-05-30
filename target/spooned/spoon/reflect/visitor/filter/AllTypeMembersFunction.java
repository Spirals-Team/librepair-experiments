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
 * Expects {@link CtType} as input
 * and produces all {@link CtTypeMember}s declared in input class
 * or any super class or super interface
 */
public class AllTypeMembersFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private spoon.reflect.visitor.chain.CtQuery query;

    private final java.lang.Class<?> memberClass;

    private java.util.Set<java.lang.String> distinctSet;

    /**
     * returns all type members
     */
    public AllTypeMembersFunction() {
        this.memberClass = null;
    }

    /**
     * returns all type members which are instance of `memberClass`.<br>
     * Example:<br>
     * <code>
     * CtField allFields = ctType.map(new AllTypeMembersFunction(CtField.class)).list();
     * </code>
     */
    public AllTypeMembersFunction(java.lang.Class<?> memberClass) {
        this.memberClass = memberClass;
    }

    /**
     * The types whose qualified name is in distinctSet are not visited.
     * The qualified name of each type visited by this mapping function is added to `distinctSet`
     *
     * @param distinctSet
     * 		- Set of qualified names of types, which has to be ignored, because they were already processed
     */
    public spoon.reflect.visitor.filter.AllTypeMembersFunction distinctSet(java.util.Set<java.lang.String> distinctSet) {
        this.distinctSet = distinctSet;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.reflect.visitor.chain.CtQuery q = ((spoon.reflect.visitor.chain.CtQueryable) (input)).map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction(((distinctSet) == null ? new java.util.HashSet<java.lang.String>() : distinctSet)).includingSelf(true));
        q.forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType<?> type) {
                for (spoon.reflect.declaration.CtTypeMember typeMember : type.getTypeMembers()) {
                    if (((memberClass) == null) || (memberClass.isInstance(typeMember))) {
                        outputConsumer.accept(typeMember);
                    }
                    if (query.isTerminated()) {
                        q.terminate();
                    }
                }
            }
        });
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

