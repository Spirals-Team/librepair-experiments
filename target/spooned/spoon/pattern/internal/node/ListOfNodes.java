package spoon.pattern.internal.node;


public class ListOfNodes extends spoon.pattern.internal.node.AbstractNode {
    protected java.util.List<spoon.pattern.internal.node.RootNode> nodes;

    public ListOfNodes(java.util.List<spoon.pattern.internal.node.RootNode> nodes) {
        super();
        this.nodes = nodes;
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        for (spoon.pattern.internal.node.RootNode node : nodes) {
            node.forEachParameterInfo(consumer);
        }
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        for (spoon.pattern.internal.node.RootNode node : nodes) {
            generator.generateTargets(node, result, parameters);
        }
    }

    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers nextMatchers) {
        return spoon.pattern.internal.matcher.ChainOfMatchersImpl.create(nodes, nextMatchers).matchAllWith(targets);
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        for (int i = 0; i < (nodes.size()); i++) {
            spoon.pattern.internal.node.RootNode node = nodes.get(i);
            if (node == oldNode) {
                nodes.set(i, newNode);
                return true;
            }
            if (node.replaceNode(oldNode, newNode)) {
                return true;
            }
        }
        return false;
    }

    public java.util.List<spoon.pattern.internal.node.RootNode> getNodes() {
        return nodes;
    }
}

