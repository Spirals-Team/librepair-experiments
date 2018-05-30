package spoon.reflect.visitor.filter;


public class AllMethodsSameSignatureFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtExecutable<?>> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingLambdas = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    public AllMethodsSameSignatureFunction() {
    }

    public spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    public spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction includingLambdas(boolean includingLambdas) {
        this.includingLambdas = includingLambdas;
        return this;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.declaration.CtExecutable<?> targetExecutable, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.reflect.visitor.filter.LambdaFilter lambdaFilter = new spoon.reflect.visitor.filter.LambdaFilter();
        final spoon.reflect.visitor.chain.CtQuery lambdaQuery = targetExecutable.getFactory().getModel().filterChildren(lambdaFilter);
        spoon.reflect.declaration.CtMethod<?> targetMethod;
        if (targetExecutable instanceof spoon.reflect.code.CtLambda) {
            if ((includingSelf) && (includingLambdas)) {
                outputConsumer.accept(targetExecutable);
                if (query.isTerminated()) {
                    return;
                }
            }
            targetMethod = ((spoon.reflect.code.CtLambda<?>) (targetExecutable)).getOverriddenMethod();
            outputConsumer.accept(targetMethod);
            if (query.isTerminated()) {
                return;
            }
            lambdaQuery.select(new spoon.reflect.visitor.Filter<spoon.reflect.code.CtLambda<?>>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.code.CtLambda<?> lambda) {
                    return targetExecutable != lambda;
                }
            });
        }else
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
        class Context {
            boolean haveToSearchForSubtypes;
        }
        final Context context = new Context();
        context.haveToSearchForSubtypes = true;
        final spoon.support.visitor.SubInheritanceHierarchyResolver subHierarchyFnc = new spoon.support.visitor.SubInheritanceHierarchyResolver(declaringType.getFactory().getModel().getRootPackage());
        subHierarchyFnc.addSuperType(declaringType);
        java.util.Set<java.lang.String> typesCheckedForRootType = new java.util.HashSet<>();
        final java.util.List<spoon.reflect.declaration.CtType<?>> toBeCheckedSubTypes = new java.util.ArrayList<>();
        toBeCheckedSubTypes.add(declaringType);
        while ((toBeCheckedSubTypes.size()) > 0) {
            for (spoon.reflect.declaration.CtType<?> subType : toBeCheckedSubTypes) {
                spoon.support.visitor.ClassTypingContext ctc = new spoon.support.visitor.ClassTypingContext(subType);
                targetMethod = getTargetMethodOfHierarchy(targetMethods, ctc);
                forEachOverridenMethod(ctc, targetMethod, typesCheckedForRootType, new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>>() {
                    @java.lang.Override
                    public void accept(spoon.reflect.declaration.CtMethod<?> overriddenMethod) {
                        targetMethods.add(overriddenMethod);
                        outputConsumer.accept(overriddenMethod);
                        spoon.reflect.declaration.CtType<?> type = overriddenMethod.getDeclaringType();
                        lambdaFilter.addImplementingInterface(type);
                        subHierarchyFnc.addSuperType(type);
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
                subHierarchyFnc.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
                    @java.lang.Override
                    public void accept(spoon.reflect.declaration.CtType<?> type) {
                        toBeCheckedSubTypes.add(type);
                    }
                });
            }
        } 
        if (includingLambdas) {
            lambdaQuery.forEach(outputConsumer);
        }
    }

    private void forEachOverridenMethod(final spoon.support.visitor.ClassTypingContext ctc, final spoon.reflect.declaration.CtMethod<?> thisMethod, java.util.Set<java.lang.String> distintTypesSet, final spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>> outputConsumer) {
        final spoon.reflect.visitor.chain.CtQuery q = ctc.getAdaptationScope().map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtMethod.class).distinctSet(distintTypesSet));
        q.forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtMethod<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtMethod<?> thatMethod) {
                if (thisMethod == thatMethod) {
                    return;
                }
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
        throw new spoon.SpoonException("No target executable was found in super type hiearchy of class typing context");
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

