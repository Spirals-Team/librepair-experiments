package spoon.pattern.internal.node;


public interface RootNode extends spoon.pattern.internal.matcher.Matchers {
    void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer);

    <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters);

    boolean isSimplifyGenerated();

    void setSimplifyGenerated(boolean simplifyGenerated);

    spoon.pattern.internal.matcher.TobeMatched matchTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers nextMatchers);

    spoon.pattern.internal.matcher.Matchers MATCH_ALL = new spoon.pattern.internal.matcher.Matchers() {
        @java.lang.Override
        public spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched targets) {
            return targets.hasTargets() ? null : targets;
        }
    };

    spoon.pattern.internal.matcher.Matchers MATCH_PART = new spoon.pattern.internal.matcher.Matchers() {
        @java.lang.Override
        public spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched targets) {
            return targets;
        }
    };

    @java.lang.Override
    default spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched targets) {
        return matchTargets(targets, spoon.pattern.internal.node.RootNode.MATCH_PART);
    }

    boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode);
}

