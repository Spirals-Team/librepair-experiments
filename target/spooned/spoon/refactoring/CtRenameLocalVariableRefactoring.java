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
package spoon.refactoring;


/**
 * Spoon model refactoring function which renames `target` local variable to `newName`<br>
 * This refactoring will throw {@link RefactoringException} if the model would be not consistent after rename to new name.
 * The exception is thrown before the model modificatons are started.
 * <pre>
 * CtLocalVariable anLocalVariable = ...
 * RenameLocalVariableRefactor refactor = new RenameLocalVariableRefactor();
 * refactor.setTarget(anLocalVariable);
 * refactor.setNewName("someNewName");
 * try {
 *   refactor.refactor();
 * } catch (RefactoringException e) {
 *   //handle name conflict or name shadowing problem
 * }
 * </pre>
 */
public class CtRenameLocalVariableRefactoring extends spoon.refactoring.AbstractRenameRefactoring<spoon.reflect.code.CtLocalVariable<?>> {
    public static java.util.regex.Pattern validVariableNameRE = spoon.refactoring.AbstractRenameRefactoring.javaIdentifierRE;

    public CtRenameLocalVariableRefactoring() {
        super(spoon.refactoring.CtRenameLocalVariableRefactoring.validVariableNameRE);
    }

    protected void refactorNoCheck() {
        getTarget().map(new spoon.reflect.visitor.filter.VariableReferenceFunction()).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtReference>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtReference t) {
                t.setSimpleName(newName);
            }
        });
        target.setSimpleName(newName);
    }

    private static class QueryDriver implements spoon.reflect.visitor.chain.CtScannerListener {
        int nrOfNestedLocalClasses = 0;

        spoon.reflect.declaration.CtElement ignoredParent;

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            if (((ignoredParent) != null) && (element instanceof spoon.reflect.declaration.CtElement)) {
                spoon.reflect.declaration.CtElement ele = ((spoon.reflect.declaration.CtElement) (element));
                if (ele.hasParent(ignoredParent)) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
            }
            if (element instanceof spoon.reflect.declaration.CtType) {
                (nrOfNestedLocalClasses)++;
            }
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
            if ((ignoredParent) == element) {
                // we are living scope of ignored parent. We can stop checking it
                ignoredParent = null;
            }
            if (element instanceof spoon.reflect.declaration.CtType) {
                (nrOfNestedLocalClasses)--;
            }
        }

        public void ignoreChildrenOf(spoon.reflect.declaration.CtElement element) {
            if ((ignoredParent) != null) {
                throw new spoon.SpoonException("Unexpected state. The ignoredParent is already set");
            }
            ignoredParent = element;
        }

        public boolean isInContextOfLocalClass() {
            return (nrOfNestedLocalClasses) > 0;
        }
    }

    @java.lang.Override
    protected void detectNameConflicts() {
        /* There can be these conflicts
        1) target variable would shadow before declared variable (parameter, localVariable, catchVariable)
        --------------------------------------------------------------------------------------------------
         */
        spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction potentialDeclarationFnc = new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(newName);
        spoon.reflect.declaration.CtVariable<?> var = getTarget().map(potentialDeclarationFnc).first();
        if (var != null) {
            /* There is a local class declaration between future variable reference and variable declaration `var`.
            The found variable declaration `var` can be hidden by target variable with newName
            as long as there is no reference to `var` in visibility scope of the target variable.
            So search for such `var` reference now
             */
            // found variable reference, which would be shadowed by variable after rename.
            /* there is no local variable reference, which would be shadowed by variable after rename.
            OK
             */
            /* the found variable is in conflict with target variable with newName */
            if (var instanceof spoon.reflect.declaration.CtField) {
                /* we have found a field of same name.
                It is not problem, because variables can hide field declaration.
                Do nothing - OK
                 */
            }/* There is a local class declaration between future variable reference and variable declaration `var`.
            The found variable declaration `var` can be hidden by target variable with newName
            as long as there is no reference to `var` in visibility scope of the target variable.
            So search for such `var` reference now
             */
            // found variable reference, which would be shadowed by variable after rename.
            /* there is no local variable reference, which would be shadowed by variable after rename.
            OK
             */
            /* the found variable is in conflict with target variable with newName */
            else
                if (potentialDeclarationFnc.isTypeOnTheWay()) {
                    spoon.reflect.reference.CtVariableReference<?> shadowedVar = target.map(new spoon.reflect.visitor.filter.SiblingsFunction().includingSelf(true).mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT)).map(new spoon.reflect.visitor.filter.VariableReferenceFunction(var)).first();
                    if (shadowedVar != null) {
                        createNameConflictIssue(var, shadowedVar);
                    }else {
                    }
                }else {
                    createNameConflictIssue(var);
                }

        }
        /* 2) target variable is shadowed by later declared variable
        ---------------------------------------------------------
         */
        final spoon.refactoring.CtRenameLocalVariableRefactoring.QueryDriver queryDriver = new spoon.refactoring.CtRenameLocalVariableRefactoring.QueryDriver();
        getTarget().map(new spoon.reflect.visitor.filter.LocalVariableScopeFunction(queryDriver)).select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
            /**
             * return true for all CtVariables, which are in conflict
             */
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtElement element) {
                if (element instanceof spoon.reflect.declaration.CtType<?>) {
                    spoon.reflect.declaration.CtType<?> localClass = ((spoon.reflect.declaration.CtType<?>) (element));
                    // TODO use faster hasField, implemented using map(new AllFieldsFunction()).select(new NameFilter(newName)).first()!=null
                    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> fields = localClass.getAllFields();
                    for (spoon.reflect.reference.CtFieldReference<?> fieldRef : fields) {
                        if (newName.equals(fieldRef.getSimpleName())) {
                            /* we have found a local class field, which will shadow input local variable if it's reference is in visibility scope of that field.
                            Search for target variable reference in visibility scope of this field.
                            If found than we cannot rename target variable to newName, because that reference would be shadowed
                             */
                            queryDriver.ignoreChildrenOf(element);
                            spoon.reflect.reference.CtLocalVariableReference<?> shadowedVar = element.map(new spoon.reflect.visitor.filter.LocalVariableReferenceFunction(target)).first();
                            if (shadowedVar != null) {
                                createNameConflictIssue(fieldRef.getFieldDeclaration(), shadowedVar);
                                return true;
                            }
                            return false;
                        }
                    }
                    return false;
                }
                if (element instanceof spoon.reflect.declaration.CtVariable<?>) {
                    spoon.reflect.declaration.CtVariable<?> variable = ((spoon.reflect.declaration.CtVariable<?>) (element));
                    if ((newName.equals(variable.getSimpleName())) == false) {
                        // the variable with different name. Ignore it
                        return false;
                    }
                    // we have found a variable with new name
                    if (variable instanceof spoon.reflect.declaration.CtField) {
                        throw new spoon.SpoonException("This should not happen. The children of local class which contains a field with new name should be skipped!");
                    }
                    if (((variable instanceof spoon.reflect.code.CtCatchVariable) || (variable instanceof spoon.reflect.code.CtLocalVariable)) || (variable instanceof spoon.reflect.declaration.CtParameter)) {
                        /* we have found a catch variable or local variable or parameter with new name. */
                        if (queryDriver.isInContextOfLocalClass()) {
                            /* We are in context of local class.
                            This variable would shadow input local variable after rename
                            so we cannot rename if there exist a local variable reference in variable visibility scope.
                             */
                            queryDriver.ignoreChildrenOf(variable.getParent());
                            spoon.reflect.visitor.chain.CtQueryable searchScope;
                            if (variable instanceof spoon.reflect.code.CtLocalVariable) {
                                searchScope = variable.map(new spoon.reflect.visitor.filter.SiblingsFunction().includingSelf(true).mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT));
                            }else {
                                searchScope = variable.getParent();
                            }
                            spoon.reflect.reference.CtLocalVariableReference<?> shadowedVar = searchScope.map(new spoon.reflect.visitor.filter.LocalVariableReferenceFunction(target)).first();
                            if (shadowedVar != null) {
                                // found local variable reference, which would be shadowed by variable after rename.
                                createNameConflictIssue(variable, shadowedVar);
                                return true;
                            }
                            // there is no local variable reference, which would be shadowed by variable after rename.
                            return false;
                        }else {
                            /* We are not in context of local class.
                            So this variable is in conflict. Return it
                             */
                            createNameConflictIssue(variable);
                            return true;
                        }
                    }else {
                        // CtField should not be there, because the children of local class which contains a field with new name should be skipped!
                        // Any new variable type???
                        throw new spoon.SpoonException(("Unexpected variable " + (variable.getClass().getName())));
                    }
                }
                return false;
            }
        }).first();
    }

    /**
     * Override this method to get access to details about this refactoring issue
     *
     * @param conflictVar
     * 		- variable which would be in conflict with the `targetVariable` after it's rename to new name
     */
    protected void createNameConflictIssue(spoon.reflect.declaration.CtVariable<?> conflictVar) {
        throw new spoon.refactoring.RefactoringException(((((conflictVar.getClass().getSimpleName()) + " with name ") + (conflictVar.getSimpleName())) + " is in conflict."));
    }

    /**
     * Override this method to get access to details about this refactoring issue
     *
     * @param conflictVar
     * 		- variable which would shadow reference to `targetVariable` after it's rename to new name
     * @param shadowedVarRef
     * 		- the reference to `targetVariable`, which would be shadowed by `conflictVar`
     */
    protected void createNameConflictIssue(spoon.reflect.declaration.CtVariable<?> conflictVar, spoon.reflect.reference.CtVariableReference<?> shadowedVarRef) {
        throw new spoon.refactoring.RefactoringException(((((conflictVar.getClass().getSimpleName()) + " with name ") + (conflictVar.getSimpleName())) + " would shadow local variable reference."));
    }
}

