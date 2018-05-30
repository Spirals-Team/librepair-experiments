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
 * This mapping function searches for all {@link CtVariable} instances,
 * which might be a declaration of an input {@link CtElement}.
 * <br>
 * It can be used to search for variable declarations of
 * variable references and for detection of variable name conflicts
 * <br>
 * It returns {@link CtLocalVariable} instances,
 * or it returns {@link CtCatchVariable} instances of catch blocks,
 * or i returns {@link CtParameter} instances of methods, lambdas and catch blocks.
 * or it returns {@link CtField} instances from wrapping classes and their super classes too.
 * <br>
 * The elements are visited in the following order: first elements are thought in the nearest parent blocks,
 * then in the fields of wrapping classes, then in the fields of super classes, etc.
 * <br>
 * Example: Search for all potential {@link CtVariable} declarations<br>
 * <pre> {@code
 * CtVariableReference varRef = ...;
 * varRef.map(new PotentialVariableDeclarationFunction()).forEach(...process result...);
 * }
 * </pre>
 * Example: Search for {@link CtVariable} declaration of variable named `varName` in scope "scope"
 * <pre> {@code
 * CtElement scope = ...;
 * String varName = "anVariableName";
 * CtVariable varOrNull = scope.map(new PotentialVariableDeclarationFunction(varName)).first();
 * }
 * </pre>
 */
public class PotentialVariableDeclarationFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean isTypeOnTheWay;

    private final java.lang.String variableName;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean isInStaticScope;

    public PotentialVariableDeclarationFunction() {
        this.variableName = null;
    }

    /**
     * Searches for a variable with exact name.
     *
     * @param variableName
     * 		
     */
    public PotentialVariableDeclarationFunction(java.lang.String variableName) {
        this.variableName = variableName;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        isTypeOnTheWay = false;
        isInStaticScope = false;
        // Search previous siblings for element which may represents the declaration of this local variable
        spoon.reflect.visitor.chain.CtQuery siblingsQuery = // select only CtVariable nodes
        input.getFactory().createQuery().map(new spoon.reflect.visitor.filter.SiblingsFunction().mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.PREVIOUS)).select(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtVariable.class));
        if ((variableName) != null) {
            // variable name is defined so we have to search only for variables with that name
            siblingsQuery = siblingsQuery.select(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, variableName));
        }
        spoon.reflect.declaration.CtElement scopeElement = input;
        // Search input and then all parents until first CtPackage for element which may represents the declaration of this local variable
        while (((scopeElement != null) && (!(scopeElement instanceof spoon.reflect.declaration.CtPackage))) && (scopeElement.isParentInitialized())) {
            spoon.reflect.declaration.CtElement parent = scopeElement.getParent();
            // visit all previous CtVariable siblings of scopeElement element in parent BodyHolder or Statement list
            // visit parameters of CtCatch and CtExecutable (method, lambda)
            if (parent instanceof spoon.reflect.declaration.CtType<?>) {
                isTypeOnTheWay = true;
                // visit each CtField of `parent` CtType
                spoon.reflect.visitor.chain.CtQuery q = parent.map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtField.class));
                q.forEach((spoon.reflect.declaration.CtField<?> field) -> {
                    if ((isInStaticScope) && ((field.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC)) == false)) {
                        /* the variable reference is used in static scope,
                        but the field is not static - ignore it
                         */
                        return;
                    }
                    // else send field as potential variable declaration
                    if (sendToOutput(field, outputConsumer)) {
                        // and terminate the internal query q if outer query is already terminated
                        q.terminate();
                    }
                });
                if (query.isTerminated()) {
                    return;
                }
            }// visit all previous CtVariable siblings of scopeElement element in parent BodyHolder or Statement list
            // visit parameters of CtCatch and CtExecutable (method, lambda)
            else
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

    /**
     *
     *
     * @param var
     * 		
     * @param output
     * 		
     * @return true if query processing is terminated
     */
    private boolean sendToOutput(spoon.reflect.declaration.CtVariable<?> var, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> output) {
        if (((variableName) == null) || (variableName.equals(var.getSimpleName()))) {
            output.accept(var);
        }
        return query.isTerminated();
    }

    /**
     * This method provides access to current state of this function.
     * It is intended to be called by other mapping functions at query processing time or after query is finished.
     *
     * @return true if there is an local class on the way from the input of this mapping function
     * to the actually found potential variable declaration
     */
    public boolean isTypeOnTheWay() {
        return isTypeOnTheWay;
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

