package spoon.pattern.internal.node;


public class SwitchNode extends spoon.pattern.internal.node.AbstractNode implements spoon.pattern.internal.node.InlineNode {
    private java.util.List<spoon.pattern.internal.node.SwitchNode.CaseNode> cases = new java.util.ArrayList<>();

    public SwitchNode() {
        super();
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        for (spoon.pattern.internal.node.SwitchNode.CaseNode caseNode : cases) {
            if (caseNode.replaceNode(oldNode, newNode)) {
                return true;
            }
        }
        return false;
    }

    public void addCase(spoon.pattern.internal.node.PrimitiveMatcher vrOfExpression, spoon.pattern.internal.node.RootNode statement) {
        cases.add(new spoon.pattern.internal.node.SwitchNode.CaseNode(vrOfExpression, statement));
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        for (spoon.pattern.internal.node.SwitchNode.CaseNode case1 : cases) {
            generator.generateTargets(case1, result, parameters);
        }
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        for (spoon.pattern.internal.node.SwitchNode.CaseNode case1 : cases) {
            if ((case1.vrOfExpression) != null) {
                case1.vrOfExpression.forEachParameterInfo(consumer);
            }
            if ((case1.statement) != null) {
                case1.statement.forEachParameterInfo(consumer);
            }
        }
    }

    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers nextMatchers) {
        boolean hasDefaultCase = false;
        for (spoon.pattern.internal.node.SwitchNode.CaseNode case1 : cases) {
            spoon.pattern.internal.matcher.TobeMatched match = case1.matchTargets(targets, nextMatchers);
            if (match != null) {
                return match;
            }
            if ((case1.vrOfExpression) == null) {
                hasDefaultCase = true;
            }
        }
        if (hasDefaultCase) {
            return null;
        }
        return new spoon.pattern.internal.node.SwitchNode.CaseNode(null, null).matchTargets(targets, nextMatchers);
    }

    private class CaseNode extends spoon.pattern.internal.node.AbstractNode implements spoon.pattern.internal.node.InlineNode {
        private spoon.pattern.internal.node.PrimitiveMatcher vrOfExpression;

        private spoon.pattern.internal.node.RootNode statement;

        private CaseNode(spoon.pattern.internal.node.PrimitiveMatcher vrOfExpression, spoon.pattern.internal.node.RootNode statement) {
            super();
            this.vrOfExpression = vrOfExpression;
            this.statement = statement;
        }

        @java.lang.Override
        public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
            if ((vrOfExpression) != null) {
                if ((vrOfExpression) == oldNode) {
                    vrOfExpression = ((spoon.pattern.internal.node.PrimitiveMatcher) (newNode));
                    return true;
                }
                if (vrOfExpression.replaceNode(oldNode, newNode)) {
                    return true;
                }
            }
            if ((statement) != null) {
                if ((statement) == oldNode) {
                    statement = newNode;
                    return true;
                }
                if (statement.replaceNode(oldNode, newNode)) {
                    return true;
                }
            }
            return false;
        }

        @java.lang.Override
        public spoon.pattern.internal.matcher.TobeMatched matchTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers nextMatchers) {
            spoon.support.util.ImmutableMap parameters = targets.getParameters();
            for (spoon.pattern.internal.node.SwitchNode.CaseNode case1 : cases) {
                if ((case1.vrOfExpression) != null) {
                    parameters = case1.vrOfExpression.matchTarget((case1 == (this)), parameters);
                    if (parameters == null) {
                        return null;
                    }
                }
            }
            targets = targets.copyAndSetParams(parameters);
            if ((statement) != null) {
                return statement.matchTargets(targets, nextMatchers);
            }
            return nextMatchers.matchAllWith(targets);
        }

        @java.lang.Override
        public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
            spoon.pattern.internal.node.SwitchNode.this.forEachParameterInfo(consumer);
        }

        @java.lang.Override
        public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
            if ((statement) != null) {
                if (isCaseSelected(generator, parameters)) {
                    generator.generateTargets(statement, result, parameters);
                }
            }
        }

        private boolean isCaseSelected(spoon.pattern.internal.DefaultGenerator generator, spoon.support.util.ImmutableMap parameters) {
            if ((vrOfExpression) == null) {
                return true;
            }
            java.lang.Boolean value = generator.generateSingleTarget(vrOfExpression, parameters, java.lang.Boolean.class);
            return value == null ? false : value.booleanValue();
        }

        @java.lang.Override
        public <T> void generateInlineTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
            spoon.reflect.factory.Factory f = generator.getFactory();
            spoon.reflect.factory.CoreFactory cf = f.Core();
            spoon.reflect.code.CtBlock<?> block = cf.createBlock();
            if ((statement) != null) {
                block.setStatements(generator.generateTargets(statement, parameters, spoon.reflect.code.CtStatement.class));
            }
            if ((vrOfExpression) != null) {
                spoon.reflect.code.CtIf ifStmt = cf.createIf();
                ifStmt.setCondition(generator.generateSingleTarget(vrOfExpression, parameters, spoon.reflect.code.CtExpression.class));
                ifStmt.setThenStatement(block);
                result.addResult(((T) (ifStmt)));
            }else {
                result.addResult(((T) (block)));
            }
        }
    }

    @java.lang.Override
    public <T> void generateInlineTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        spoon.reflect.code.CtStatement resultStmt = null;
        spoon.reflect.code.CtStatement lastElse = null;
        spoon.reflect.code.CtIf lastIf = null;
        for (spoon.pattern.internal.node.SwitchNode.CaseNode caseNode : cases) {
            spoon.reflect.code.CtStatement stmt = generator.generateSingleTarget(caseNode, parameters, spoon.reflect.code.CtStatement.class);
            if (stmt instanceof spoon.reflect.code.CtIf) {
                spoon.reflect.code.CtIf ifStmt = ((spoon.reflect.code.CtIf) (stmt));
                if (lastIf == null) {
                    resultStmt = ifStmt;
                    lastIf = ifStmt;
                }else {
                    lastIf.setElseStatement(ifStmt);
                    lastIf = ifStmt;
                }
            }else {
                if (lastElse != null) {
                    throw new spoon.SpoonException("Only one SwitchNode can have no expression.");
                }
                lastElse = stmt;
            }
        }
        if (lastIf == null) {
            if (lastElse != null) {
                result.addResult(((T) (lastElse)));
            }
            return;
        }
        if (lastElse != null) {
            lastIf.setElseStatement(lastElse);
        }
        result.addResult(((T) (resultStmt)));
    }
}

