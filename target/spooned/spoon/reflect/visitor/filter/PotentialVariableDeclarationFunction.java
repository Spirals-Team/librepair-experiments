package spoon.reflect.visitor.filter;


public class PotentialVariableDeclarationFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean isTypeOnTheWay;

    private final java.lang.String variableName;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean isInStaticScope;

    public PotentialVariableDeclarationFunction() {
        this.variableName = null;
    }

    public PotentialVariableDeclarationFunction(java.lang.String variableName) {
        this.variableName = variableName;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        isTypeOnTheWay = false;
        isInStaticScope = false;
        spoon.reflect.visitor.chain.CtQuery siblingsQuery = input.getFactory().createQuery().map(new spoon.reflect.visitor.filter.SiblingsFunction().mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.PREVIOUS)).select(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtVariable.class));
        if ((variableName) != null) {
            siblingsQuery = siblingsQuery.select(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, variableName));
        }
        spoon.reflect.declaration.CtElement scopeElement = input;
        while (((scopeElement != null) && (!(scopeElement instanceof spoon.reflect.declaration.CtPackage))) && (scopeElement.isParentInitialized())) {
            spoon.reflect.declaration.CtElement parent = scopeElement.getParent();
            if (parent instanceof spoon.reflect.declaration.CtType<?>) {
                isTypeOnTheWay = true;
                spoon.reflect.visitor.chain.CtQuery q = parent.map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtField.class));
                q.forEach((spoon.reflect.declaration.CtField<?> field) -> {
                    if ((isInStaticScope) && ((field.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC)) == false)) {
                        return;
                    }
                    if (sendToOutput(field, outputConsumer)) {
                        q.terminate();
                    }
                });
                if (query.isTerminated()) {
                    return;
                }
            }else
                if ((parent instanceof spoon.reflect.code.CtBodyHolder) || (parent instanceof spoon.reflect.code.CtStatementList)) {
                    siblingsQuery.setInput(scopeElement).forEach(outputConsumer);
                    if (query.isTerminated()) {
                        return;
                    }
                    if (parent instanceof spoon.reflect.code.CtCatch) {
                        spoon.reflect.code.CtCatch ctCatch = ((spoon.reflect.code.CtCatch) (parent));
                        if (sendToOutput(ctCatch.getParameter(), outputConsumer)) {
                            return;
                        }
                    }else
                        if (parent instanceof spoon.reflect.declaration.CtExecutable) {
                            spoon.reflect.declaration.CtExecutable<?> exec = ((spoon.reflect.declaration.CtExecutable<?>) (parent));
                            for (spoon.reflect.declaration.CtParameter<?> param : exec.getParameters()) {
                                if (sendToOutput(param, outputConsumer)) {
                                    return;
                                }
                            }
                        }

                }

            if (parent instanceof spoon.reflect.declaration.CtModifiable) {
                isInStaticScope = (isInStaticScope) || (((spoon.reflect.declaration.CtModifiable) (parent)).hasModifier(spoon.reflect.declaration.ModifierKind.STATIC));
            }
            scopeElement = parent;
        } 
    }

    private boolean sendToOutput(spoon.reflect.declaration.CtVariable<?> var, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> output) {
        if (((variableName) == null) || (variableName.equals(var.getSimpleName()))) {
            output.accept(var);
        }
        return query.isTerminated();
    }

    public boolean isTypeOnTheWay() {
        return isTypeOnTheWay;
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

