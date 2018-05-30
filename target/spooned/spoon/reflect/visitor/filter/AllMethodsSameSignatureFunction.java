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
 * Returns all methods/lambdas with same signature in related inheritance hierarchies.
 * It can be be used to found all other methods, which has to be changed if signature of method or lambda expression has to be changed.<br>
 *
 * Expects {@link CtExecutable} as input
 * and produces all {@link CtExecutable}s,
 * which have same signature and are declared in sub/super classes or sub/super interfaces of this or related inheritance hierarchy.<br>
 *
 * It makes sense to call this mapping functions for {@link CtMethod} and {@link CtLambda} instances
 * and then it returns {@link CtMethod} and {@link CtLambda} instance which overrides each other or have same signature.
 */
public class AllMethodsSameSignatureFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtExecutable<?>> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingLambdas = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    public AllMethodsSameSignatureFunction() {
    }

    /**
     *
     *
     * @param includingSelf
     * 		if true then input element is sent to output too. By default it is false.
     */
    public spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    /**
     *
     *
     * @param includingLambdas
     * 		if true then extra search for {@link CtLambda} executables,
     * 		with same signature will be processed too.
     * 		If false, then it returns only {@link CtMethod} instances.
     * 		By default it is true.
     */
    public spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction includingLambdas(boolean includingLambdas) {
        this.includingLambdas = includingLambdas;
        return this;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.declaration.CtExecutable<?> targetExecutable, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        // prepare filter for lambda expression. It will be configured by the algorithm below
        final spoon.reflect.visitor.filter.LambdaFilter lambdaFilter = new spoon.reflect.visitor.filter.LambdaFilter();
        final spoon.reflect.visitor.chain.CtQuery lambdaQuery = targetExecutable.getFactory().getModel().filterChildren(lambdaFilter);
        // the to be searched method
        spoon.reflect.declaration.CtMethod<?> targetMethod;
        // CtConstructor or CtAnonymousExecutable never overrides other executable. We are done
        if (targetExecutable instanceof spoon.reflect.code.CtLambda) {
            // the input is lambda
            if ((includingSelf) && (includingLambdas)) {
                outputConsumer.accept(targetExecutable);
                if (query.isTerminated()) {
                    return;
                }
            }
            // in case of lambda, the target method is the method implemented by lambda
            targetMethod = ((spoon.reflect.code.CtLambda<?>) (targetExecutable)).getOverriddenMethod();
            outputConsumer.accept(targetMethod);
            if (query.isTerminated()) {
                return;
            }
            // the input is the lambda expression, which was already returned or doesn't have to be returned at all because includingSelf == false
            // add extra filter into lambdaQuery which skips that input lambda expression
            lambdaQuery.select(new spoon.reflect.visitor.Filter<spoon.reflect.code.CtLambda<?>>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.code.CtLambda<?> lambda) {
                    return targetExecutable != lambda;
                }
            });
        }// CtConstructor or CtAnonymousExecutable never overrides other executable. We are done
        else
            if (targetExecutable instanceof spoon.reflect.declaration.CtMethod) {
                if (includingSelf) {
                    outputConsumer.accept(targetExecutable);
                    if (query.isTerminated()) {
                        return;
                    }
                }
                targetMethod = ((spoon.reflect.declaration.CtMethod<?>) (targetExecutable));
            }else {
                if (includingSelf) {
                    outputConsumer.accept(targetExecutable);
                }
                return;
            }

        final java.util.List<spoon.reflect.declaration.CtMethod<?>> targetMethods = new java.util.ArrayList<>();
        targetMethods.add(targetMethod);
        spoon.reflect.declaration.CtType<?> declaringType = targetMethod.getDeclaringType();
        lambdaFilter.addImplementingInterface(declaringType);
        // search for all declarations and implementations of this method in sub and super classes and interfaces of all related hierarchies.
        class Context {
            boolean haveToSearchForSubtypes;
        }
        final Context context = new Context();
        // at the beginning we know that we have to always search for sub types too.
        context.haveToSearchForSubtypes = true;
        // Sub inheritance hierarchy function, which remembers visited sub types and does not returns/visits them again
        final spoon.support.visitor.SubInheritanceHierarchyResolver subHierarchyFnc = new spoon.support.visitor.SubInheritanceHierarchyResolver(declaringType.getFactory().getModel().getRootPackage());
        // add hierarchy of `targetMethod` as to be checked for sub types of declaring type
        subHierarchyFnc.addSuperType(declaringType);
        // unique names of all types whose super inheritance hierarchy was searched for rootType
        java.util.Set<java.lang.String> typesCheckedForRootType = new java.util.HashSet<>();
        // list of sub types whose inheritance hierarchy has to be checked
        final java.util.List<spoon.reflect.declaration.CtType<?>> toBeCheckedSubTypes = new java.util.ArrayList<>();
        // add hierarchy of `targetMethod` as to be checked for super types of declaring type
        toBeCheckedSubTypes.add(declaringType);
        while ((toBeCheckedSubTypes.size()) > 0) {
            for (spoon.reflect.declaration.CtType<?> subType : toBeCheckedSubTypes) {
                spoon.support.visitor.ClassTypingContext ctc = new spoon.support.visitor.ClassTypingContext(subType);
                // search for first target method from the same type inheritance hierarchy
                targetMethod = getTargetMethodOfHierarchy(targetMethods, ctc);
                // search for all methods with same signature in inheritance hierarchy of `subType`
                forEachOverridenMethod(ctc, targetMethod, typesCheckedForRootType, new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>>() {
                    @java.lang.Override
                    public void accept(spoon.reflect.declaration.CtMethod<?> overriddenMethod) {
                        targetMethods.add(overriddenMethod);
                        outputConsumer.accept(overriddenMethod);
                        spoon.reflect.declaration.CtType<?> type = overriddenMethod.getDeclaringType();
                        lambdaFilter.addImplementingInterface(type);
                        subHierarchyFnc.addSuperType(type);
                        // mark that new super type was added, so we have to search for sub types again
                        context.haveToSearchForSubtypes = true;
                    }
                });
                if (query.isTerminated()) {
                    return;
                }
            }
            toBeCheckedSubTypes.clear();
            if (context.haveToSearchForSubtypes) {
                context.haveToSearchForSubtypes = false;
                // there are some new super types, whose sub inheritance hierarchy has to be checked
                // search their inheritance hierarchy for sub types
                subHierarchyFnc.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
                    @java.lang.Override
                    public void accept(spoon.reflect.declaration.CtType<?> type) {
                        toBeCheckedSubTypes.add(type);
                    }
                });
            }
        } 
        if (includingLambdas) {
            // search for all lambdas implementing any of the found interfaces
            lambdaQuery.forEach(outputConsumer);
        }
    }

    /**
     * calls outputConsumer for each method which is overridden by 'thisMethod' in scope of `ctc`.
     * There is assured that each method is returned only once.
     *
     * @param ctc
     * 		- class typing context whose scope is searched for overridden methods
     * @param thisMethod
     * 		- the
     * @param distintTypesSet
     * 		set of qualified names of types which were already visited
     * @param outputConsumer
     * 		result handling consumer
     */
    private void forEachOverridenMethod(final spoon.support.visitor.ClassTypingContext ctc, final spoon.reflect.declaration.CtMethod<?> thisMethod, java.util.Set<java.lang.String> distintTypesSet, final spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>> outputConsumer) {
        final spoon.reflect.visitor.chain.CtQuery q = ctc.getAdaptationScope().map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtMethod.class).distinctSet(distintTypesSet));
        q.forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtMethod<?> thatMethod) {
                if (thisMethod == thatMethod) {
                    // do not return scope method
                    return;
                }
                // check whether method is overridden by searched method
                /* note: we are in super inheritance hierarchy of type declaring input `method`, so we do not have to check isSubTypeOf.
                Check for isSubSignature is enough
                 */
                if (ctc.isSubSignature(thisMethod, thatMethod)) {
                    outputConsumer.accept(thatMethod);
                    if (query.isTerminated()) {
                        q.terminate();
                    }
                }
            }
        });
    }

    private spoon.reflect.declaration.CtMethod<?> getTargetMethodOfHierarchy(java.util.List<spoon.reflect.declaration.CtMethod<?>> targetMethods, spoon.support.visitor.ClassTypingContext ctc) {
        for (spoon.reflect.declaration.CtMethod<?> method : targetMethods) {
            spoon.reflect.declaration.CtType<?> declaringType = method.getDeclaringType();
            if (ctc.isSubtypeOf(declaringType.getReference())) {
                return method;
            }
        }
        // this should never happen
        throw new spoon.SpoonException("No target executable was found in super type hiearchy of class typing context");
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

