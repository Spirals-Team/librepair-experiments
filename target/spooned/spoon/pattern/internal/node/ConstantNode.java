package spoon.pattern.internal.node;


public class ConstantNode<T> extends spoon.pattern.internal.node.AbstractPrimitiveMatcher {
    protected final T template;

    public ConstantNode(T template) {
        super();
        this.template = template;
    }

    public T getTemplateNode() {
        return template;
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        return false;
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
    }

    @java.lang.Override
    public <U> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<U> result, spoon.support.util.ImmutableMap parameters) {
        result.addResult(((U) (template)));
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap matchTarget(java.lang.Object target, spoon.support.util.ImmutableMap parameters) {
        if ((target == null) && ((template) == null)) {
            return parameters;
        }
        if ((target == null) || ((template) == null)) {
            return null;
        }
        if ((target.getClass()) != (template.getClass())) {
            return null;
        }
        return target.equals(template) ? parameters : null;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(template);
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        return spoon.pattern.Quantifier.POSSESSIVE;
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        return false;
    }
}

