package spoon.pattern.internal.node;


public interface InlineNode extends spoon.pattern.internal.node.RootNode {
    <T> void generateInlineTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters);
}

