package spoon.refactoring;


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
        spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction potentialDeclarationFnc = new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(newName);
        spoon.reflect.declaration.CtVariable<?> var = getTarget().map(potentialDeclarationFnc).first();
        if (var != null) {
            if (var instanceof spoon.reflect.declaration.CtField) {
            }else
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
        final spoon.refactoring.CtRenameLocalVariableRefactoring.QueryDriver queryDriver = new spoon.refactoring.CtRenameLocalVariableRefactoring.QueryDriver();
        getTarget().map(new spoon.reflect.visitor.filter.LocalVariableScopeFunction(queryDriver)).select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtElement element) {
                if (element instanceof spoon.reflect.declaration.CtType<?>) {
                    spoon.reflect.declaration.CtType<?> localClass = ((spoon.reflect.declaration.CtType<?>) (element));
                    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> fields = localClass.getAllFields();
                    for (spoon.reflect.reference.CtFieldReference<?> fieldRef : fields) {
                        if (newName.equals(fieldRef.getSimpleName())) {
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
                        return false;
                    }
                    if (variable instanceof spoon.reflect.declaration.CtField) {
                        throw new spoon.SpoonException("This should not happen. The children of local class which contains a field with new name should be skipped!");
                    }
                    if (((variable instanceof spoon.reflect.code.CtCatchVariable) || (variable instanceof spoon.reflect.code.CtLocalVariable)) || (variable instanceof spoon.reflect.declaration.CtParameter)) {
                        if (queryDriver.isInContextOfLocalClass()) {
                            queryDriver.ignoreChildrenOf(variable.getParent());
                            spoon.reflect.visitor.chain.CtQueryable searchScope;
                            if (variable instanceof spoon.reflect.code.CtLocalVariable) {
                                searchScope = variable.map(new spoon.reflect.visitor.filter.SiblingsFunction().includingSelf(true).mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT));
                            }else {
                                searchScope = variable.getParent();
                            }
                            spoon.reflect.reference.CtLocalVariableReference<?> shadowedVar = searchScope.map(new spoon.reflect.visitor.filter.LocalVariableReferenceFunction(target)).first();
                            if (shadowedVar != null) {
                                createNameConflictIssue(variable, shadowedVar);
                                return true;
                            }
                            return false;
                        }else {
                            createNameConflictIssue(variable);
                            return true;
                        }
                    }else {
                        throw new spoon.SpoonException(("Unexpected variable " + (variable.getClass().getName())));
                    }
                }
                return false;
            }
        }).first();
    }

    protected void createNameConflictIssue(spoon.reflect.declaration.CtVariable<?> conflictVar) {
        throw new spoon.refactoring.RefactoringException(((((conflictVar.getClass().getSimpleName()) + " with name ") + (conflictVar.getSimpleName())) + " is in conflict."));
    }

    protected void createNameConflictIssue(spoon.reflect.declaration.CtVariable<?> conflictVar, spoon.reflect.reference.CtVariableReference<?> shadowedVarRef) {
        throw new spoon.refactoring.RefactoringException(((((conflictVar.getClass().getSimpleName()) + " with name ") + (conflictVar.getSimpleName())) + " would shadow local variable reference."));
    }
}

