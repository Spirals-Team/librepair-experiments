package spoon.refactoring;


public class CtParameterRemoveRefactoring implements spoon.refactoring.CtRefactoring {
    private spoon.reflect.declaration.CtParameter<?> target;

    private int parameterIndex;

    private java.util.List<spoon.reflect.declaration.CtExecutable<?>> targetExecutables;

    private java.util.List<spoon.reflect.code.CtInvocation<?>> targetInvocations;

    public CtParameterRemoveRefactoring() {
    }

    public spoon.reflect.declaration.CtParameter<?> getTarget() {
        return target;
    }

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

    public java.util.List<spoon.reflect.declaration.CtExecutable<?>> getTargetExecutables() {
        if ((targetExecutables) == null) {
            computeAllExecutables();
        }
        return targetExecutables;
    }

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

    protected void detectIssues() {
        checkAllExecutables();
        checkAllInvocations();
    }

    private void computeAllExecutables() {
        if ((getTarget()) == null) {
            throw new spoon.SpoonException("The target of refactoring is not defined");
        }
        final java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = new java.util.ArrayList<>();
        spoon.reflect.declaration.CtExecutable<?> targetExecutable = target.getParent();
        executables.add(targetExecutable);
        targetExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction()).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtExecutable<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtExecutable<?> executable) {
                executables.add(executable);
            }
        });
        targetExecutables = java.util.Collections.unmodifiableList(executables);
    }

    private void computeAllInvocations() {
        spoon.reflect.visitor.filter.ExecutableReferenceFilter execRefFilter = new spoon.reflect.visitor.filter.ExecutableReferenceFilter();
        for (spoon.reflect.declaration.CtExecutable<?> exec : getTargetExecutables()) {
            execRefFilter.addExecutable(exec);
        }
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = new java.util.ArrayList<>();
        target.getFactory().getModel().filterChildren(execRefFilter).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.reference.CtExecutableReference<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.reference.CtExecutableReference<?> t) {
                spoon.reflect.declaration.CtElement parent = t.getParent();
                if (parent instanceof spoon.reflect.code.CtInvocation<?>) {
                    invocations.add(((spoon.reflect.code.CtInvocation<?>) (parent)));
                }
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

    protected boolean isAllowedParameterUsage(spoon.reflect.reference.CtParameterReference<?> paramRef) {
        if (isRemovedParamOfRefactoredInvocation(paramRef)) {
            return true;
        }
        return false;
    }

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

    protected void createParameterUsedIssue(spoon.reflect.declaration.CtParameter<?> usedParameter, spoon.reflect.reference.CtParameterReference<?> parameterUsage) {
        throw new spoon.refactoring.RefactoringException((((("The parameter " + (usedParameter.getSimpleName())) + " cannot be removed because it is used (") + (parameterUsage.getPosition())) + ")"));
    }

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

