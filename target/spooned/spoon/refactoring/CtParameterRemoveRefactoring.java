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
 * Removes target {@link CtParameter} from the parent target {@link CtExecutable}
 * and from all overriding/overridden methods of related type hierarchies
 * and from all lambda expressions (if any) implementing the modified interface.
 * It removes arguments from all invocations of refactored executables too.<br>
 *
 * Before the refactoring is started it checks that:
 * <ul>
 * <li>to be removed parameter is NOT used in any refactored implementation
 * <li>to be removed argument contains read only expression, which can be safely removed
 * </ul>
 * If one of the validation constraints fails, then {@link RefactoringException} is thrown and nothing is changed.
 * You can override `#create*Issue(...)` methods to handle such exceptions individually.
 * <br>
 */
public class CtParameterRemoveRefactoring implements spoon.refactoring.CtRefactoring {
    private spoon.reflect.declaration.CtParameter<?> target;

    private int parameterIndex;

    /**
     * List of all {@link CtExecutable}s whose parameter has to be removed
     */
    private java.util.List<spoon.reflect.declaration.CtExecutable<?>> targetExecutables;

    /**
     * List of all {@link CtInvocation}s whose argument has to be removed
     */
    private java.util.List<spoon.reflect.code.CtInvocation<?>> targetInvocations;

    public CtParameterRemoveRefactoring() {
    }

    /**
     *
     *
     * @return the {@link CtParameter} which has to be removed by this refactoring function
     */
    public spoon.reflect.declaration.CtParameter<?> getTarget() {
        return target;
    }

    /**
     *
     *
     * @param target
     * 		the {@link CtParameter} which has to be removed by this refactoring function
     * @return this to support fluent API
     */
    public spoon.refactoring.CtParameterRemoveRefactoring setTarget(spoon.reflect.declaration.CtParameter<?> target) {
        if ((this.target) == target) {
            return this;
        }
        this.target = target;
        this.parameterIndex = target.getParent().getParameters().indexOf(target);
        targetExecutables = null;
        targetInvocations = null;
        return this;
    }

    /**
     *
     *
     * @return computes and returns all executables, which will be modified by this refactoring
     */
    public java.util.List<spoon.reflect.declaration.CtExecutable<?>> getTargetExecutables() {
        if ((targetExecutables) == null) {
            computeAllExecutables();
        }
        return targetExecutables;
    }

    /**
     *
     *
     * @return computes and returns all invocations, which will be modified by this refactoring
     */
    public java.util.List<spoon.reflect.code.CtInvocation<?>> getTargetInvocations() {
        if ((targetInvocations) == null) {
            computeAllInvocations();
        }
        return targetInvocations;
    }

    @java.lang.Override
    public void refactor() {
        if ((getTarget()) == null) {
            throw new spoon.SpoonException("The target of refactoring is not defined");
        }
        detectIssues();
        refactorNoCheck();
    }

    /**
     * validates whether this refactoring can be done without changing behavior of the refactored code.
     */
    protected void detectIssues() {
        checkAllExecutables();
        checkAllInvocations();
    }

