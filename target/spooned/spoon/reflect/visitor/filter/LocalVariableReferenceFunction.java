package spoon.reflect.visitor.filter;


public class LocalVariableReferenceFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    final spoon.reflect.declaration.CtVariable<?> targetVariable;

    final java.lang.Class<?> variableClass;

    final java.lang.Class<?> variableReferenceClass;

    public LocalVariableReferenceFunction() {
        this(spoon.reflect.code.CtLocalVariable.class, spoon.reflect.reference.CtLocalVariableReference.class);
    }

    public LocalVariableReferenceFunction(spoon.reflect.code.CtLocalVariable<?> localVariable) {
        this(spoon.reflect.code.CtLocalVariable.class, spoon.reflect.reference.CtLocalVariableReference.class, localVariable);
    }

    LocalVariableReferenceFunction(java.lang.Class<?> variableClass, java.lang.Class<?> variableReferenceClass) {
        this.variableClass = variableClass;
        this.variableReferenceClass = variableReferenceClass;
        this.targetVariable = null;
    }

    LocalVariableReferenceFunction(java.lang.Class<?> variableClass, java.lang.Class<?> variableReferenceClass, spoon.reflect.declaration.CtVariable<?> variable) {
        this.variableClass = variableClass;
        this.variableReferenceClass = variableReferenceClass;
        this.targetVariable = variable;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.declaration.CtElement scope, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.declaration.CtVariable<?> var = targetVariable;
        if (var == null) {
            if (variableClass.isInstance(scope)) {
                var = ((spoon.reflect.declaration.CtVariable<?>) (scope));
            }else {
                throw new spoon.SpoonException(((((("The input of " + (getClass().getSimpleName())) + " must be a ") + (variableClass.getSimpleName())) + " but is ") + (scope.getClass().getSimpleName())));
            }
        }
        final spoon.reflect.declaration.CtVariable<?> variable = var;
        final java.lang.String simpleName = variable.getSimpleName();
        final spoon.reflect.visitor.filter.LocalVariableReferenceFunction.Context context = new spoon.reflect.visitor.filter.LocalVariableReferenceFunction.Context();
        spoon.reflect.visitor.chain.CtQuery scopeQuery;
        if (scope == variable) {
            scopeQuery = createScopeQuery(variable, scope, context);
        }else {
            final spoon.reflect.declaration.CtElement variableParent = variable.getParent();
            if ((scope.map(new spoon.reflect.visitor.filter.ParentFunction()).select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.declaration.CtElement element) {
                    if (element instanceof spoon.reflect.declaration.CtType) {
                        (context.nrTypes)++;
                    }
                    return variableParent == element;
                }
            }).first()) == null) {
                throw new spoon.SpoonException("Cannot search for references of variable in wrong scope.");
            }
            scopeQuery = scope.map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(context));
        }
        scopeQuery.select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtElement element) {
                if (variableReferenceClass.isInstance(element)) {
                    spoon.reflect.reference.CtVariableReference<?> varRef = ((spoon.reflect.reference.CtVariableReference<?>) (element));
                    if (simpleName.equals(varRef.getSimpleName())) {
                        if (context.hasLocalType()) {
                            return variable == (varRef.getDeclaration());
                        }
                        return true;
                    }
                }
                return false;
            }
        }).forEach(outputConsumer);
    }

    private static class Context implements spoon.reflect.visitor.chain.CtScannerListener {
        int nrTypes = 0;

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            if (element instanceof spoon.reflect.declaration.CtType) {
                (nrTypes)++;
            }
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
            if (element instanceof spoon.reflect.declaration.CtType) {
                (nrTypes)--;
            }
        }

        boolean hasLocalType() {
            return (nrTypes) > 0;
        }
    }

    private static final class QueryCreator extends spoon.reflect.visitor.CtAbstractVisitor {
        spoon.reflect.declaration.CtElement scope;

        spoon.reflect.visitor.chain.CtScannerListener listener;

        spoon.reflect.visitor.chain.CtQuery query;

        QueryCreator(spoon.reflect.declaration.CtElement scope, spoon.reflect.visitor.chain.CtScannerListener listener) {
            super();
            this.scope = scope;
            this.listener = listener;
        }

        @java.lang.Override
        public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
            query = scope.map(new spoon.reflect.visitor.filter.LocalVariableScopeFunction(listener));
        }

        @java.lang.Override
        public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
            query = scope.map(new spoon.reflect.visitor.filter.CatchVariableScopeFunction(listener));
        }

        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            query = scope.map(new spoon.reflect.visitor.filter.ParameterScopeFunction(listener));
        }
    }

    private spoon.reflect.visitor.chain.CtQuery createScopeQuery(spoon.reflect.declaration.CtVariable<?> variable, spoon.reflect.declaration.CtElement scope, spoon.reflect.visitor.filter.LocalVariableReferenceFunction.Context context) {
        spoon.reflect.visitor.filter.LocalVariableReferenceFunction.QueryCreator qc = new spoon.reflect.visitor.filter.LocalVariableReferenceFunction.QueryCreator(scope, context);
        variable.accept(qc);
        if ((qc.query) == null) {
            throw new spoon.SpoonException(("Unexpected type of variable: " + (variable.getClass().getName())));
        }
        return qc.query;
    }
}

