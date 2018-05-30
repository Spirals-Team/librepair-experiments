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
 * This mapping function expects a {@link CtLocalVariable} as input
 * and returns all {@link CtLocalVariableReference}s, which refers this input.
 * <br>
 * Usage:<br>
 * <pre> {@code
 * CtLocalVariable var = ...;
 * var
 *   .map(new LocalVariableReferenceFunction())
 *   .forEach((CtLocalVariableReference ref)->...process references...);
 * }
 * </pre>
 */
public class LocalVariableReferenceFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    final spoon.reflect.declaration.CtVariable<?> targetVariable;

    final java.lang.Class<?> variableClass;

    final java.lang.Class<?> variableReferenceClass;

    public LocalVariableReferenceFunction() {
        this(spoon.reflect.code.CtLocalVariable.class, spoon.reflect.reference.CtLocalVariableReference.class);
    }

    /**
     * This constructor allows to define input local variable - the one for which this function will search for.
     * In such case the input of mapping function represents the scope
     * where this local variable is searched for.
     *
     * @param localVariable
     * 		- the local variable declaration which is searched in scope of input element of this mapping function.
     */
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
        // the context which knows whether we are scanning in scope of local type or not
        final spoon.reflect.visitor.filter.LocalVariableReferenceFunction.Context context = new spoon.reflect.visitor.filter.LocalVariableReferenceFunction.Context();
        spoon.reflect.visitor.chain.CtQuery scopeQuery;
        if (scope == variable) {
            // we are starting search from local variable declaration
            scopeQuery = createScopeQuery(variable, scope, context);
        }else {
            // we are starting search later, somewhere deep in scope of variable declaration
            final spoon.reflect.declaration.CtElement variableParent = variable.getParent();
            /* search in parents of searching scope for the variableParent
            1) to check that scope is a child of variableParent
            2) to detect if there is an local class between variable declaration and scope
             */
            if ((scope.map(new spoon.reflect.visitor.filter.ParentFunction()).select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.declaration.CtElement element) {
                    if (element instanceof spoon.reflect.declaration.CtType) {
                        // detected that the search scope is in local class declared in visibility scope of variable
                        (context.nrTypes)++;
                    }
                    return variableParent == element;
                }
            }).first()) == null) {
                // the scope is not under children of localVariable
                throw new spoon.SpoonException("Cannot search for references of variable in wrong scope.");
            }
            // search in all children of the scope element
            scopeQuery = scope.map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(context));
        }
        scopeQuery.select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtElement element) {
                if (variableReferenceClass.isInstance(element)) {
                    spoon.reflect.reference.CtVariableReference<?> varRef = ((spoon.reflect.reference.CtVariableReference<?>) (element));
                    if (simpleName.equals(varRef.getSimpleName())) {
                        // we have found a variable reference of required type in visibility scope of targetVariable
                        if (context.hasLocalType()) {
                            // there exists a local type in visibility scope of this variable declaration
                            // another variable declarations in scope of this local class may shadow input localVariable
                            // so finally check that found variable reference is really a reference to target variable
                            return variable == (varRef.getDeclaration());
                        }
                        // else we can be sure that found reference is reference to variable
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