    /**
     * search for all methods and lambdas which has to be refactored together with target method
     */
    private void computeAllExecutables() {
        if ((getTarget()) == null) {
            throw new spoon.SpoonException("The target of refactoring is not defined");
        }
        final java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = new java.util.ArrayList<>();
        spoon.reflect.declaration.CtExecutable<?> targetExecutable = target.getParent();
        // all the executables, which belongs to same inheritance tree
        executables.add(targetExecutable);
        targetExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction()).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtExecutable<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtExecutable<?> executable) {
                executables.add(executable);
            }
        });
        targetExecutables = java.util.Collections.unmodifiableList(executables);
    }

    /**
     * search for all methods and lambdas which has to be refactored together with target method
     */
    private void computeAllInvocations() {
        spoon.reflect.visitor.filter.ExecutableReferenceFilter execRefFilter = new spoon.reflect.visitor.filter.ExecutableReferenceFilter();
        for (spoon.reflect.declaration.CtExecutable<?> exec : getTargetExecutables()) {
            execRefFilter.addExecutable(exec);
        }
        // all the invocations, which belongs to same inheritance tree
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = new java.util.ArrayList<>();
        target.getFactory().getModel().filterChildren(execRefFilter).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtExecutableReference<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtExecutableReference<?> t) {
                spoon.reflect.declaration.CtElement parent = t.getParent();
                if (parent instanceof spoon.reflect.code.CtInvocation<?>) {
                    invocations.add(((spoon.reflect.code.CtInvocation<?>) (parent)));
                }// else ignore other hits, which are not in context of invocation

            }
        });
        targetInvocations = java.util.Collections.unmodifiableList(invocations);
    }

    private void checkAllExecutables() {
        for (spoon.reflect.declaration.CtExecutable<?> executable : getTargetExecutables()) {
            checkExecutable(executable);
        }
    }

    private void checkExecutable(spoon.reflect.declaration.CtExecutable<?> executable) {
        final spoon.reflect.declaration.CtParameter<?> toBeRemovedParam = executable.getParameters().get(this.parameterIndex);
        toBeRemovedParam.map(new spoon.reflect.visitor.filter.ParameterReferenceFunction()).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtParameterReference<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtParameterReference<?> paramRef) {
                // some parameter uses are acceptable
                // e.g. parameter in invocation of super of method, which is going to be removed too.
                if (isAllowedParameterUsage(paramRef)) {
                    return;
                }
                createParameterUsedIssue(toBeRemovedParam, paramRef);
            }
        });
    }

    private void checkAllInvocations() {
        for (spoon.reflect.code.CtInvocation<?> invocation : getTargetInvocations()) {
            checkInvocation(invocation);
        }
    }

    private void checkInvocation(spoon.reflect.code.CtInvocation<?> invocation) {
        final spoon.reflect.code.CtExpression<?> toBeRemovedExpression = invocation.getArguments().get(this.parameterIndex);
        if ((canRemoveExpression(toBeRemovedExpression)) == false) {
            createExpressionCannotBeRemovedIssue(invocation, toBeRemovedExpression);
        }
    }

    /**
     * Detects whether found usage of removed parameter is acceptable
     *
     * @param paramRef
     * 		the found reference to
     * @return true if it is allowed parameter use
     */
    protected boolean isAllowedParameterUsage(spoon.reflect.reference.CtParameterReference<?> paramRef) {
        if (isRemovedParamOfRefactoredInvocation(paramRef)) {
            return true;
        }
        return false;
    }

    /**
     * Detects whether `toBeRemovedExpression` can be safely removed during the refactoring
     *
     * @param toBeRemovedExpression
     * 		the {@link CtExpression}, which will be removed by this refactoring
     * @return true if the expression used to deliver argument of removed parameter can be removed
     * false if cannot be removed and this refactoring has to be avoided.
     */
    protected boolean canRemoveExpression(spoon.reflect.code.CtExpression<?> toBeRemovedExpression) {
        class Context {
            boolean canBeRemoved = false;
        }
        final Context context = new Context();
        toBeRemovedExpression.accept(new spoon.reflect.visitor.CtAbstractVisitor() {
            @java.lang.Override
            public <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> reference) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> literal) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
                context.canBeRemoved = true;
            }

            @java.lang.Override
            public <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> thisAccess) {
                context.canBeRemoved = true;
            }
        });
        return context.canBeRemoved;
    }

    protected boolean isRemovedParamOfRefactoredInvocation(spoon.reflect.reference.CtParameterReference<?> paramRef) {
        spoon.reflect.code.CtInvocation<?> invocation = paramRef.getParent(spoon.reflect.code.CtInvocation.class);
        if (invocation == null) {
            return false;
        }
        return getTargetInvocations().contains(invocation);
    }

    /**
     * Override this method to get access to details about this refactoring issue
     *
     * @param usedParameter
     * 		to be removed parameter, which is used by `parameterUsage`
     * @param parameterUsage
     * 		the usage of parameter, which avoids it's remove
     */
    protected void createParameterUsedIssue(spoon.reflect.declaration.CtParameter<?> usedParameter, spoon.reflect.reference.CtParameterReference<?> parameterUsage) {
        throw new spoon.refactoring.RefactoringException((((("The parameter " + (usedParameter.getSimpleName())) + " cannot be removed because it is used (") + (parameterUsage.getPosition())) + ")"));
    }

    /**
     * Override this method to get access to details about this refactoring issue.
     *
     * @param toBeRemovedExpression
     * 		is the expression which delivers value for the argument of the removed parameter,
     * 		where {@link #canRemoveExpression(CtExpression)} returned false.
     */
    protected void createExpressionCannotBeRemovedIssue(spoon.reflect.code.CtInvocation<?> invocation, spoon.reflect.code.CtExpression<?> toBeRemovedExpression) {
        throw new spoon.refactoring.RefactoringException(((((("The expression " + toBeRemovedExpression) + ", which creates argument of the to be removed parameter in invocation ") + invocation) + " cannot be removed.") + " Override method `canRemoveExpression` to customize this behavior."));
    }

    protected void refactorNoCheck() {
        removeInvocationArguments();
        removeMethodParameters();
    }

    protected void removeInvocationArguments() {
        java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = getTargetInvocations();
        for (spoon.reflect.code.CtInvocation<?> invocation : invocations) {
            removeInvocationArgument(invocation);
        }
    }

    protected void removeInvocationArgument(spoon.reflect.code.CtInvocation<?> invocation) {
        invocation.removeArgument(invocation.getArguments().get(this.parameterIndex));
    }

    protected void removeMethodParameters() {
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = getTargetExecutables();
        for (spoon.reflect.declaration.CtExecutable<?> executable : executables) {
            removeParameter(executable);
        }
    }

    protected void removeParameter(spoon.reflect.declaration.CtExecutable<?> executable) {
        executable.removeParameter(executable.getParameters().get(this.parameterIndex));
    }
}

