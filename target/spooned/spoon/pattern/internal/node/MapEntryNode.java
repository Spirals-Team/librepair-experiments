package spoon.pattern.internal.node;


public class MapEntryNode extends spoon.pattern.internal.node.AbstractPrimitiveMatcher {
    private spoon.pattern.internal.node.RootNode key;

    private spoon.pattern.internal.node.RootNode value;

    public MapEntryNode(spoon.pattern.internal.node.RootNode key, spoon.pattern.internal.node.RootNode value) {
        super();
        this.key = key;
        this.value = value;
    }

    public spoon.pattern.internal.node.RootNode getKey() {
        return key;
    }

    public spoon.pattern.internal.node.RootNode getValue() {
        return value;
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        if ((key) == oldNode) {
            key = newNode;
            return true;
        }
        if ((value) == oldNode) {
            value = newNode;
            return true;
        }
        if (key.replaceNode(oldNode, newNode)) {
            return true;
        }
        if (value.replaceNode(oldNode, newNode)) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        key.forEachParameterInfo(consumer);
        value.forEachParameterInfo(consumer);
    }

    private static class Entry implements java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtElement> {
        private final java.lang.String key;

        private spoon.reflect.declaration.CtElement value;

        Entry(java.lang.String key, spoon.reflect.declaration.CtElement value) {
            super();
            this.key = key;
            this.value = value;
        }

        @java.lang.Override
        public java.lang.String getKey() {
            return key;
        }

        @java.lang.Override
        public spoon.reflect.declaration.CtElement getValue() {
            return value;
        }

        @java.lang.Override
        public spoon.reflect.declaration.CtElement setValue(spoon.reflect.declaration.CtElement value) {
            spoon.reflect.declaration.CtElement oldV = this.value;
            this.value = value;
            return oldV;
        }
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        java.lang.String entryKey = generator.generateSingleTarget(key, parameters, java.lang.String.class);
        spoon.reflect.declaration.CtElement entryValue = generator.generateSingleTarget(value, parameters, spoon.reflect.declaration.CtElement.class);
        if ((entryKey != null) && (entryValue != null)) {
            result.addResult(((T) (new spoon.pattern.internal.node.MapEntryNode.Entry(entryKey, entryValue))));
        }
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap matchTarget(java.lang.Object target, spoon.support.util.ImmutableMap parameters) {
        if (target instanceof java.util.Map.Entry) {
            java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtElement> targetEntry = ((java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtElement>) (target));
            parameters = spoon.pattern.internal.matcher.TobeMatched.getMatchedParameters(getKey().matchAllWith(spoon.pattern.internal.matcher.TobeMatched.create(parameters, spoon.reflect.meta.ContainerKind.SINGLE, targetEntry.getKey())));
            if (parameters == null) {
                return null;
            }
            return spoon.pattern.internal.matcher.TobeMatched.getMatchedParameters(getValue().matchAllWith(spoon.pattern.internal.matcher.TobeMatched.create(parameters, spoon.reflect.meta.ContainerKind.SINGLE, targetEntry.getValue())));
        }
        throw new spoon.SpoonException(("Unexpected target type " + (target.getClass().getName())));
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        if ((key) instanceof spoon.pattern.internal.node.ParameterNode) {
            return ((spoon.pattern.internal.node.ParameterNode) (key)).getMatchingStrategy();
        }
        return spoon.pattern.Quantifier.POSSESSIVE;
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        if ((key) instanceof spoon.pattern.internal.node.ParameterNode) {
            return ((spoon.pattern.internal.node.ParameterNode) (key)).isTryNextMatch(parameters);
        }
        return false;
    }
}

