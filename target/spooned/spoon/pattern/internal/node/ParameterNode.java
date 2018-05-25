package spoon.pattern.internal.node;


public class ParameterNode extends spoon.pattern.internal.node.AbstractPrimitiveMatcher {
    private final spoon.pattern.internal.parameter.ParameterInfo parameterInfo;

    public ParameterNode(spoon.pattern.internal.parameter.ParameterInfo parameterInfo) {
        super();
        this.parameterInfo = parameterInfo;
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        return false;
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        generator.getValueAs(parameterInfo, result, parameters);
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap matchTarget(java.lang.Object target, spoon.support.util.ImmutableMap parameters) {
        return parameterInfo.addValueAs(parameters, target);
    }

    public spoon.pattern.internal.parameter.ParameterInfo getParameterInfo() {
        return parameterInfo;
    }

    @java.lang.Override
    public boolean isRepeatable() {
        return parameterInfo.isRepeatable();
    }

    @java.lang.Override
    public boolean isMandatory(spoon.support.util.ImmutableMap parameters) {
        return parameterInfo.isMandatory(parameters);
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        return parameterInfo.isTryNextMatch(parameters);
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        return parameterInfo.getMatchingStrategy();
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        consumer.accept(parameterInfo, this);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ("${" + (parameterInfo)) + "}";
    }
}

