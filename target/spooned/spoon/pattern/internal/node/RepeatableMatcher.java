package spoon.pattern.internal.node;


public interface RepeatableMatcher extends spoon.pattern.internal.node.RootNode {
    spoon.pattern.Quantifier getMatchingStrategy();

    default boolean isRepeatable() {
        return false;
    }

    default boolean isMandatory(spoon.support.util.ImmutableMap parameters) {
        return true;
    }

    boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters);
}

