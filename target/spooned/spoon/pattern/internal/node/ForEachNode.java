package spoon.pattern.internal.node;


public class ForEachNode extends spoon.pattern.internal.node.AbstractRepeatableMatcher implements spoon.pattern.internal.node.InlineNode {
    private spoon.pattern.internal.node.PrimitiveMatcher iterableParameter;

    private spoon.pattern.internal.node.RootNode nestedModel;

    private spoon.pattern.internal.parameter.ParameterInfo localParameter;

    public ForEachNode() {
        super();
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        if ((iterableParameter) == oldNode) {
            oldNode = newNode;
            return true;
        }
        if (iterableParameter.replaceNode(oldNode, newNode)) {
            return true;
        }
        if ((nestedModel) == oldNode) {
            nestedModel = newNode;
            return true;
        }
        if (nestedModel.replaceNode(oldNode, newNode)) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        for (java.lang.Object parameterValue : generator.generateTargets(iterableParameter, parameters, java.lang.Object.class)) {
            generator.generateTargets(nestedModel, result, parameters.putValue(localParameter.getName(), parameterValue));
        }
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        return iterableParameter.getMatchingStrategy();
    }

    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched tobeMatched) {
        spoon.pattern.internal.matcher.TobeMatched localMatch = nestedModel.matchAllWith(tobeMatched.copyAndSetParams(tobeMatched.getParameters().checkpoint()));
        if (localMatch == null) {
            return null;
        }
        spoon.support.util.ImmutableMap newParameters = tobeMatched.getParameters();
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> e : localMatch.getParameters().getModifiedValues().entrySet()) {
            java.lang.String name = e.getKey();
            java.lang.Object value = e.getValue();
            if (name.equals(localParameter.getName())) {
                newParameters = iterableParameter.matchTarget(value, newParameters);
                if (newParameters == null) {
                    return null;
                }
            }else {
                newParameters = newParameters.putValue(name, value);
            }
        }
        return localMatch.copyAndSetParams(newParameters);
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        iterableParameter.forEachParameterInfo(consumer);
        consumer.accept(localParameter, this);
    }

    public void setNestedModel(spoon.pattern.internal.node.RootNode valueResolver) {
        this.nestedModel = valueResolver;
    }

    public void setIterableParameter(spoon.pattern.internal.node.PrimitiveMatcher substRequestOfIterable) {
        this.iterableParameter = substRequestOfIterable;
    }

    public void setLocalParameter(spoon.pattern.internal.parameter.ParameterInfo parameterInfo) {
        this.localParameter = parameterInfo;
    }

    @java.lang.Override
    public boolean isRepeatable() {
        return iterableParameter.isRepeatable();
    }

    @java.lang.Override
    public boolean isMandatory(spoon.support.util.ImmutableMap parameters) {
        return iterableParameter.isMandatory(parameters);
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        return iterableParameter.isTryNextMatch(parameters);
    }

    @java.lang.Override
    public <T> void generateInlineTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        spoon.reflect.factory.Factory f = generator.getFactory();
        spoon.reflect.code.CtForEach forEach = f.Core().createForEach();
        forEach.setVariable(f.Code().createLocalVariable(f.Type().objectType(), localParameter.getName(), null));
        forEach.setExpression(generator.generateSingleTarget(iterableParameter, parameters, spoon.reflect.code.CtExpression.class));
        spoon.reflect.code.CtBlock<?> body = f.createBlock();
        body.setStatements(generator.generateTargets(nestedModel, parameters, spoon.reflect.code.CtStatement.class));
        forEach.setBody(body);
        result.addResult(((T) (forEach)));
    }
}

