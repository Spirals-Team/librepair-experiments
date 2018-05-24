package spoon.pattern;


@spoon.support.Experimental
public class InlinedStatementConfigurator {
    private final spoon.pattern.PatternBuilder patternBuilder;

    private boolean failOnMissingParameter = true;

    private spoon.pattern.ConflictResolutionMode conflictResolutionMode = spoon.pattern.ConflictResolutionMode.FAIL;

    public InlinedStatementConfigurator(spoon.pattern.PatternBuilder patternBuilder) {
        this.patternBuilder = patternBuilder;
    }

    public spoon.pattern.ConflictResolutionMode getConflictResolutionMode() {
        return conflictResolutionMode;
    }

    public spoon.pattern.InlinedStatementConfigurator setConflictResolutionMode(spoon.pattern.ConflictResolutionMode conflictResolutionMode) {
        this.conflictResolutionMode = conflictResolutionMode;
        return this;
    }

    public spoon.pattern.InlinedStatementConfigurator inlineIfOrForeachReferringTo(java.lang.String variableName) {
        patternBuilder.patternQuery.filterChildren((spoon.reflect.reference.CtVariableReference varRef) -> variableName.equals(varRef.getSimpleName())).forEach(this::byElement);
        return this;
    }

    spoon.pattern.InlinedStatementConfigurator byElement(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.code.CtStatement stmt = (element instanceof spoon.reflect.code.CtStatement) ? ((spoon.reflect.code.CtStatement) (element)) : element.getParent(spoon.reflect.code.CtStatement.class);
        stmt.accept(new spoon.reflect.visitor.CtAbstractVisitor() {
            @java.lang.Override
            public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
                markAsInlined(foreach);
            }

            @java.lang.Override
            public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
                markAsInlined(ifElement);
            }
        });
        return this;
    }

    public spoon.pattern.InlinedStatementConfigurator markAsInlined(spoon.reflect.code.CtForEach foreach) {
        spoon.pattern.internal.node.RootNode vr = patternBuilder.getPatternNode(foreach.getExpression());
        if ((vr instanceof spoon.pattern.internal.node.PrimitiveMatcher) == false) {
            throw new spoon.SpoonException("Each inline `for(x : iterable)` statement must have defined pattern parameter for `iterable` expression");
        }
        spoon.pattern.internal.node.PrimitiveMatcher parameterOfExpression = ((spoon.pattern.internal.node.PrimitiveMatcher) (vr));
        spoon.pattern.internal.node.ForEachNode mvr = new spoon.pattern.internal.node.ForEachNode();
        mvr.setIterableParameter(parameterOfExpression);
        spoon.reflect.code.CtLocalVariable<?> lv = foreach.getVariable();
        java.lang.String paramName = lv.getSimpleName();
        patternBuilder.configureLocalParameters(( pb) -> {
            pb.parameter(paramName).byVariable(lv);
            mvr.setLocalParameter(pb.getCurrentParameter());
        });
        mvr.setNestedModel(patternBuilder.getPatternNode(foreach, spoon.reflect.path.CtRole.BODY, spoon.reflect.path.CtRole.STATEMENT));
        patternBuilder.setNodeOfElement(foreach, mvr, conflictResolutionMode);
        return this;
    }

    public spoon.pattern.InlinedStatementConfigurator markAsInlined(spoon.reflect.code.CtIf ifElement) {
        spoon.pattern.internal.node.SwitchNode osp = new spoon.pattern.internal.node.SwitchNode();
        boolean[] canBeInline = new boolean[]{ true };
        forEachIfCase(ifElement, ( expression, block) -> {
            if (expression != null) {
                spoon.pattern.internal.node.RootNode vrOfExpression = patternBuilder.getPatternNode(expression);
                if ((vrOfExpression instanceof spoon.pattern.internal.node.ParameterNode) == false) {
                    if (failOnMissingParameter) {
                        throw new spoon.SpoonException("Each inline `if` statement must have defined pattern parameter in expression. If you want to ignore this, then call InlinedStatementConfigurator#setFailOnMissingParameter(false) first.");
                    }else {
                        canBeInline[0] = false;
                        return;
                    }
                }
                if (vrOfExpression instanceof spoon.pattern.internal.node.PrimitiveMatcher) {
                    osp.addCase(((spoon.pattern.internal.node.PrimitiveMatcher) (vrOfExpression)), getPatternNode(spoon.pattern.PatternBuilder.bodyToStatements(block)));
                }else {
                    throw new spoon.SpoonException(("Inline `if` statement have defined single value pattern parameter in expression. But there is " + (vrOfExpression.getClass().getName())));
                }
            }else {
                osp.addCase(null, getPatternNode(spoon.pattern.PatternBuilder.bodyToStatements(block)));
            }
        });
        if (canBeInline[0]) {
            patternBuilder.setNodeOfElement(ifElement, osp, conflictResolutionMode);
        }
        return this;
    }

    private spoon.pattern.internal.node.ListOfNodes getPatternNode(java.util.List<? extends spoon.reflect.declaration.CtElement> template) {
        java.util.List<spoon.pattern.internal.node.RootNode> nodes = new java.util.ArrayList<>(template.size());
        for (spoon.reflect.declaration.CtElement element : template) {
            nodes.add(patternBuilder.getPatternNode(element));
        }
        return new spoon.pattern.internal.node.ListOfNodes(nodes);
    }

    private void forEachIfCase(spoon.reflect.code.CtIf ifElement, java.util.function.BiConsumer<spoon.reflect.code.CtExpression<java.lang.Boolean>, spoon.reflect.code.CtStatement> consumer) {
        consumer.accept(ifElement.getCondition(), ifElement.getThenStatement());
        spoon.reflect.code.CtStatement elseStmt = getElseIfStatement(ifElement.getElseStatement());
        if (elseStmt instanceof spoon.reflect.code.CtIf) {
            forEachIfCase(((spoon.reflect.code.CtIf) (elseStmt)), consumer);
        }else
            if (elseStmt != null) {
                consumer.accept(null, elseStmt);
            }

    }

    private spoon.reflect.code.CtStatement getElseIfStatement(spoon.reflect.code.CtStatement elseStmt) {
        if (elseStmt instanceof spoon.reflect.code.CtBlock<?>) {
            spoon.reflect.code.CtBlock<?> block = ((spoon.reflect.code.CtBlock<?>) (elseStmt));
            if (block.isImplicit()) {
                java.util.List<spoon.reflect.code.CtStatement> stmts = block.getStatements();
                if ((stmts.size()) == 1) {
                    if ((stmts.get(0)) instanceof spoon.reflect.code.CtIf) {
                        return stmts.get(0);
                    }
                }
            }
        }
        if (elseStmt instanceof spoon.reflect.code.CtIf) {
            return ((spoon.reflect.code.CtIf) (elseStmt));
        }
        return elseStmt;
    }

    public boolean isFailOnMissingParameter() {
        return failOnMissingParameter;
    }

    public spoon.pattern.InlinedStatementConfigurator setFailOnMissingParameter(boolean failOnMissingParameter) {
        this.failOnMissingParameter = failOnMissingParameter;
        return this;
    }
}

